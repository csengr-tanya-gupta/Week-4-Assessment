package com.capgemini.model.repository;

import com.capgemini.model.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface HospitalRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findByEmail(String email);
}