package gr.codehub.sacchon.app.dto;

import gr.codehub.sacchon.app.model.Consultation;
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
public class ConsultationBasicInfoDto {
    private String doctorFirstName;
    private String doctorLastName;
    private String doctorEmail;
    private LocalDate dateCreated;
    private List<String> medications;
    private String details;

    public ConsultationBasicInfoDto(Consultation consultation){
        if(consultation!=null){
            doctorFirstName = consultation.getDoctor().getFirstName();
            doctorLastName = consultation.getDoctor().getLastName();
            doctorEmail = consultation.getDoctor().getEmail();

            dateCreated = consultation.getDateCreated();
            medications = consultation.getMedications();
            details = consultation.getDetails();
        }
    }

    public Consultation asConsultation(){
        Consultation consultation = new Consultation();
        consultation.setDoctorFirstName(getDoctorFirstName());
        consultation.setDoctorFirstName(getDoctorLastName());
        consultation.setDateCreated(dateCreated);
        consultation.setMedications(medications);
        consultation.setDetails(details);
        return consultation;
    }
}