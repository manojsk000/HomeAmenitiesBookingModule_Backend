package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.module.Facility;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {
	Facility findByName(String name);
}
