package gr.codehub.sacchon.app.controller;

import gr.codehub.sacchon.app.dto.ConsultationWriterDto;
import gr.codehub.sacchon.app.dto.DoctorDto;
import gr.codehub.sacchon.app.dto.PatientDto;
import gr.codehub.sacchon.app.service.DoctorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class DoctorController {

    private DoctorService doctorService;

    @GetMapping("/doctor")
    public List<DoctorDto> getDoctorsDto(){
        log.info("The end point doctor has been used");
        return doctorService.readDoctor();
    }

    @GetMapping("doctor/email/{match}")
    public List<DoctorDto> readDoctorByEmailNative(@PathVariable("match") String match){
        log.info("The end point doctor/email/{match} has been used");
        return doctorService.readDoctorByEmailNativeService(match);
    }

    @GetMapping("/doctor/{id}/basicinfo")
    public DoctorDto getDoctorNameAndEmailById(@PathVariable(name="id") long id) {
        log.info("The end point doctor/id/ has been used");
        return doctorService.readDoctorNameAndEmailById(id);
    }

    @GetMapping("/doctor/{id}/info")
    public DoctorDto getDoctorInfoById(@PathVariable(name="id") long id) {
        log.info("The end point doctor/id/ has been used");
        return doctorService.readDoctorById(id);
    }

    @PostMapping("/signup/doctor")
    public long signUp(@RequestBody DoctorDto doctorDto){
        LocalDate curDate = LocalDate.now();
        doctorDto.setSignedDate(curDate);
        log.info("The end point signup/doctor has been used");
        return doctorService.registerDoctor(doctorDto);
//        return ResponseEntity.ok(doctorDto);
    }

    //    @GetMapping("/doctor/patients/waiting/query")
//    public List<PatientDto> getPatientsWithNoConsultation
//            (@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(name="dateGiven") LocalDate dateGiven) {
//        log.info("The end point doctor/patients/waiting/query has been used");
//        return doctorServices.readPatientsWithNoConsultation(dateGiven);
//    }

    @GetMapping("/doctor/patients/waiting/query")
    public List<PatientDto> getPatientsWithNoConsultation() {
        LocalDate dateGiven = LocalDate.now().minusMonths(1).plusDays(1);
        log.info("The end point doctor/patients/waiting/query has been used");
        return doctorService.readPatientsWithNoConsultation(dateGiven);
    }

//    @PostMapping("/doctor")
//    public DoctorDto createDoctorDto(@RequestBody DoctorDto doctor){
//        log.info("The end point doctor has been used");
//        return doctorService.createDoctor(doctor);
//    }

    @PutMapping("/doctor/update/{id}")
    public boolean updateDoctorDto(@RequestBody DoctorDto doctor,
                                     @PathVariable(name="id") int id){
        log.info("The end point doctor with id has been used");
        return doctorService.updateDoctor(doctor, id);
    }

//    @DeleteMapping("/doctor/delete/{id}")
//    public void deleteDoctorDto(@PathVariable(name="id") long id){
//        log.info("The end point doctor with id has been used");
//        doctorService.deleteDoctorById(id);
//    }

    @PostMapping("/doctor/consultation/new")
    public Long createConsultation(
            @RequestBody ConsultationWriterDto consultationWriterDto)
    {
        log.info("The end point /doctor/consultation/new has been used.");
        return doctorService.createConsultation(consultationWriterDto);
    }
}