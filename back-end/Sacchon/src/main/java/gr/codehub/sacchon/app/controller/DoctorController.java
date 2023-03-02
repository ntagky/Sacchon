package gr.codehub.sacchon.app.controller;

import gr.codehub.sacchon.app.dto.*;
import gr.codehub.sacchon.app.repository.ConsultationRepository;
import gr.codehub.sacchon.app.repository.MedicationRepository;
import gr.codehub.sacchon.app.service.ConsultationService;
import gr.codehub.sacchon.app.service.DoctorService;
import gr.codehub.sacchon.app.service.MedicationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
  The doctor controllers contain CRUD controllers that enable the user to achieve the following
  functionalities in the application:
  _____________________________________________________________________________________________
  • view their account
  • sign up for an account remove the account
  • view patient record browse the data for a single patient (patient data and consultations)
  • search /find patients that have not had a consultation in the last month
  • consult provide advice to a patient for the upcoming month (name of medication
    and dosage)
  • update modify a consultation to a patient

   @RestController: This annotation is used to define the class as a Spring Rest Controller
   @AllArgsConstructor: This annotation is used to generate a constructor with arguments for all fields in the class
   @Slf4j: This annotation is used to enable logging in the class using the Simple Logging Facade for Java (SLF4J) API

   @author Christos Tzoulias
   @version 1.0
   @since 2023-02-28
 */

@RestController
@AllArgsConstructor
@Slf4j
public class DoctorController {

    private DoctorService doctorService;
    private ConsultationService consultationService;
    private MedicationService medicationService;
    private ConsultationRepository consultationRepository;
    private MedicationRepository medicationRepository;

    @GetMapping("/doctor")
    public List<DoctorDto> getDoctorsDto(){
        log.info("The end point /doctor has been used.");
        return doctorService.readDoctor();
    }

    @GetMapping("doctor/email/{match}")
    public List<DoctorDto> readDoctorByEmailNative(@PathVariable("match") String match){
        log.info("The end point doctor/email/match has been used.");
        return doctorService.readDoctorByEmailNativeService(match);
    }

    @GetMapping("/doctor/{id}/basicinfo")
    public DoctorDto getDoctorNameAndEmailById(@PathVariable(name="id") long id) {
        log.info("The end point doctor/id/basicinfo has been used.");
        return doctorService.readDoctorNameAndEmailById(id);
    }

    @GetMapping("/doctor/{id}")
    public DoctorDto getDoctorInfoById(@PathVariable(name="id") long id) {
        log.info("The end point doctor/id/ has been used.");
        return doctorService.readDoctorById(id);
    }

    @PostMapping("/signup/doctor")
    public long signUp(@RequestBody DoctorDto doctorDto){
        LocalDate curDate = LocalDate.now();
        doctorDto.setSignedDate(curDate);
        log.info("The end point signup/doctor has been used.");
        return doctorService.registerDoctor(doctorDto);
    }

    @GetMapping("/doctor/patients/waiting/query")
    public List<PatientDto> getPatientsWithNoConsultation() {
        LocalDate dateGiven = LocalDate.now().minusMonths(1).plusDays(1);
        log.info("The end point doctor/patients/waiting/query has been used.");
        return doctorService.readPatientsWithNoConsultation(dateGiven);
    }

    @PutMapping("/doctor/update/{id}")
    public boolean updateDoctorDto(@RequestBody DoctorDto doctor,
                                     @PathVariable(name="id") int id){
        log.info("The end point doctor with id has been used.");
        return doctorService.updateDoctor(doctor, id);
    }

    @DeleteMapping("/doctor/delete/{id}")
    public void deleteDoctorDto(@PathVariable(name="id") long id){
        log.info("The end point doctor/delete/id has been used.");
        doctorService.deleteDoctorById(id);
    }

    @PostMapping("/doctor/consultation/new")
    public long createConsultation(@RequestBody ConsultationWriterDto consultationWriterDto) {
        log.info("The end point /doctor/consultation/new has been used.");
        return doctorService.createConsultation(consultationWriterDto);
    }

    @PutMapping("/doctor/consultation/modify")
    public void modifyConsultation(@RequestBody ConsultationModifiedDto consultationModifiedDto) {
        consultationService.updateConsultationFromDoctorByPatientId(consultationModifiedDto);
        log.info("The end point /doctor/consultation/modify has been used.");
    }

    @PutMapping("/doctor/medication/modify")
    public void modifyMedication(@RequestBody MedicationDto medicationDto) {
        medicationService.updateMedicationByConsultationId(medicationDto);
        log.info("The end point /doctor/medication/modify has been used.");
    }
}