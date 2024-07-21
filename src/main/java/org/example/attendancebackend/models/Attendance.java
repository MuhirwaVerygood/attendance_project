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
    private Integer yajeCount;
    private Integer yarasuyeCount;
    private Integer yarasuweCount;
    private Integer yarafashijeCount;
    private Integer yarafashijweCount;
    private Integer yize7Count;
    private Integer yatangiyeIsabatoCount;
    private Integer ararwayeCount;
    private Integer abashyitsiCount;
    private LocalDateTime issuedDate;
}
