package org.example.attendancebackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "member")
public class Members {
    @Id
    @SequenceGenerator(name = "member_generator", sequenceName = "member_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_generator")
    private Integer id;
    private String firstname;
    private String lastname;
    private String className;
    @ManyToOne
    @JoinColumn(name = "family_id")
    @JsonIgnore
    private Family family;

    //more about  attendance
    @JsonIgnore
    private Boolean yaje;
    @JsonIgnore
    private Boolean yarasuye;
    @JsonIgnore
    private Boolean yarasuwe;
    @JsonIgnore
    private Boolean yarafashije;
    @JsonIgnore
    private Boolean yarafashijwe;
    @JsonIgnore
    private Boolean yatangiyeIsabato;
}
