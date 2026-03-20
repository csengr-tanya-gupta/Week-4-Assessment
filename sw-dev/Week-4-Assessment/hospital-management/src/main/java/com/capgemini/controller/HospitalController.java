package com.capgemini.controller;

import com.capgemini.dto.*;
import com.capgemini.model.entity.*;
import com.capgemini.model.service.HospitalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class HospitalController {

    @Autowired
    private HospitalService service;

    // DOCTOR APIs
    @PostMapping("/doctors")
    public DoctorDTO createDoctor(@RequestBody Doctor doctor) {
        return service.createDoctor(doctor);
    }

    @GetMapping("/doctors")
    public List<Doctor> getAllDoctors() {
        return service.getAllDoctors();
    }

    // APPOINTMENT APIs
    @PostMapping("/doctors/{doctorId}/appointments")
    public Appointment createAppointment(
            @PathVariable Long doctorId,
            @Valid @RequestBody AppointmentDTO dto) {

        return service.createAppointment(doctorId, dto);
    }

    @GetMapping("/doctors/{doctorId}/appointments")
    public Page<Appointment> getAppointments(
            @PathVariable Long doctorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        return service.getAppointments(doctorId, page, size);
    }

    // SPECIAL API
    @GetMapping("/appointments/pending")
    public List<Appointment> getPending() {
        return service.getPendingAppointments();
    }
}