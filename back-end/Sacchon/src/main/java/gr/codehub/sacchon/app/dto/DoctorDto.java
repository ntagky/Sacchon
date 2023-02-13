package gr.codehub.sacchon.app.dto;

import gr.codehub.sacchon.app.model.Doctor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDto extends PersonDto {
    private int id;
    private List<PatientDto> patients;
    private List<ConsultationDto> consultations;

    public DoctorDto(Doctor doctor){
        if(doctor!=null){
            id = doctor.getId();
            patients = doctor.getPatients()
                    .stream()
                    .map(PatientDto::new)
                    .collect(Collectors.toList());
            consultations = doctor.getConsultations()
                    .stream()
                    .map(ConsultationDto::new)
                    .collect(Collectors.toList());
        }
    }

    public Doctor asDoctor(){
        Doctor doctor = new Doctor();
        doctor.setId(id);
        doctor.setPatients(
                patients
                        .stream()
                        .map(PatientDto::asPatient)
                        .collect(Collectors.toList())
        );
        doctor.setConsultations(
                consultations
                        .stream()
                        .map(ConsultationDto::asConsultation)
                        .collect(Collectors.toList())
        );
        return doctor;
    }
}
