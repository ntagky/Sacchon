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
    private Long id;
    private final String UNITS = "mg/dL";
    private LocalDate date;
    @OneToMany(mappedBy = "glucose", cascade = CascadeType.REMOVE)
    private List<GlucoseRecord> measurement;
    @ManyToOne(optional = false,cascade = CascadeType.REMOVE)
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

}
