package gr.codehub.sacchon.app.dto;

import gr.codehub.sacchon.app.model.Doctor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class DoctorDto extends PersonDto{

    public DoctorDto(Doctor doctor){
        if(doctor!=null){
            super.setId(doctor.getId());
            super.setFirstName(doctor.getFirstName());
            super.setLastName(doctor.getLastName());
            super.setEmail(doctor.getEmail());
        }
    }

    public Doctor asDoctor(){
        Doctor doctor = new Doctor();
        doctor.setId(super.getId());
        doctor.setFirstName(super.getFirstName());
        doctor.setLastName(super.getLastName());
        doctor.setEmail(super.getEmail());
        return doctor;
    }
}
