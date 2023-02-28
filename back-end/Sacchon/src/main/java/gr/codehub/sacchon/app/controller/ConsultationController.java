package gr.codehub.sacchon.app.controller;

import gr.codehub.sacchon.app.dto.*;
import gr.codehub.sacchon.app.exception.ConsultationException;
import gr.codehub.sacchon.app.service.ConsultationService;
import gr.codehub.sacchon.app.service.MedicationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
  This class represents the RestController for consultations.
  It is responsible for handling HTTP requests related to consultations,
  and it communicates with the ConsultationService and MedicationService to
  execute the necessary actions.

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
public class ConsultationController {
    private ConsultationService consultationService;
    private MedicationService medicationService;

    @GetMapping("/patient/{id}/consultations")
    public List<AllConsultationsReceivedForOnePatientDto> getConsultationsByPatientId
            (@PathVariable("id") long id){
        return consultationService.getAllConsultationsReceivedForPatient(id);
    }

    @GetMapping("/consultation")
    public List<ConsultationDto> getConsultationDto(){
        log.info("The end point consultation has been used");
        return consultationService.readConsultation();
    }

    @GetMapping("/consultation/{id}")
    public ConsultationDto getConsultationDto(@PathVariable(name="id") long id) throws ConsultationException {
        log.info("The end point consultation with id has been used");
        return consultationService.readConsultation(id);
    }

    @GetMapping("/consultation/{id}/info")
    public List<ConsultationBasicInfoDto> getConsultationInfoByPatientId(@PathVariable(name="id") long id){
        return consultationService.findConsultationInfoByPatientId(id);
    }

    @PostMapping("/consultation")
    public ConsultationDto createConsultationDto(@RequestBody ConsultationReceivedDto consultation){
        log.info("The end point consultation has been used");
        return consultationService.createConsultation(consultation);
    }

    @DeleteMapping("/consultation/{id}")
    public boolean deleteConsultationDto(@PathVariable(name="id")  long id){
        log.info("The end point consultation with id has been used");
        return consultationService.deleteConsultation(id);
    }

    @GetMapping("/consultation/{id}/medication")
    public List<MedicationDto> getMedicationsByConsId(@PathVariable(name="id") long id){
        log.info("The end point consultation/{id}/medication has been used");
        return medicationService.findMedications(id);
    }
}
