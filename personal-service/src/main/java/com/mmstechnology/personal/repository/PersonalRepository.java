package com.mmstechnology.personal.repository;

import com.mmstechnology.personal.model.Personal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalRepository extends JpaRepository<Personal, Long> {}
