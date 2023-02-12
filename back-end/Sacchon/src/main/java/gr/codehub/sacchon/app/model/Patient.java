package gr.codehub.sacchon.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String medicalRecordNumber;
    private String address;
    private String gender;
    private LocalDate dateOfBirth;
    private String bloodType;
    private String diabetesType;
    private int height;
    private double weight;

    @ElementCollection
    private List<String> allergies;  //todo: Maybe a class?
    @ElementCollection
    private List<String> medications;
    @ElementCollection
    private List<String> conditions;

    // Doctor doctor  //todo: Implement

}
