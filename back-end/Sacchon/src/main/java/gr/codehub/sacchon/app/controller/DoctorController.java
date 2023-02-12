package gr.codehub.sacchon.app.controller;

import gr.codehub.sacchon.app.dto.DoctorDto;
import gr.codehub.sacchon.app.exception.DoctorException;
import gr.codehub.sacchon.app.service.DoctorServices;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class DoctorController {

    private DoctorServices doctorServices;

    @GetMapping("/doctor")
    public List<DoctorDto> getDoctorDto(){
        log.info("The end point doctor has been used");
        return doctorServices.readDoctor();
    }

    @GetMapping("/doctor/{id}")
    public DoctorDto getDoctorDto(@PathVariable(name="id")  int id) throws DoctorException {
        log.info("The end point doctor with id has been used");
        return doctorServices.readDoctor(id);
    }

    @PostMapping("/doctor")
    public  DoctorDto  createDoctorDto(@RequestBody DoctorDto doctor){
        log.info("The end point doctor has been used");
        return doctorServices.createDoctor(doctor);
    }

    @PutMapping("/doctor/{id}")
    public boolean updateDoctorDto(@RequestBody DoctorDto doctor,
                                     @PathVariable(name="id")  int id){
        log.info("The end point doctor with id has been used");
        return doctorServices.updateDoctor(doctor, id);
    }

    @DeleteMapping("/doctor/{id}")
    public boolean deleteDoctorDto(@PathVariable(name="id")  int id){
        log.info("The end point doctor with id has been used");
        return doctorServices.deleteDoctor(id);
    }
}