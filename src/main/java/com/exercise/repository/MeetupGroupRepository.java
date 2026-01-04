package com.exercise.repository;

import com.exercise.entity.MeetupGroup;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeetupGroupRepository extends JpaRepository<MeetupGroup, Long> {
    Optional<MeetupGroup> findByName(String name);

    @EntityGraph(attributePaths = {"events"})
    @Query("SELECT mg FROM MeetupGroup mg")
    List<MeetupGroup> findAllWithEvents();

    @EntityGraph(attributePaths = {"events"})
    @Query("SELECT mg FROM MeetupGroup mg WHERE mg.id = :id")
    Optional<MeetupGroup> findByIdWithEvents(@Param("id") Long id);
}

