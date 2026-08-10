package com.college.visitorgatepass.controller;

import com.college.visitorgatepass.dto.GatePassDtos.DecisionRequest;
import com.college.visitorgatepass.dto.GatePassDtos.GatePassRequest;
import com.college.visitorgatepass.dto.GatePassDtos.GatePassResponse;
import com.college.visitorgatepass.model.GatePassStatus;
import com.college.visitorgatepass.model.User;
import com.college.visitorgatepass.service.GatePassService;
import com.college.visitorgatepass.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gate-passes")
public class GatePassController {

    private final GatePassService gatePassService;
    private final UserService userService;

    public GatePassController(GatePassService gatePassService, UserService userService) {
        this.gatePassService = gatePassService;
        this.userService = userService;
    }

    @GetMapping
    public List<GatePassResponse> list(@RequestParam(required = false) GatePassStatus status,
                                       @RequestParam(required = false) Long hostId) {
        return gatePassService.findAll(status, hostId).stream().map(GatePassResponse::from).toList();
    }

    @GetMapping("/{id}")
    public GatePassResponse get(@PathVariable Long id) {
        return GatePassResponse.from(gatePassService.getById(id));
    }

    @GetMapping("/code/{passCode}")
    public GatePassResponse getByCode(@PathVariable String passCode) {
        return GatePassResponse.from(gatePassService.getByPassCode(passCode));
    }

    @PostMapping
    public ResponseEntity<GatePassResponse> create(@Valid @RequestBody GatePassRequest request,
                                                   @AuthenticationPrincipal UserDetails principal) {
        User currentUser = userService.getByEmail(principal.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GatePassResponse.from(gatePassService.create(request, currentUser)));
    }

    @PostMapping("/{id}/approve")
    @PreAuthorize("hasAnyRole('ADMIN','HOST')")
    public GatePassResponse approve(@PathVariable Long id,
                                    @RequestBody(required = false) DecisionRequest request,
                                    @AuthenticationPrincipal UserDetails principal) {
        User approver = userService.getByEmail(principal.getUsername());
        return GatePassResponse.from(gatePassService.approve(id, approver, remarksOf(request)));
    }

    @PostMapping("/{id}/reject")
    @PreAuthorize("hasAnyRole('ADMIN','HOST')")
    public GatePassResponse reject(@PathVariable Long id,
                                   @RequestBody(required = false) DecisionRequest request,
                                   @AuthenticationPrincipal UserDetails principal) {
        User approver = userService.getByEmail(principal.getUsername());
        return GatePassResponse.from(gatePassService.reject(id, approver, remarksOf(request)));
    }

    @PostMapping("/{id}/cancel")
    @PreAuthorize("hasAnyRole('ADMIN','HOST')")
    public GatePassResponse cancel(@PathVariable Long id) {
        return GatePassResponse.from(gatePassService.cancel(id));
    }

    @PostMapping("/code/{passCode}/check-in")
    @PreAuthorize("hasAnyRole('ADMIN','SECURITY')")
    public GatePassResponse checkIn(@PathVariable String passCode) {
        return GatePassResponse.from(gatePassService.checkIn(passCode));
    }

    @PostMapping("/code/{passCode}/check-out")
    @PreAuthorize("hasAnyRole('ADMIN','SECURITY')")
    public GatePassResponse checkOut(@PathVariable String passCode) {
        return GatePassResponse.from(gatePassService.checkOut(passCode));
    }

    private String remarksOf(DecisionRequest request) {
        return request == null ? null : request.remarks();
    }
}
