package gr.codehub.sacchon.app.dto;

import gr.codehub.sacchon.app.model.Consultation;
import gr.codehub.sacchon.app.model.Medication;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConsultationDto {
    private Long id;
    private String doctorFirstName;
    private String doctorLastName;
    private String doctorEmail;
    private LocalDate dateCreated;
    private boolean seenConsultation;
    private List<Medication> medications;
    private String details;

    public ConsultationDto(Consultation consultation){
        if(consultation!=null){
            id = consultation.getId();
            doctorFirstName = consultation.getDoctor().getFirstName();
            doctorLastName = consultation.getDoctor().getLastName();
            doctorEmail = consultation.getDoctor().getEmail();
            dateCreated = consultation.getDateCreated();
            seenConsultation = consultation.isSeenConsultation();
            medications = consultation.getMedications();
            details = consultation.getDetails();
        }
    }
}