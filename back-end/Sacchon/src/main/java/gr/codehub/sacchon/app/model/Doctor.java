package gr.codehub.sacchon.app.model;

import gr.codehub.sacchon.app.SacchonApplication;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

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
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = SacchonApplication.SCHEMA, name = "Doctor")
@SuperBuilder
public class Doctor extends Person {

    @OneToMany(mappedBy = "doctor") //by default: lazy loading
    private List<Patient> patients;

    @OneToMany(mappedBy = "doctor") //by default: lazy loading
    private List<Consultation> consultations;
}
