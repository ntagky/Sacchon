package gr.codehub.sacchon.app.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Glucose implements Measurement<List<GlucoseRecord>> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private final String UNITS = "mg/dL";
    private LocalDate date;
    @OneToMany
    private List<GlucoseRecord> measurement;
    @ManyToOne
    private Patient patient;

}
