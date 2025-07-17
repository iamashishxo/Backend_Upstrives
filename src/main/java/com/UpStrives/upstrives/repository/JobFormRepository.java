package com.UpStrives.upstrives.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.UpStrives.upstrives.entity.JobFormEntity;

@Repository
public interface JobFormRepository extends JpaRepository<JobFormEntity, Long> {
  // Define custom query methods if needed

}
