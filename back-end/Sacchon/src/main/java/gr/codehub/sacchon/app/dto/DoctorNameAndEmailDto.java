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
public class DoctorNameAndEmailDto extends PersonDto{
    private String firstName;
    private String lastName;
    private String email;

    public DoctorNameAndEmailDto(Doctor doctor){
        if(doctor!=null){
            firstName = super.getFirstName();
            lastName = super.getLastName();
            email = super.getEmail();
        }
    }

    public Doctor asDoctor(){
        Doctor doctor = new Doctor();
        doctor.setFirstName(firstName);
        doctor.setLastName(lastName);
        doctor.setEmail(email);
        return doctor;
    }
}
