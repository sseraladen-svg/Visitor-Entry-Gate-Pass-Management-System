package com.college.visitorgatepass.dto;

import com.college.visitorgatepass.model.Visitor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;

public final class VisitorDtos {

    private VisitorDtos() {
    }

    public record VisitorRequest(
            @NotBlank String fullName,
            @NotBlank @Pattern(regexp = "\\d{10,15}", message = "phone must be 10-15 digits") String phone,
            @Email String email,
            String company,
            String idProofType,
            String idProofNumber,
            String address) {
    }

    public record VisitorResponse(
            Long id,
            String fullName,
            String phone,
            String email,
            String company,
            String idProofType,
            String idProofNumber,
            String address,
            LocalDateTime createdAt) {

        public static VisitorResponse from(Visitor visitor) {
            return new VisitorResponse(visitor.getId(), visitor.getFullName(), visitor.getPhone(), visitor.getEmail(),
                    visitor.getCompany(), visitor.getIdProofType(), visitor.getIdProofNumber(), visitor.getAddress(),
                    visitor.getCreatedAt());
        }
    }
}
