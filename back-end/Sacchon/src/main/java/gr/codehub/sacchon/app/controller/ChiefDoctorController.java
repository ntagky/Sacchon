package gr.codehub.sacchon.app.controller;

import gr.codehub.sacchon.app.dto.ChiefDoctorDto;
import gr.codehub.sacchon.app.exception.ChiefDoctorException;
import gr.codehub.sacchon.app.service.ChiefDoctorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
    public boolean deleteChiefDoctorDto(@PathVariable(name="id")  long id){
        log.info("The end point chiefdoctor has been used");
        return chiefDoctorService.deleteChiefDoctor(id);
    }
}
