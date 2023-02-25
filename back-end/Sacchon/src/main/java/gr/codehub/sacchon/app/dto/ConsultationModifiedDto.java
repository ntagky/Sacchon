package gr.codehub.sacchon.app.dto;

import gr.codehub.sacchon.app.model.Consultation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConsultationModifiedDto {
    private Long id;
    private String details;

    public ConsultationModifiedDto(Consultation consultation){
        if(consultation!=null){
            id = consultation.getId();
            details = consultation.getDetails();
        }
    }
//
//    public Consultation asConsultation(){
//        Consultation consultation = new Consultation();
//        consultation.setId(id);
//        consultation.setSeenConsultation(false);
//        consultation.setDetails(details);
//        return consultation;
//    }
}