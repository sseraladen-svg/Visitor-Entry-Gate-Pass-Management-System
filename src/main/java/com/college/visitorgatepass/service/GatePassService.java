package com.college.visitorgatepass.service;

import com.college.visitorgatepass.dto.GatePassDtos.DashboardStats;
import com.college.visitorgatepass.dto.GatePassDtos.GatePassRequest;
import com.college.visitorgatepass.exception.BadRequestException;
import com.college.visitorgatepass.exception.ResourceNotFoundException;
import com.college.visitorgatepass.model.GatePass;
import com.college.visitorgatepass.model.GatePassStatus;
import com.college.visitorgatepass.model.Role;
import com.college.visitorgatepass.model.User;
import com.college.visitorgatepass.model.Visitor;
import com.college.visitorgatepass.repository.GatePassRepository;
import com.college.visitorgatepass.repository.VisitorRepository;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GatePassService {

    private static final String CODE_ALPHABET = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    private static final int CODE_LENGTH = 8;

    private final GatePassRepository gatePassRepository;
    private final VisitorRepository visitorRepository;
    private final VisitorService visitorService;
    private final UserService userService;
    private final SecureRandom random = new SecureRandom();

    public GatePassService(GatePassRepository gatePassRepository,
                           VisitorRepository visitorRepository,
                           VisitorService visitorService,
                           UserService userService) {
        this.gatePassRepository = gatePassRepository;
        this.visitorRepository = visitorRepository;
        this.visitorService = visitorService;
        this.userService = userService;
    }

    public List<GatePass> findAll(GatePassStatus status, Long hostId) {
        if (status != null) {
            return gatePassRepository.findByStatusOrderByCreatedAtDesc(status);
        }
        if (hostId != null) {
            return gatePassRepository.findByHostIdOrderByCreatedAtDesc(hostId);
        }
        return gatePassRepository.findAllByOrderByCreatedAtDesc();
    }

    public GatePass getById(Long id) {
        return gatePassRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Gate pass not found with id " + id));
    }

    public GatePass getByPassCode(String passCode) {
        return gatePassRepository.findByPassCode(passCode.trim().toUpperCase())
                .orElseThrow(() -> new ResourceNotFoundException("No gate pass found for code " + passCode));
    }

    @Transactional
    public GatePass create(GatePassRequest request, User currentUser) {
        Visitor visitor;
        if (request.visitorId() != null) {
            visitor = visitorService.getById(request.visitorId());
        } else if (request.visitor() != null) {
            visitor = visitorService.findOrCreate(request.visitor());
        } else {
            throw new BadRequestException("Either visitorId or visitor details must be provided");
        }

        if (!request.expectedExit().isAfter(request.expectedEntry())) {
            throw new BadRequestException("expectedExit must be after expectedEntry");
        }

        User host = userService.getById(request.hostId());
        if (host.getRole() == Role.SECURITY) {
            throw new BadRequestException("A security user cannot be selected as the host");
        }

        GatePass pass = new GatePass();
        pass.setPassCode(generateUniquePassCode());
        pass.setVisitor(visitor);
        pass.setHost(host);
        pass.setCreatedBy(currentUser);
        pass.setPurpose(request.purpose());
        pass.setExpectedEntry(request.expectedEntry());
        pass.setExpectedExit(request.expectedExit());
        pass.setVehicleNumber(request.vehicleNumber());
        pass.setNumberOfVisitors(request.numberOfVisitors() == null ? 1 : request.numberOfVisitors());
        pass.setRemarks(request.remarks());
        pass.setStatus(GatePassStatus.PENDING);
        return gatePassRepository.save(pass);
    }

    @Transactional
    public GatePass approve(Long id, User approver, String remarks) {
        GatePass pass = getById(id);
        requireStatus(pass, GatePassStatus.PENDING, "approved");
        pass.setStatus(GatePassStatus.APPROVED);
        pass.setApprovedBy(approver);
        if (remarks != null && !remarks.isBlank()) {
            pass.setRemarks(remarks);
        }
        return gatePassRepository.save(pass);
    }

    @Transactional
    public GatePass reject(Long id, User approver, String remarks) {
        GatePass pass = getById(id);
        requireStatus(pass, GatePassStatus.PENDING, "rejected");
        pass.setStatus(GatePassStatus.REJECTED);
        pass.setApprovedBy(approver);
        if (remarks != null && !remarks.isBlank()) {
            pass.setRemarks(remarks);
        }
        return gatePassRepository.save(pass);
    }

    @Transactional
    public GatePass cancel(Long id) {
        GatePass pass = getById(id);
        if (pass.getStatus() == GatePassStatus.CHECKED_IN || pass.getStatus() == GatePassStatus.CHECKED_OUT) {
            throw new BadRequestException("A pass that has already been used cannot be cancelled");
        }
        pass.setStatus(GatePassStatus.CANCELLED);
        return gatePassRepository.save(pass);
    }

    @Transactional
    public GatePass checkIn(String passCode) {
        GatePass pass = getByPassCode(passCode);
        if (pass.getStatus() != GatePassStatus.APPROVED) {
            throw new BadRequestException("Only an approved pass can be checked in (current status: "
                    + pass.getStatus() + ")");
        }
        if (pass.isExpired()) {
            throw new BadRequestException("This pass expired on " + pass.getExpectedExit());
        }
        pass.setStatus(GatePassStatus.CHECKED_IN);
        pass.setCheckInTime(LocalDateTime.now());
        return gatePassRepository.save(pass);
    }

    @Transactional
    public GatePass checkOut(String passCode) {
        GatePass pass = getByPassCode(passCode);
        if (pass.getStatus() != GatePassStatus.CHECKED_IN) {
            throw new BadRequestException("Only a checked-in pass can be checked out (current status: "
                    + pass.getStatus() + ")");
        }
        pass.setStatus(GatePassStatus.CHECKED_OUT);
        pass.setCheckOutTime(LocalDateTime.now());
        return gatePassRepository.save(pass);
    }

    public DashboardStats stats() {
        return new DashboardStats(
                visitorRepository.count(),
                gatePassRepository.count(),
                gatePassRepository.countByStatus(GatePassStatus.PENDING),
                gatePassRepository.countByStatus(GatePassStatus.APPROVED),
                gatePassRepository.countByStatus(GatePassStatus.CHECKED_IN),
                gatePassRepository.countByStatus(GatePassStatus.CHECKED_IN),
                gatePassRepository.countByCheckInTimeAfter(LocalDate.now().atStartOfDay()));
    }

    private void requireStatus(GatePass pass, GatePassStatus expected, String action) {
        if (pass.getStatus() != expected) {
            throw new BadRequestException("Only a " + expected + " pass can be " + action
                    + " (current status: " + pass.getStatus() + ")");
        }
    }

    private String generateUniquePassCode() {
        for (int attempt = 0; attempt < 10; attempt++) {
            StringBuilder builder = new StringBuilder("VP-");
            for (int i = 0; i < CODE_LENGTH; i++) {
                builder.append(CODE_ALPHABET.charAt(random.nextInt(CODE_ALPHABET.length())));
            }
            String code = builder.toString();
            if (gatePassRepository.findByPassCode(code).isEmpty()) {
                return code;
            }
        }
        throw new IllegalStateException("Unable to generate a unique pass code");
    }
}
