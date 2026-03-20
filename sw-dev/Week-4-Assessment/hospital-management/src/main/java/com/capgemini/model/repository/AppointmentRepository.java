package com.capgemini.model.repository;

import com.capgemini.model.entity.Appointment;
import com.capgemini.model.entity.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByStatusOrderByScheduledTimeAsc(AppointmentStatus status);

    List<Appointment> findByDoctorDoctorId(Long doctorId);
}