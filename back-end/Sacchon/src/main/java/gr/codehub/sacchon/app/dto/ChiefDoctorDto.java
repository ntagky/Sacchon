package gr.codehub.sacchon.app.dto;

import gr.codehub.sacchon.app.model.ChiefDoctor;
import gr.codehub.sacchon.app.model.Doctor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ChiefDoctorDto {

    public ChiefDoctorDto(ChiefDoctor chiefDoctor){

    }

    public ChiefDoctor asChiefDoctor(){
        ChiefDoctor chiefDoctor = new ChiefDoctor();
        return chiefDoctor;
    }
}
