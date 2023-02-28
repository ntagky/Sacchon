package gr.codehub.sacchon.app.model;

import gr.codehub.sacchon.app.SacchonApplication;
import jakarta.persistence.*;
import lombok.*;

/**
 The Medication class represents a medication that is prescribed by a Consultation to a Patient.
 It contains the medication's id, name, dosage and the consultation it was prescribed in.

 It contains the following properties:

 ID: Unique identifier
 MedName: Medication name
 dosage: The dosage of the medication
 Consultation: The consultation in which this medication instance belongs


 @author Christos Tzoulias
 @version 1.0
 @since 2023-02-28
 */


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = SacchonApplication.SCHEMA)
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String medName;
    private String dosage;
    @ManyToOne
    @JoinColumn(name="consultation_id", referencedColumnName = "id")
    private Consultation consultation;
}
