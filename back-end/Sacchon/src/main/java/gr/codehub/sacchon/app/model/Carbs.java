package gr.codehub.sacchon.app.model;

import gr.codehub.sacchon.app.SacchonApplication;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * <p>Carbs is one of the sub entities of the Sacchon Diabetes Management application.
 * Carbs is used to store the patients carbohydrates data
 *
 * Properties
 * id: unique identification number
 * Units: Units of measurement
 * Date: Date measured
 * Measurement: Measurement value
 * Patient: Owning patient
 *
 * @author Georgia Giannokosta - geogiannokosta@gmail.com
 * @version 1.0
 * @since 2023-02-28
 */

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(schema = SacchonApplication.SCHEMA)
public class Carbs implements Measurement<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private final String UNITS = "g";
    private LocalDate date;
    private Integer measurement;
    @ManyToOne(optional = false)
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient; // ???

}
