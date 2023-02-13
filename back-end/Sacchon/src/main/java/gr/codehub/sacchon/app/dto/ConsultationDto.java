package gr.codehub.sacchon.app.dto;

import gr.codehub.sacchon.app.model.Consultation;
import jakarta.persistence.ElementCollection;
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
    private boolean seenConsultation;
    @ElementCollection
    private List<String> medications;
    private String details;
    private PatientDto patientDto;
    private DoctorDto doctorDto;

    public ConsultationDto(Consultation consultation){
        if(consultation!=null){
            id = consultation.getId();
            doctorName = consultation.getDoctorName();
            dateCreated = consultation.getDateCreated();
            seenConsultation = consultation.isSeenConsultation();
//            medications = consultation.getMedications();
            details = consultation.getDetails();
            patientDto = new PatientDto(consultation.getPatient());
            doctorDto = new DoctorDto(consultation.getDoctor());
        }
    }

    public Consultation asConsultation(){
        Consultation consultation = new Consultation();
        consultation.setId(id);
        consultation.setDateCreated(dateCreated);
        consultation.setSeenConsultation(seenConsultation);
//        consultation.setMedications(medications);
        consultation.setDetails(details);
        consultation.setPatient(patientDto.asPatient());
        consultation.setDoctor(doctorDto.asDoctor());
        return consultation;
    }
}