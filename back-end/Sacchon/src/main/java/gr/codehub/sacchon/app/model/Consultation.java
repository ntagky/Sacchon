package gr.codehub.sacchon.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * <p>Consultation is one of the main entities of the Sacchon Diabetes Management application.
 * Consultation class is connected with the Doctor, Patient and Medication classes.
 * Consultation class is used to store the necessary data and implement the methods needed concerning the doctors' prescription to the patients.</p>
 *
 * <p>Properties:</p>
 * id: unique identification number <br>
 * doctorName: the name of the doctor that prescribed this consultation <br>
 * dateCreated: the local date that the consultation was created <br>
 * seenConsultation: boolean variable that becomes true when the patient opens their new consultation <br>
 * List<Medication>: the list of medications (name and dosage) the doctor prescribed <br>
 * details: further instructions / details that the doctor may give to the patient*
 *
 * @author Georgia Giannokosta - geogiannokosta@gmail.com
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String doctorName;
    private LocalDate dateCreated;
    private boolean seenConsultation;
//    private List<Medication> medications;
    private String details;
}
