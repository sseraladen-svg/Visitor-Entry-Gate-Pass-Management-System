package com.college.visitorgatepass.dto;

import com.college.visitorgatepass.dto.AuthDtos.UserResponse;
import com.college.visitorgatepass.dto.VisitorDtos.VisitorRequest;
import com.college.visitorgatepass.dto.VisitorDtos.VisitorResponse;
import com.college.visitorgatepass.model.GatePass;
import com.college.visitorgatepass.model.GatePassStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public final class GatePassDtos {

    private GatePassDtos() {
    }

    /**
     * Either an existing {@code visitorId} or the details of a new {@code visitor} must be supplied.
     */
    public record GatePassRequest(
            Long visitorId,
            @Valid VisitorRequest visitor,
            @NotNull Long hostId,
            @NotBlank String purpose,
            @NotNull LocalDateTime expectedEntry,
            @NotNull @Future LocalDateTime expectedExit,
            String vehicleNumber,
            @Min(1) Integer numberOfVisitors,
            String remarks) {
    }

    public record DecisionRequest(String remarks) {
    }

    public record GatePassResponse(
            Long id,
            String passCode,
            VisitorResponse visitor,
            UserResponse host,
            UserResponse createdBy,
            UserResponse approvedBy,
            String purpose,
            LocalDateTime expectedEntry,
            LocalDateTime expectedExit,
            GatePassStatus status,
            String vehicleNumber,
            int numberOfVisitors,
            String remarks,
            LocalDateTime checkInTime,
            LocalDateTime checkOutTime,
            LocalDateTime createdAt) {

        public static GatePassResponse from(GatePass pass) {
            return new GatePassResponse(
                    pass.getId(),
                    pass.getPassCode(),
                    VisitorResponse.from(pass.getVisitor()),
                    UserResponse.from(pass.getHost()),
                    pass.getCreatedBy() == null ? null : UserResponse.from(pass.getCreatedBy()),
                    pass.getApprovedBy() == null ? null : UserResponse.from(pass.getApprovedBy()),
                    pass.getPurpose(),
                    pass.getExpectedEntry(),
                    pass.getExpectedExit(),
                    pass.getStatus(),
                    pass.getVehicleNumber(),
                    pass.getNumberOfVisitors(),
                    pass.getRemarks(),
                    pass.getCheckInTime(),
                    pass.getCheckOutTime(),
                    pass.getCreatedAt());
        }
    }

    public record DashboardStats(
            long totalVisitors,
            long totalPasses,
            long pendingPasses,
            long approvedPasses,
            long checkedInPasses,
            long visitorsInsideNow,
            long checkInsToday) {
    }
}
