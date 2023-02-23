package gr.codehub.sacchon.app.dto;

import gr.codehub.sacchon.app.model.Consultation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConsultationModifiedDto {
    private Long id;
    private String doctorFirstName;
    private String doctorLastName;
    private String doctorEmail;
    private boolean seenConsultation;
    private List<String> medications;
    private String details;

    public ConsultationModifiedDto(Consultation consultation){
        if(consultation!=null){
            id = consultation.getId();
            doctorFirstName = consultation.getDoctor().getFirstName();
            doctorLastName = consultation.getDoctor().getLastName();
            doctorEmail = consultation.getDoctor().getEmail();
            seenConsultation = consultation.isSeenConsultation();
            medications = consultation.getMedications();
            details = consultation.getDetails();
        }
    }

    public Consultation asConsultation(){
        Consultation consultation = new Consultation();
        consultation.setId(id);
        consultation.setSeenConsultation(seenConsultation);
        consultation.setMedications(medications);
        consultation.setDetails(details);
        return consultation;
    }
}