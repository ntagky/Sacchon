package gr.codehub.sacchon.app.model;

import gr.codehub.sacchon.app.SacchonApplication;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

/**
 The Glucose class represents a glucose measurement for a specific patient at a certain date.
 It implements the Measurement interface with a list of GlucoseRecord measurements.

 It contains the following properties:

 ID: Unique Identifier
 Units: Units of measurement
 Date: Date of Measurement
 Measurement: The value of the measurement
 Patient: Owning patient

 @author Christos Tzoulias
 @version 1.0
 @since 2023-02-28
 */

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
