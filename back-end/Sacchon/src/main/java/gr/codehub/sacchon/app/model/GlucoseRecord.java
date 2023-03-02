package gr.codehub.sacchon.app.model;

import gr.codehub.sacchon.app.SacchonApplication;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalTime;

/**
 A class that represents a glucose record which contains a time, a measurement,
 and a reference to the parent glucose entity.
 It contains the following properties:

 ID: Unique Identifier
 Time: Time of Measurement
 Measurement: The value of the measurement
 Glucose: Owning glucose class

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
public class GlucoseRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalTime time;
    private BigDecimal measurement;
    @ManyToOne
    @JoinColumn(name = "glucose_id", referencedColumnName = "id")
    private Glucose glucose;

}
