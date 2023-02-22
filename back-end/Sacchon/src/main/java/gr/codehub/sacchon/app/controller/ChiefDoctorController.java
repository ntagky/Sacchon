package gr.codehub.sacchon.app.controller;

import gr.codehub.sacchon.app.dto.*;
import gr.codehub.sacchon.app.exception.ChiefDoctorException;
import gr.codehub.sacchon.app.service.ChiefDoctorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class ChiefDoctorController {
    private ChiefDoctorService chiefDoctorService;

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

    @DeleteMapping("/chiefdoctor/{id}")
    public boolean deleteChiefDoctorDto(
            @PathVariable(name="id")  long id,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(name="dateBefore") LocalDate dateBefore
    ){
        log.info("The end point @chiefDoctor seeking patients waiting consultation has been used.");
        return chiefDoctorService.deleteChiefDoctor(id);
    }

    @DeleteMapping("/chiefdoctor/{id}/consultation/query")
    public boolean deleteChiefDoctorDto(@PathVariable(name="id")  long id){
        log.info("The end point chiefdoctor has been used");
        return chiefDoctorService.deleteChiefDoctor(id);
    }
}
