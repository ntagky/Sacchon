package gr.codehub.sacchon.app.dto;

import gr.codehub.sacchon.app.model.Consultation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AllConsultationsReceivedForOnePatientDto {

    private String doctor_first_name;
    private String doctor_last_name;
    private String doctor_email;
    private LocalDate date_created;

    private String details;


    public AllConsultationsReceivedForOnePatientDto(Consultation consultation){
        if(consultation!=null){
            doctor_first_name = consultation.getDoctor().getFirstName();
            doctor_last_name = consultation.getDoctor().getLastName();
            doctor_email = consultation.getDoctor().getEmail();
            date_created = consultation.getDateCreated();
            details = consultation.getDetails();
        }
    }

    public Consultation asConsultation(){
        Consultation consultation = new Consultation();
        consultation.setDoctorFirstName(getDoctor_first_name());
        consultation.setDoctorLastName(getDoctor_last_name());
        consultation.setDateCreated(date_created);
        consultation.setDetails(details);
        consultation.setDoctorEmail(doctor_email);
        return consultation;
    }



}
