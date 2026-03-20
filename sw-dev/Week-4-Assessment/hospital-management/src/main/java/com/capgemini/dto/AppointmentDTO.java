package com.capgemini.dto;

import jakarta.validation.constraints.Future;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentDTO {

    private Long appointmentId;

    private String patientName;

    @Future
    private LocalDateTime scheduledTime;

    private String status;
}