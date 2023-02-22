package gr.codehub.sacchon.app.controller;

import gr.codehub.sacchon.app.dto.DoctorDto;
import gr.codehub.sacchon.app.dto.PatientDto;
import gr.codehub.sacchon.app.service.DoctorServices;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class DoctorController {

    private DoctorServices doctorServices;

    @GetMapping("/doctor")
    public List<DoctorDto> getDoctorsDto(){
        log.info("The end point doctor has been used");
        return doctorServices.readDoctor();
    }

    @GetMapping("doctor/email/{match}")
    public List<DoctorDto> readDoctorByEmailNative(@PathVariable("match") String match){
        log.info("The end point doctor/email/{match} has been used");
        return doctorServices.readDoctorByEmailNativeService(match);
    }

    @GetMapping("/doctor/{id}/basicinfo")
    public DoctorDto getDoctorNameAndEmailById(@PathVariable(name="id") long id) {
        log.info("The end point doctor/id/ has been used");
        return doctorServices.readDoctorNameAndEmailById(id);
    }

    @GetMapping("/doctor/patients/waiting/query")
    public List<PatientDto> getPatientsWithNoConsultation
            (@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(name="dateGiven") LocalDate dateGiven) {
        log.info("The end point doctor/patients/waiting/query has been used");
        return doctorServices.readPatientsWithNoConsultation(dateGiven);
    }

    @PostMapping("/doctor")
    public DoctorDto createDoctorDto(@RequestBody DoctorDto doctor){
        log.info("The end point doctor has been used");
        return doctorServices.createDoctor(doctor);
    }

    @PutMapping("/doctor/update/{id}")
    public boolean updateDoctorDto(@RequestBody DoctorDto doctor,
                                     @PathVariable(name="id") int id){
        log.info("The end point doctor with id has been used");
        return doctorServices.updateDoctor(doctor, id);
    }

    @DeleteMapping("/doctor/delete/{id}")
    public boolean deleteDoctorDto(@PathVariable(name="id") long id){
        log.info("The end point doctor with id has been used");
        return doctorServices.deleteDoctor(id);
    }
}