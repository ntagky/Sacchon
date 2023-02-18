package gr.codehub.sacchon.app.model;

import gr.codehub.sacchon.app.SacchonApplication;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(schema = SacchonApplication.SCHEMA)
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
    @Enumerated(EnumType.STRING)
    private BloodType bloodType;
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
    @OneToMany(mappedBy = "patient")
    private List<Carbs> carbs;
    @OneToMany(mappedBy = "patient")
    private List<Glucose> glucose;
    @ManyToOne
    private Doctor doctor;

}
