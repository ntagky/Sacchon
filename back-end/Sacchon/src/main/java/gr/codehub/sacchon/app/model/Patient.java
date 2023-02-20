package gr.codehub.sacchon.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gr.codehub.sacchon.app.SacchonApplication;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(schema = SacchonApplication.SCHEMA)
@NoArgsConstructor
@AllArgsConstructor
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
    private List<String> allergies;
    @ElementCollection
    private List<String> medications;
    @ElementCollection
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
    @ManyToOne(optional = true)
    @JoinColumn(name="doctor_id", referencedColumnName = "id")
    private Doctor doctor;

}
