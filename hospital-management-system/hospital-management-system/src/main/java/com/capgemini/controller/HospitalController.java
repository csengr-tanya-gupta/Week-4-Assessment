package com.capgemini.controller;

import com.capgemini.model.entity.*;
import com.capgemini.model.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class HospitalController {

    @Autowired
    private HospitalService service;

    // ===== DOCTOR APIs =====

    @PostMapping("/doctors")
    public Doctor createDoctor(@RequestBody Doctor doctor) {
        return service.createDoctor(doctor);
    }

    @GetMapping("/doctors")
    public List<Doctor> getAllDoctors() {
        return service.getAllDoctors();
    }

    @GetMapping("/doctors/{id}")
    public Doctor getDoctor(@PathVariable Long id) {
        return service.getDoctor(id);
    }

    @PutMapping("/doctors/{id}")
    public Doctor updateDoctor(@PathVariable Long id, @RequestBody Doctor doctor) {
        return service.updateDoctor(id, doctor);
    }

    @DeleteMapping("/doctors/{id}")
    public String deleteDoctor(@PathVariable Long id) {
        service.deleteDoctor(id);
        return "Doctor deleted successfully";
    }

    // ===== APPOINTMENT APIs =====

    @PostMapping("/doctors/{doctorId}/appointments")
    public Appointment createAppointment(@PathVariable Long doctorId,
                                         @RequestBody Appointment appt) {
        return service.createAppointment(doctorId, appt);
    }

    @GetMapping("/doctors/{doctorId}/appointments")
    public Page<Appointment> getAppointments(@PathVariable Long doctorId,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "5") int size) {
        return service.getAppointments(doctorId, page, size);
    }

    @GetMapping("/doctors/{doctorId}/appointments/{apptId}")
    public Appointment getAppointment(@PathVariable Long doctorId,
                                      @PathVariable Long apptId) {
        return service.getAppointment(doctorId, apptId);
    }

    @PutMapping("/doctors/{doctorId}/appointments/{apptId}")
    public Appointment updateAppointment(@PathVariable Long doctorId,
                                         @PathVariable Long apptId,
                                         @RequestBody Appointment appt) {
        return service.updateAppointment(doctorId, apptId, appt);
    }

    @DeleteMapping("/doctors/{doctorId}/appointments/{apptId}")
    public String deleteAppointment(@PathVariable Long doctorId,
                                    @PathVariable Long apptId) {
        service.deleteAppointment(doctorId, apptId);
        return "Appointment deleted successfully";
    }

    // ===== SPECIAL API =====

    @GetMapping("/appointments/pending")
    public List<Appointment> getPendingAppointments() {
        return service.getPendingAppointments();
    }
}