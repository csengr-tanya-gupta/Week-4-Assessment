package com.capgemini.dto;

import lombok.Data;

@Data
public class DoctorDTO {
    private Long doctorId;
    private String name;
    private String specialization;
    private String email;
    private String phone;
    private int totalAppointments;
}