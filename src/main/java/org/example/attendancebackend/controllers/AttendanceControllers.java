package org.example.attendancebackend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.attendancebackend.models.Attendance;
import org.example.attendancebackend.services.AttendanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/attendances")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name="Attendance Controllers", description = "Operations about attendances")
public class AttendanceControllers {
    private final AttendanceService service;

    @GetMapping
    @Operation(summary = "Get all attendances", description = "Endpoint to get all attendances")
    public ResponseEntity<List<Attendance>> getAllAttendances(){
        return service.getAllAttendances();
    }


    @GetMapping("/{family-id}")
    @Operation(summary = "Get attendancies by family id", description = "Endpoint to get all attendancies by family id")
    public ResponseEntity<List<Attendance>> getAllAttendanciesByFamilyId(@PathVariable("family-id") Integer familyId){
        return service.getAllAttendancesByFamilyId(familyId);
    }
    @PostMapping
    @Operation(summary = "Add an attendance", description = "Endpoint to add a new attendance")
    public ResponseEntity<String> addAttendance(@RequestBody AttendanceRequest request){
        return service.addAttendance(request);
    }

    @DeleteMapping("/{attendance-id}")
    @Operation(summary = "Delete attendance by id", description = "Delete attendance by attendance id")
    public ResponseEntity<String> deleteAttendanceById(@PathVariable("attendance-id") Integer attendanceId){
        return service.deleteAttendanceById(attendanceId);
    }






 }
