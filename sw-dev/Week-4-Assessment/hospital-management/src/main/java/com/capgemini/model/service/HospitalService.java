package com.capgemini.model.service;

import com.capgemini.dto.*;
import com.capgemini.exception.*;
import com.capgemini.model.entity.*;
import com.capgemini.model.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HospitalService {

    @Autowired
    private DoctorRepository doctorRepo;

    @Autowired
    private AppointmentRepository apptRepo;

    // CREATE DOCTOR
    public DoctorDTO createDoctor(Doctor doctor) {

        doctorRepo.findByEmail(doctor.getEmail())
                .ifPresent(d -> { throw new DuplicateEmailException("Email already registered"); });

        Doctor saved = doctorRepo.save(doctor);

        DoctorDTO dto = new DoctorDTO();
        dto.setDoctorId(saved.getDoctorId());
        dto.setName(saved.getName());
        dto.setSpecialization(saved.getSpecialization());
        dto.setEmail(saved.getEmail());
        dto.setPhone(saved.getPhone());
        dto.setTotalAppointments(0);

        return dto;
    }

    // GET ALL DOCTORS
    public List<Doctor> getAllDoctors() {
        return doctorRepo.findAll();
    }

    // CREATE APPOINTMENT
    public Appointment createAppointment(Long doctorId, AppointmentDTO dto) {

        Doctor doctor = doctorRepo.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

        Appointment appt = new Appointment();
        appt.setPatientName(dto.getPatientName());
        appt.setScheduledTime(dto.getScheduledTime());
        appt.setStatus(AppointmentStatus.valueOf(dto.getStatus()));
        appt.setDoctor(doctor);

        return apptRepo.save(appt);
    }

    // PAGINATED APPOINTMENTS
    public Page<Appointment> getAppointments(Long doctorId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("scheduledTime").ascending());

        return apptRepo.findAll(pageable);
    }

    // PENDING SORTED
    public List<Appointment> getPendingAppointments() {
        return apptRepo.findByStatusOrderByScheduledTimeAsc(AppointmentStatus.PENDING);
    }
}