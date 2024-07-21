package org.example.attendancebackend.controllers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.attendancebackend.models.Family;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FamilyResponse {
    private String message;
    private Family family;
}
