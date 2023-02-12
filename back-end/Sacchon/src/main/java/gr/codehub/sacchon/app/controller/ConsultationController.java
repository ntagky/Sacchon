package gr.codehub.sacchon.app.controller;

import gr.codehub.sacchon.app.dto.ConsultationDto;
import gr.codehub.sacchon.app.exception.ConsultationException;
import gr.codehub.sacchon.app.service.ConsultationServices;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class ConsultationController {
    private ConsultationServices consultationServices;

    @GetMapping("/consultation")
    public List<ConsultationDto> getConsultationDto(){
        log.info("The end point consultation has been used");
        return consultationServices.readConsultation();
    }

    @GetMapping("/consultation/{id}")
    public ConsultationDto getConsultationDto(@PathVariable(name="id")  int id) throws ConsultationException {
        log.info("The end point consultation with id has been used");
        return consultationServices.readConsultation(id);
    }

    @PostMapping("/consultation")
    public  ConsultationDto  createConsultationDto(@RequestBody ConsultationDto consultation){
        log.info("The end point consultation has been used");
        return consultationServices.createConsultation(consultation);
    }

    @PutMapping("/consultation/{id}")
    public boolean updateConsultationDto(@RequestBody ConsultationDto consultation,
                                   @PathVariable(name="id")  int id){
        log.info("The end point consultation with id has been used");
        return consultationServices.updateConsultation(consultation, id);
    }

    @DeleteMapping("/consultation/{id}")
    public boolean deleteConsultationDto(@PathVariable(name="id")  int id){
        log.info("The end point consultation with id has been used");
        return consultationServices.deleteConsultation(id);
    }
}
