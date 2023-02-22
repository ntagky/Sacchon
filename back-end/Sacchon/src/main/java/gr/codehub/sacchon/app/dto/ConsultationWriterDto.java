package gr.codehub.sacchon.app.dto;

import gr.codehub.sacchon.app.model.Consultation;
import gr.codehub.sacchon.app.model.Doctor;
import gr.codehub.sacchon.app.model.Patient;
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
public class ConsultationWriterDto {
    private Long id;
    private String doctorFirstName;
    private String doctorLastName;
    private String doctorEmail;
    private LocalDate dateCreated;
    private boolean seenConsultation;
    private List<String> medications;
    private String details;
    private long doctorId;
    private long patientId;

    public ConsultationWriterDto(Consultation consultation){
        if(consultation!=null){
            id = consultation.getId();
            doctorFirstName = consultation.getDoctor().getFirstName();
            doctorLastName = consultation.getDoctor().getLastName();
            doctorEmail = consultation.getDoctor().getEmail();
            dateCreated = consultation.getDateCreated();
            seenConsultation = consultation.isSeenConsultation();
            medications = consultation.getMedications();
            details = consultation.getDetails();
            doctorId = consultation.getDoctor().getId();
            patientId = consultation.getPatient().getId();
        }
    }

    public Consultation asConsultation(Doctor doctor, Patient patient){
        Consultation consultation = new Consultation();
        consultation.setId(id);
        consultation.setDateCreated(dateCreated);
        consultation.setSeenConsultation(seenConsultation);
        consultation.setMedications(medications);
        consultation.setDetails(details);
        consultation.setDoctor(doctor);
        consultation.setPatient(patient);
        return consultation;
    }
}