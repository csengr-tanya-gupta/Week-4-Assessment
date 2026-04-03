package com.capgemini.model.service;

import com.capgemini.model.entity.*;
import com.capgemini.model.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class HospitalService {

    @Autowired
    private HospitalRepository doctorRepo;

    @Autowired
    private AppointmentRepository appointmentRepo;

    // ================= DOCTOR =================

    public Doctor createDoctor(Doctor doctor) {
        if (doctorRepo.findByEmail(doctor.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }
        return doctorRepo.save(doctor);
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepo.findAll();
    }

    public Doctor getDoctor(Long id) {
        return doctorRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
    }

    public Doctor updateDoctor(Long id, Doctor updated) {
        Doctor doctor = getDoctor(id);

        doctor.setName(updated.getName());
        doctor.setSpecialization(updated.getSpecialization());
        doctor.setPhone(updated.getPhone());

        return doctorRepo.save(doctor);
    }

    public void deleteDoctor(Long id) {
        Doctor doctor = getDoctor(id);
        doctorRepo.delete(doctor);
    }

    // ================= APPOINTMENT =================

    public Appointment createAppointment(Long doctorId, Appointment appt) {
        Doctor doctor = getDoctor(doctorId);

        if (appt.getScheduledTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Scheduled time must be future");
        }

        appt.setDoctor(doctor);
        return appointmentRepo.save(appt);
    }

    public Page<Appointment> getAppointments(Long doctorId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("scheduledTime").ascending());
        return appointmentRepo.findByDoctorDoctorId(doctorId, pageable);
    }

    public Appointment getAppointment(Long doctorId, Long apptId) {
        return appointmentRepo.findByAppointmentIdAndDoctorDoctorId(apptId, doctorId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
    }

    public Appointment updateAppointment(Long doctorId, Long apptId, Appointment updated) {
        Appointment appt = getAppointment(doctorId, apptId);

        if (updated.getScheduledTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Scheduled time must be future");
        }

        appt.setPatientName(updated.getPatientName());
        appt.setScheduledTime(updated.getScheduledTime());
        appt.setStatus(updated.getStatus());

        return appointmentRepo.save(appt);
    }

    public void deleteAppointment(Long doctorId, Long apptId) {
        Appointment appt = getAppointment(doctorId, apptId);
        appointmentRepo.delete(appt);
    }

    public List<Appointment> getPendingAppointments() {
        return appointmentRepo.findByStatusOrderByScheduledTimeAsc(Appointment.Status.PENDING);
    }
}