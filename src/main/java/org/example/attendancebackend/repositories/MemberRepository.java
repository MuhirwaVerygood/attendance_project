package org.example.attendancebackend.repositories;

import org.example.attendancebackend.models.Members;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Members, Integer> {
    List<Members> findMembersByFamilyId(Integer id);
}
