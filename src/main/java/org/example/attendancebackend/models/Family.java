package org.example.attendancebackend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "family")
public class Family {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String familyName;
    @OneToMany(mappedBy = "family", fetch = FetchType.LAZY)
    private List<Members> members;
    @OneToMany(mappedBy = "family", fetch = FetchType.LAZY)
    private List<Attendance> attendances;
    private int memberCount = 0;

}