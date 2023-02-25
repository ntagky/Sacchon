package gr.codehub.sacchon.app.model;

import gr.codehub.sacchon.app.SacchonApplication;
import jakarta.persistence.*;
import lombok.*;

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
