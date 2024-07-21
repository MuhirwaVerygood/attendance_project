package org.example.attendancebackend.services;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.attendancebackend.models.Members;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FamilyRequest {
    private String familyName;
    private Members[] members;
}
