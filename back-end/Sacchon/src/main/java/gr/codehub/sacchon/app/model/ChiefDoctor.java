package gr.codehub.sacchon.app.model;

import gr.codehub.sacchon.app.SacchonApplication;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * <p>ChiefDoctor is one of the main entities of the Sacchon Diabetes Management application.
 * ChiefDoctor inherits the Doctor class.
 * ChiefDoctor is used to store the necessary data and implement the methods needed concerning the
 * Chief Doctor of Sacchon. It is also annotated with {@link SuperBuilder}
 * to enable the creation of a builder pattern for creating instances of this class.</p>
 *
 * <p>Properties:</p>
 * id: unique identification number
 *
 * @author Georgia Giannokosta - geogiannokosta@gmail.com
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
@Table(schema = SacchonApplication.SCHEMA, name = "ChiefDoctor")
@SuperBuilder
public class ChiefDoctor extends Person {
}
