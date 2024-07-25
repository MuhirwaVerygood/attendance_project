package org.example.attendancebackend.services;

import lombok.RequiredArgsConstructor;
import org.example.attendancebackend.controllers.AttendanceRequest;
import org.example.attendancebackend.models.Attendance;
import org.example.attendancebackend.models.AttendanceObject;
import org.example.attendancebackend.models.Family;
import org.example.attendancebackend.repositories.AttendanceRepository;
import org.example.attendancebackend.repositories.FamilyRepository;
import org.example.attendancebackend.repositories.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceRepository repository;
    private final MemberRepository memberRepository;
    private final FamilyRepository familyRepository;
    public ResponseEntity<List<Attendance>> getAllAttendances() {
        return ResponseEntity.status(200).body(repository.findAll());
    };

    public ResponseEntity<String> addAttendance(AttendanceRequest request) {
        Attendance attendance = new Attendance();
        for (AttendanceObject ao : request.getAttendanceObject()) {
            if (memberRepository.existsById(ao.getMemberId())) {
                Family family = memberRepository.findById(ao.getMemberId()).get().getFamily();
                attendance.setFamily(family);
                attendance.setAbashyitsiCount(request.getVisitors());
                if (ao.getYaje().equals(true)) {
                    attendance.setYajeCount(attendance.getYajeCount() + 1);
                }
                if (ao.getArarwaye().equals(true)) {
                    attendance.setArarwayeCount(attendance.getArarwayeCount() + 1);
                }
                if (ao.getYarasuye().equals(true)) {
                    attendance.setYarasuyeCount(attendance.getYarasuyeCount() + 1);
                }
                if (ao.getYarasuwe().equals(true)) {
                    attendance.setYarasuweCount(attendance.getYarasuweCount() + 1);
                }
                if (ao.getYarafashije().equals(true)) {
                    attendance.setYarafashijeCount(attendance.getYarafashijeCount() + 1);
                }

                if (ao.getYarafashijwe().equals(true)) {
                    attendance.setYarafashijweCount(attendance.getYarafashijweCount() + 1);
                }
                if (ao.getYize7().equals(true)) {
                    attendance.setYize7Count(attendance.getYize7Count() + 1);
                }
                if (ao.getYatangiyeIsabato().equals(true)) {
                    attendance.setYatangiyeIsabatoCount(attendance.getYatangiyeIsabatoCount() + 1);
                }
            } else {
                return ResponseEntity.status(404).body("Member with that id not found");
            }
        }
        attendance.setIssuedDate(LocalDateTime.now());
        repository.save(attendance);
        return ResponseEntity.ok("Attendance added successfully");
    }


    public ResponseEntity<List<Attendance>> getAllAttendancesByFamilyId(Integer familyId) {
        try{
            if(familyRepository.existsById(familyId)){
                return ResponseEntity.ok(familyRepository.findById(familyId).get().getAttendances());
            }else{
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Family not found");
            }
        }catch (ResponseStatusException e){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "Family not found");
        }
    }

    public ResponseEntity<String> deleteAttendanceById(Integer attendanceId) {
        if(repository.existsById(attendanceId)){
            repository.deleteById(attendanceId);
            return new ResponseEntity<>("Attendance added successfully",HttpStatus.OK );
        }else{
            return ResponseEntity.status(404).body("Attendance not found");
        }
    }
}
