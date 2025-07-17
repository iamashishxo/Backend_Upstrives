package com.UpStrives.upstrives.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.UpStrives.upstrives.entity.ExpertFormEntity;

public interface EnityformRepository extends JpaRepository<ExpertFormEntity, Long> {
  // This interface will automatically provide CRUD operations for ExpertFormDto
  // No additional methods are needed unless specific queries are required

}
