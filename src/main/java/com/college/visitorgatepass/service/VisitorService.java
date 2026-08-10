package com.college.visitorgatepass.service;

import com.college.visitorgatepass.dto.VisitorDtos.VisitorRequest;
import com.college.visitorgatepass.exception.ResourceNotFoundException;
import com.college.visitorgatepass.model.Visitor;
import com.college.visitorgatepass.repository.VisitorRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VisitorService {

    private final VisitorRepository visitorRepository;

    public VisitorService(VisitorRepository visitorRepository) {
        this.visitorRepository = visitorRepository;
    }

    public List<Visitor> search(String query) {
        if (query == null || query.isBlank()) {
            return visitorRepository.findAll();
        }
        return visitorRepository.findByFullNameContainingIgnoreCaseOrPhoneContaining(query, query);
    }

    public Visitor getById(Long id) {
        return visitorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Visitor not found with id " + id));
    }

    @Transactional
    public Visitor create(VisitorRequest request) {
        return visitorRepository.save(apply(new Visitor(), request));
    }

    /**
     * Returns the existing visitor with the same phone number, creating one when absent.
     */
    @Transactional
    public Visitor findOrCreate(VisitorRequest request) {
        return visitorRepository.findByPhone(request.phone()).orElseGet(() -> create(request));
    }

    @Transactional
    public Visitor update(Long id, VisitorRequest request) {
        return visitorRepository.save(apply(getById(id), request));
    }

    @Transactional
    public void delete(Long id) {
        visitorRepository.delete(getById(id));
    }

    private Visitor apply(Visitor visitor, VisitorRequest request) {
        visitor.setFullName(request.fullName());
        visitor.setPhone(request.phone());
        visitor.setEmail(request.email());
        visitor.setCompany(request.company());
        visitor.setIdProofType(request.idProofType());
        visitor.setIdProofNumber(request.idProofNumber());
        visitor.setAddress(request.address());
        return visitor;
    }
}
