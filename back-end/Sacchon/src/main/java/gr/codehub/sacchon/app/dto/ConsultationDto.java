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
public class ConsultationDto {
    private Long id;
    private String doctorFirstName;
    private String doctorLastName;
    private String doctorEmail;
    private LocalDate dateCreated;
    private boolean seenConsultation;
    private List<String> medications;
    private String details;
    private DoctorDto doctorDto;
    private PatientDto patientDto;

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
            doctorDto = new DoctorDto(consultation.getDoctor());
            patientDto = new PatientDto(consultation.getPatient());
        }
    }

    public Consultation asConsultation(){
        Consultation consultation = new Consultation();
//        consultation.setId(id);
//        consultation.setDateCreated(dateCreated);
//        consultation.setSeenConsultation(seenConsultation);
//        consultation.setMedications(medications);
//        consultation.setDetails(details);
//        consultation.setPatient(patientDto.asPatient());
//        consultation.setDoctor(doctorDto.asDoctor());
        return consultation;
    }
}