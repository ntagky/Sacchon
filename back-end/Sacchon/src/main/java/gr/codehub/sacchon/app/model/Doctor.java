package gr.codehub.sacchon.app.model;

import gr.codehub.sacchon.app.SacchonApplication;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


/**
 * <p>Doctor is one of the main entities of the Sacchon Diabetes Management application.
 * Doctor inherits the Person class and is connected with the Patient, Consultation and Chief Doctor classes.
 * Doctor class is used to store the necessary data and implement the methods needed concerning the doctors enrolled in the application.</p>
 *
 * <p>Properties:</p>
 * id: unique identification number
 * <br>
 * List Patient: the list of Patients the Doctor is consulting at the momemnt
 * <br>
 * List Consultation: the list of consultations the Doctor has prescribed
 *
 * @author Georgia Giannokosta - geogiannokosta@gmail.com
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = SacchonApplication.DEBUG_MODE ? "develop" : "production")
public class Doctor extends Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany
    private List<Patient> patients;

    @OneToMany
    private List<Consultation> consultations; //todo: connection with Consultation class
}
