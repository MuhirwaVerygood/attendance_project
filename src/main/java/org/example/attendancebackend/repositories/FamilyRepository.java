package org.example.attendancebackend.repositories;

import org.example.attendancebackend.models.Family;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FamilyRepository extends JpaRepository<Family, Integer> {
    Optional<Family> findByFamilyName(String name);
    Optional<Family> findById(Integer id);
}
