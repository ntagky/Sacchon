package gr.codehub.sacchon.app.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Carbs implements Measurement<Double> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private final String UNITS = "g";
    private LocalDate date;
    private Double measurement;
    @ManyToOne
    private Patient patient;

}
