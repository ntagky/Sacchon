package gr.codehub.sacchon.app.model;

import gr.codehub.sacchon.app.SacchonApplication;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(schema = SacchonApplication.SCHEMA)
public class Carbs implements Measurement<Double> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private final String UNITS = "g";
    private LocalDate date;
    private Double measurement;
    @ManyToOne(optional = false)
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

}
