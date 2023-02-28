package gr.codehub.sacchon.app.controller;

import gr.codehub.sacchon.app.dto.ChiefDoctorDto;
import gr.codehub.sacchon.app.dto.PatientConsultationCountDto;
import gr.codehub.sacchon.app.dto.PatientDto;
import gr.codehub.sacchon.app.dto.*;
import gr.codehub.sacchon.app.exception.ChiefDoctorException;
import gr.codehub.sacchon.app.service.ChiefDoctorService;
import gr.codehub.sacchon.app.service.GlucoseRecordService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**  This Chief Doctor controllers enables doctors who have the chief doctor functionality
    to be able to achieve the following functionalities and receive the following information:
    _________________________________________________________________________________________
   . The information submissions (personal monitor data) of a patient over a time range
   . The information submissions (consultations) of a doctor over a time range
   . The list of the patients who are waiting for a consultation
   . The patients and the number of consultations they got over a time range
   . The list of the patients with no activity over a time range
   . The list of the doctors with no activity over a time range

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
public class ChiefDoctorController {
    private ChiefDoctorService chiefDoctorService;
    private final GlucoseRecordService glucoseRecordService;


    @GetMapping("/chiefdoctor")
    public List<ChiefDoctorDto> getChiefDoctorDto(){
        log.info("The end point chiefdoctor has been used");
        return chiefDoctorService.readChiefDoctor();
    }

    @GetMapping("/doctor/consultations/{id}/dates")
    public List<ConsultationsGivenByDoctor> getConsultationsByDoctorIdOnSpecificDates(
            @PathVariable(name="id") long doctorId,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(name="start") LocalDate startingDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(name="end") LocalDate endingDate
    ) {

        return chiefDoctorService.getConsultationsBetweenDatesGiven(doctorId,startingDate,endingDate);
    }

    @GetMapping("/chiefdoctor/{id}")
    public ChiefDoctorDto getChiefDoctorDto(@PathVariable(name="id")  long id) throws ChiefDoctorException {
        log.info("The end point chiefdoctor with id has been used");
        return chiefDoctorService.readChiefDoctor(id);
    }

    @PostMapping("/chiefdoctor")
    public  ChiefDoctorDto  createChiefDoctorDto(@RequestBody ChiefDoctorDto chiefDoctor){
        log.info("The end point chiefdoctor has been used");
        return chiefDoctorService.createChiefDoctor(chiefDoctor);
    }

    @PutMapping("/chiefdoctor/{id}")
    public boolean updateChiefDoctorDto(@RequestBody ChiefDoctorDto chiefDoctor,
                                   @PathVariable(name="id")  int id){
        log.info("The end point chiefdoctor with id has been used");
        return chiefDoctorService.updateChiefDoctor(chiefDoctor, id);
    }

    @GetMapping("/chief/consultation/waiting/query")
    public List<PatientDto> readPatientsWaitingConsultationDto(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(name="dateBefore") LocalDate dateBefore
    ){
        log.info("The end point @chiefDoctor seeking patients waiting consultation has been used.");
        return chiefDoctorService.readPatientsIdWaitingForConsultation(dateBefore);
    }

    @GetMapping("/chief/consultation/range/query")
    public List<PatientConsultationCountDto> readPatientsWithConsultationWithinRange(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(name="start") LocalDate startingDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(name="end") LocalDate endingDate
    ){
        log.info("The end point @chiefDoctor seeking patients with the number of consultations within range has been used.");
        return chiefDoctorService.readPatientsWithConsultationBetween(startingDate, endingDate);
    }

    @GetMapping("/chief/patient/absence/query")
    public List<PatientDto> readInactivePatientsWithinRange(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(name="start") LocalDate startingDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(name="end") LocalDate endingDate
    ){
        log.info("The end point @chiefDoctor seeking inactive patients has been used.");
        return chiefDoctorService.readInactivePatientsWithinRange(startingDate, endingDate);
    }

    @GetMapping("/chief/doctor/absence/query")
    public List<DoctorDto> readInactiveDoctorsWithinRange(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(name="start") LocalDate startingDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(name="end") LocalDate endingDate
    ){
        log.info("The end point @chiefDoctor seeking inactive patients has been used.");
        return chiefDoctorService.readInactiveDoctorsWithinRange(startingDate, endingDate);
    }



    @GetMapping("/readings/glucose/{patientId}")
    public List<PastGlucoseMeasurementDto> getGlucoseReadingsByPatientId(
            @PathVariable long patientId,
            @RequestParam(name = "start_date") LocalDate startDate,
            @RequestParam(name = "end_date") LocalDate endDate) {
        return glucoseRecordService.getGlucoseReadingsBetweenDatesByPatientId(patientId, startDate, endDate);
    }
}
