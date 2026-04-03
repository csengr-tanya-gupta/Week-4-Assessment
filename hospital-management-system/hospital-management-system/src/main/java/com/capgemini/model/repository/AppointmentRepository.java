package com.capgemini.model.repository;

import com.capgemini.model.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.*;
import java.util.*;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Page<Appointment> findByDoctorDoctorId(Long doctorId, Pageable pageable);

    Optional<Appointment> findByAppointmentIdAndDoctorDoctorId(Long apptId, Long doctorId);

    List<Appointment> findByStatusOrderByScheduledTimeAsc(Appointment.Status status);
}