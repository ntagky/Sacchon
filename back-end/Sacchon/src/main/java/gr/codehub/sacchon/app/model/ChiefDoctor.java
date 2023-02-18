package gr.codehub.sacchon.app.model;

import gr.codehub.sacchon.app.SacchonApplication;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>ChiefDoctor is one of the main entities of the Sacchon Diabetes Management application.
 * ChiefDoctor inherits the Doctor class.
 * ChiefDoctor is used to store the necessary data and implement the methods needed concerning the Chief Doctor of Sacchon.</p>
 *
 * <p>Properties:</p>
 * id: unique identification number
 *
 * @author Georgia Giannokosta - geogiannokosta@gmail.com
 */
@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(schema = SacchonApplication.DEBUG_MODE ? "develop" : "production")
public class ChiefDoctor extends Doctor {
}
