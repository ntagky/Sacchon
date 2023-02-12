package gr.codehub.sacchon.app.dto;

import gr.codehub.sacchon.app.model.Consultation;
import gr.codehub.sacchon.app.model.Doctor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class DoctorDto {
    private int id;
//    private List<Patient>;
    private List<Consultation> consultations;

    public DoctorDto(Doctor doctor){
        if(doctor!=null){
            id = doctor.getId();
        }
    }

    public Doctor asDoctor(){
        Doctor doctor = new Doctor();
        doctor.setId(id);
//        doctor.setConsultations(consultations);
        return doctor;
    }
}
