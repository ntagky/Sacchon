package gr.codehub.sacchon.app.dto;

import gr.codehub.sacchon.app.model.Consultation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class ConsultationDto {
    private int id;
    private String doctorName; // ayto mipws den xreiazetai giati tha to pairnei ap ti vasi afotou sindethoun oi pinakes?
    private LocalDate dateCreated;
    private LocalDate dateChanged;
    private LocalDate seenConsultation;
    //    private List<Medication> medications;
    private String details;

    public ConsultationDto(Consultation consultation){
        if(consultation!=null){
            id = consultation.getId();
            doctorName = consultation.getDoctorName();
            dateCreated = consultation.getDateCreated();
            dateChanged = consultation.getDateChanged();
            seenConsultation = consultation.getSeenConsultation();
//            medications = consultation.getMedications();
            details = consultation.getDetails();
        }
    }

    public Consultation asConsultation(){
        Consultation consultation = new Consultation();
        consultation.setId(id);
        consultation.setDateCreated(dateCreated);
        consultation.setDateCreated(dateChanged);
        consultation.setDateCreated(seenConsultation);
//        consultation.setDateCreated(medications);
        consultation.setDetails(details);
        return consultation;
    }
}