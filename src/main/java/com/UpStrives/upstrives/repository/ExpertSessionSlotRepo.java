package com.UpStrives.upstrives.repository;


import com.UpStrives.upstrives.entity.ExpertSessionSlot;
import com.UpStrives.upstrives.entity.User;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ExpertSessionSlotRepo extends JpaRepository<ExpertSessionSlot, Long> {
    List<ExpertSessionSlot> findBySessionDateAndStatus(LocalDate date, ExpertSessionSlot.Status status);
    List<ExpertSessionSlot> findBySessionDate(LocalDate sessionDate);
    Optional<ExpertSessionSlot> findBySessionDateAndStartTime(LocalDate date, LocalTime time);
    long countBySessionDate(LocalDate date);
    long countBySessionDateAndStatus(LocalDate date, ExpertSessionSlot.Status status); 
    List<ExpertSessionSlot> findByUserAndSessionDateGreaterThanEqualOrderBySessionDateAscStartTimeAsc(User user, LocalDate date);
    
    //clean up old unbooked slots from the expert_session table
    @Transactional
    @Modifying
    @Query("DELETE FROM ExpertSessionSlot s WHERE s.sessionDate < :today AND s.status <> 'BOOKED'")
    void deleteOldUnbookedSlots(@Param("today") LocalDate today);
}
