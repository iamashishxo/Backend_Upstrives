package com.UpStrives.upstrives.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.UpStrives.upstrives.entity.InternshipFormEntity;

@Repository
public interface InternshipFormRepository extends JpaRepository<InternshipFormEntity, Long> {
}
