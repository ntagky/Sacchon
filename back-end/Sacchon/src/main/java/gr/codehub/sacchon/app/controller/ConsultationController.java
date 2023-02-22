package gr.codehub.sacchon.app.controller;

import gr.codehub.sacchon.app.dto.AllConsultationsReceivedForOnePatientDto;
import gr.codehub.sacchon.app.dto.ConsultationBasicInfoDto;
import gr.codehub.sacchon.app.dto.ConsultationDto;
import gr.codehub.sacchon.app.dto.ConsultationReceivedDto;
import gr.codehub.sacchon.app.exception.ConsultationException;
import gr.codehub.sacchon.app.service.ConsultationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class ConsultationController {
    private ConsultationService consultationService;

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

    @PutMapping("/consultation/{id}")
    public boolean updateConsultationDto(@RequestBody ConsultationReceivedDto consultation,
                                   @PathVariable(name="id")  int id){
        log.info("The end point consultation with id has been used");
        return consultationService.updateConsultation(consultation, id);
    }

    @DeleteMapping("/consultation/{id}")
    public boolean deleteConsultationDto(@PathVariable(name="id")  long id){
        log.info("The end point consultation with id has been used");
        return consultationService.deleteConsultation(id);
    }
}
