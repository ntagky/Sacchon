package gr.codehub.sacchon.app.controller;

import gr.codehub.sacchon.app.dto.CarbsFromPersonDto;
import gr.codehub.sacchon.app.dto.GlucoseFromPersonDto;
import gr.codehub.sacchon.app.dto.PatientDto;
import gr.codehub.sacchon.app.exception.PatientException;
import gr.codehub.sacchon.app.service.CarbsService;
import gr.codehub.sacchon.app.service.GlucoseService;
import gr.codehub.sacchon.app.service.PatientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class PatientController {

    private PatientService patientService;
    private CarbsService carbsService;
    private GlucoseService glucoseService;

    @GetMapping("/patient")
    public List<PatientDto> getPatientDto(){
        log.info("The end point PatientDto has been used");
        return patientService.readPatient();
    }

    @GetMapping("/patient/{id}")
    public PatientDto getPatientDtoById(@PathVariable(name="id") int id) throws PatientException {
        log.info("The end point PatientDto has been used");
        return patientService.readPatientById(id);
    }

    @GetMapping("/patient/{id}/carbs")
    public List<CarbsFromPersonDto> getCarbsDtoFromPatientById(@PathVariable(name="id") int id) {
        log.info("The end point PatientDto & CarbsDto has been used");
        return carbsService.readCarbsByPatientId(id);
    }

    @GetMapping("/patient/{id}/glucose")
    public List<GlucoseFromPersonDto> getGlucoseDtoFromPatientById(@PathVariable(name="id") int id) {
        log.info("The end point PatientDto & CarbsDto has been used");
        return glucoseService.readGlucoseByPatientId(id);
    }

    @PostMapping("/patient")
    public  PatientDto createPatientDto(@RequestBody PatientDto PatientDto){
        log.info("The end point PatientDto has been used");
        return patientService.createPatient(PatientDto);
    }

    @PutMapping("/patient/{id}")
    public boolean updatePatientDto(@RequestBody PatientDto PatientDto,
                                     @PathVariable(name="id") int id){
        return patientService.updatePatient(PatientDto, id);
    }

    @DeleteMapping("/patient/{id}")
    public boolean deletePatientDto(@PathVariable(name="id") int id){
        return patientService.deletePatientById(id);
    }

}
