package gr.codehub.sacchon.app.model;

import gr.codehub.sacchon.app.SacchonApplication;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalTime;

@Entity
@Table(schema = SacchonApplication.SCHEMA)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class GlucoseRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalTime time;
    private BigDecimal measurement;
    @ManyToOne(optional = false)
    @JoinColumn(name = "glucose_id", referencedColumnName = "id")
    private Glucose glucose;

}
