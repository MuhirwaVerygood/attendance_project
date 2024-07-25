package org.example.attendancebackend.repositories;

import org.example.attendancebackend.models.Attendance;
import org.example.attendancebackend.models.Family;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
    Optional<Attendance> findByFamilyAndIssuedDate(Family family, LocalDateTime issuedDate);
    List<Attendance> findByFamilyId(Integer id);
}
