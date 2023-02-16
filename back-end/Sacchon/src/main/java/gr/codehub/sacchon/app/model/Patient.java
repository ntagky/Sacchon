package gr.codehub.sacchon.app.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Patient extends Person {

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

    @OneToMany
    private List<Consultation> consultations;
    @OneToMany
    private List<Carbs> carbs;
    @OneToMany
    private List<Glucose> glucose;
    @ManyToOne
    private Doctor doctor;

}
