package org.example.attendancebackend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.attendancebackend.models.Family;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Family family;
    private Integer yajeCount=0;
    private Integer yarasuyeCount=0;
    private Integer yarasuweCount=0;
    private Integer yarafashijeCount=0;
    private Integer yarafashijweCount=0;
    private Integer yize7Count=0;
    private Integer yatangiyeIsabatoCount=0;
    private Integer ararwayeCount=0;
    private Integer abashyitsiCount=0;
    private LocalDateTime issuedDate;
}
