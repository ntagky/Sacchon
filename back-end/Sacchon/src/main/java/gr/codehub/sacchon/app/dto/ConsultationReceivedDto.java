package gr.codehub.sacchon.app.dto;

import gr.codehub.sacchon.app.model.Consultation;
import gr.codehub.sacchon.app.model.Medication;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConsultationReceivedDto {
    private Long id;
    private boolean seenConsultation;
    private List<Medication> medications;
    private String details;

    public ConsultationReceivedDto(Consultation consultation){
        if(consultation!=null){
            id = consultation.getId();
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