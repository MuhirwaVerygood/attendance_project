package org.example.attendancebackend.models;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class AttendanceObject {
    private Integer memberId;
    private Boolean yaje =false;

    private Boolean yarasuye = false;
    private Boolean yarasuwe = false;
    private Boolean ararwaye = false;
    private Boolean yarafashije = false;
    private Boolean yarafashijwe = false;
    private Boolean yatangiyeIsabato = false;
    private Boolean yize7 = false;
}
