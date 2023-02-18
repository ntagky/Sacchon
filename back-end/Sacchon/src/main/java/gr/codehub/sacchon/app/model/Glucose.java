package gr.codehub.sacchon.app.model;

import gr.codehub.sacchon.app.SacchonApplication;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(schema = SacchonApplication.SCHEMA)
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
    @OneToMany(mappedBy = "glucose")
    private List<GlucoseRecord> measurement;
    @ManyToOne(optional = false)
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

}
