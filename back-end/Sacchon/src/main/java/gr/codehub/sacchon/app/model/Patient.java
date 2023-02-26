package gr.codehub.sacchon.app.model;

import gr.codehub.sacchon.app.SacchonApplication;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(schema = SacchonApplication.SCHEMA, name = "Patient")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Patient extends Person {

    private String medicalRecordNumber;
    private String address;
    private String gender;
    private LocalDate dateOfBirth;
    @Enumerated(EnumType.STRING)
    private BloodType bloodType;
    @Enumerated(EnumType.STRING)
    private DiabetesType diabetesType;
    private int height;
    private double weight;



    @ElementCollection
    @CollectionTable(schema = SacchonApplication.SCHEMA)
    private List<String> allergies;
//    @ElementCollection
//    @CollectionTable(schema = SacchonApplication.SCHEMA)
//    private List<String> medications;
    @ElementCollection
    @CollectionTable(schema = SacchonApplication.SCHEMA)
    private List<String> conditions;

    @OneToMany(mappedBy = "patient")
    private List<Consultation> consultations;
    @OneToMany(mappedBy = "patient")
    private List<Carbs> carbs;
    @OneToMany(mappedBy = "patient")
    private List<Glucose> glucose;

    // @ManyToOne -> by default: eager loading
    // optional = false -> de mporei na mhn exei doctor. To default einai true
    // @ManyToOne(optional = true, cascade = CascadeType.PERSIST)
    @ManyToOne()
    @JoinColumn(name="doctor_id", referencedColumnName = "id")
    private Doctor doctor;



}
