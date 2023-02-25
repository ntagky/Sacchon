package gr.codehub.sacchon.app.model;

import gr.codehub.sacchon.app.SacchonApplication;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

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
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = SacchonApplication.SCHEMA)
public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String doctorFirstName; // in case the doctor leaves the system - referral
    private String doctorLastName; // in case the doctor leaves the system - referral
    private String doctorEmail; // in case the doctor leaves the system  - referral
    private LocalDate dateCreated;
    private boolean seenConsultation;

    @OneToMany(mappedBy = "consultation", cascade = CascadeType.REMOVE)
    private List<Medication> medications;
    private String details;
    @ManyToOne // me ti logiki oti to consultation de tha syndeetai pleon me ton giatro, an fygei apo to systima, ara mporei na meinei xwris referenced giatro
    @JoinColumn(name="doctor_id", referencedColumnName = "id")
    private Doctor doctor;

    @ManyToOne(optional = false) // me ti logiki oti o asthenis de mporei na meinei xwris consultation
    @JoinColumn(name="patient_id", referencedColumnName = "id")
    private Patient patient;
}
