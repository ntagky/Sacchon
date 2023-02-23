package gr.codehub.sacchon.app.dto;

import gr.codehub.sacchon.app.model.Doctor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class DoctorDto extends PersonDto{
//    private Long id;

    public DoctorDto(Doctor doctor){
        if(doctor!=null){
//            id = doctor.getId();
            super.setId(doctor.getId());
            super.setFirstName(doctor.getFirstName());
            super.setLastName(doctor.getLastName());
            super.setEmail(doctor.getEmail());
            super.setPassword(doctor.getPassword());
            super.setSignedDate(doctor.getSignedDate());
        }
    }

    public Doctor asDoctor(){
        Doctor doctor = new Doctor();
//        doctor.setId(id);
        doctor.setId(super.getId());
        doctor.setFirstName(super.getFirstName());
        doctor.setLastName(super.getLastName());
        doctor.setEmail(super.getEmail());
        doctor.setPassword(super.getPassword());
        doctor.setSignedDate(super.getSignedDate());
        return doctor;
    }
}
