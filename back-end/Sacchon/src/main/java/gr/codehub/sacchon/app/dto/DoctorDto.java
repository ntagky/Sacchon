package gr.codehub.sacchon.app.dto;

import gr.codehub.sacchon.app.model.Doctor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor
public class DoctorDto extends PersonDto{
    private Long id;
    private List<PatientDto> patientsDto;
    private List<ConsultationDto> consultationsDto;

    public DoctorDto(Doctor doctor){
        if(doctor!=null){
            id = doctor.getId();
            patientsDto = doctor
                    .getPatients()
                    .stream()
                    .map(PatientDto::new)
                    .collect(Collectors.toList());
            consultationsDto = doctor
                    .getConsultations()
                    .stream()
                    .map(ConsultationDto::new)
                    .collect(Collectors.toList());
        }
    }

    public Doctor asDoctor(){
        Doctor doctor = new Doctor();
        doctor.setId(id);
        doctor.setConsultations(
                consultationsDto.stream()
                        .map(ConsultationDto::asConsultation)
                        .collect(Collectors.toList())
        );
        doctor.setPatients(
                patientsDto.stream()
                        .map(PatientDto::asPatient)
                        .collect(Collectors.toList())
        );
        return doctor;
    }
}
