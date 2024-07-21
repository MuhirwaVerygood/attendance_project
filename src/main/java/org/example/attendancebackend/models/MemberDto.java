package org.example.attendancebackend.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    private  Integer id;
    private String firstname;
    private String lastname;
    private String className;
    private String familyId;
    private String familyName;
}
