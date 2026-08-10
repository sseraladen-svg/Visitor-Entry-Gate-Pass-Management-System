package com.college.visitorgatepass.controller;

import com.college.visitorgatepass.dto.VisitorDtos.VisitorRequest;
import com.college.visitorgatepass.dto.VisitorDtos.VisitorResponse;
import com.college.visitorgatepass.service.VisitorService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/visitors")
public class VisitorController {

    private final VisitorService visitorService;

    public VisitorController(VisitorService visitorService) {
        this.visitorService = visitorService;
    }

    @GetMapping
    public List<VisitorResponse> list(@RequestParam(required = false) String query) {
        return visitorService.search(query).stream().map(VisitorResponse::from).toList();
    }

    @GetMapping("/{id}")
    public VisitorResponse get(@PathVariable Long id) {
        return VisitorResponse.from(visitorService.getById(id));
    }

    @PostMapping
    public ResponseEntity<VisitorResponse> create(@Valid @RequestBody VisitorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(VisitorResponse.from(visitorService.create(request)));
    }

    @PutMapping("/{id}")
    public VisitorResponse update(@PathVariable Long id, @Valid @RequestBody VisitorRequest request) {
        return VisitorResponse.from(visitorService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        visitorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
