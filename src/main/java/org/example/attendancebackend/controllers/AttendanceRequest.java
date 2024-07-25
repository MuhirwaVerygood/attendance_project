package org.example.attendancebackend.controllers;
import jakarta.persistence.Embedded;
import lombok.*;
import org.example.attendancebackend.models.AttendanceObject;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceRequest {
    @Embedded
    private AttendanceObject[] attendanceObject;
   private Integer visitors=0;
}
