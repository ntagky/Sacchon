package gr.codehub.sacchon.app.controller;

import gr.codehub.sacchon.app.dto.CarbsFromPersonDto;
import gr.codehub.sacchon.app.dto.ConsultationDto;
import gr.codehub.sacchon.app.dto.GlucoseFromPersonDto;
import gr.codehub.sacchon.app.dto.PatientDto;
import gr.codehub.sacchon.app.exception.PatientException;
import gr.codehub.sacchon.app.model.Patient;
import gr.codehub.sacchon.app.service.CarbsService;
import gr.codehub.sacchon.app.service.ConsultationService;
import gr.codehub.sacchon.app.service.GlucoseService;
import gr.codehub.sacchon.app.service.PatientService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class PatientController {

    private PatientService patientService;
    private CarbsService carbsService;
    private GlucoseService glucoseService;
    private ConsultationService consultationService;


    @PostMapping("/signup")
    public ResponseEntity<PatientDto> signUp(@RequestBody PatientDto patientDto) {
        patientService.registerPatient(patientDto);
        return ResponseEntity.ok(patientDto);
    }

    @GetMapping("/{id}")
    //http://localhost:9000/api/{{id}}
    public Patient getPatientById(@PathVariable long id) {
        return patientService.getPatientById(id);
    }


    @GetMapping("/patient")
    //http://localhost:9000/api/patient
    public List<PatientDto> getPatientDto(){
        log.info("The end point PatientDto has been used");
        return patientService.readPatient();
    }

    @DeleteMapping("/{patientId}")   //<?> for generic response, could also be not found
    public ResponseEntity<?> deletePatientById(@PathVariable("patientId") long patientId) {
        patientService.deletePatientById(patientId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/patient/{id}")
    //http://localhost:9000/api/patient/{{id}}
    public PatientDto getPatientDtoById(@PathVariable(name="id") long id) throws PatientException {
        log.info("The end point PatientDto has been used");
        return patientService.readPatientById(id);
    }

    @GetMapping("/patient/{id}/carbs")
    public List<CarbsFromPersonDto> getCarbsDtoFromPatientById(@PathVariable(name="id") long id) {
        log.info("The end point PatientDto & CarbsDto has been used");
        return carbsService.readCarbsByPatientId(id);
    }

    @GetMapping("/patient/{id}/carbs/query")
    public Integer getDailyAverageCarbsByPatientIdOnSpecificDates(
            @PathVariable(name="id") long patientId,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(name="start") LocalDate startingDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(name="end") LocalDate endingDate
    ) {
        log.info("The end point @DailyAverageCarbsByPatientIdOnSpecificDates has been used.");
        return carbsService.readAverageCarbsIntakeByPatientIdOnSpecificDates(
                patientId, startingDate, endingDate);
    }

    @GetMapping("/patient/{id}/glucose/query")
    public List<BigDecimal> getDailyAverageGlucoseByPatientIdOnSpecificDates(
            @PathVariable(name="id") long patientId,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(name="start") LocalDate startingDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(name="end") LocalDate endingDate
            ) {
        log.info("The end point @DailyAverageGlucoseByPatientIdOnSpecificDates has been used.");
        return glucoseService.readDailyAverageGlucoseByPatientIdOnSpecificDates(
                patientId, startingDate, endingDate);
    }

    @GetMapping("/patient/{id}/glucose")
    public List<GlucoseFromPersonDto> getGlucoseDtoFromPatientById(@PathVariable(name="id") long id) {
        log.info("The end point PatientDto & GlucoseDto has been used");
        return glucoseService.readGlucoseByPatientId(id);
    }

    @GetMapping("/patient/{id}/consultation")
    public List<ConsultationDto> getConsultationOfPatientById(@PathVariable(name="id") long id) {
        log.info("The end point patient/{id}/consultation has been used");
        return consultationService.readConsultationByPatientId(id);
    }

    @PostMapping("/patient")
    //http://localhost:9000/api/patient]
    public  PatientDto createPatientDto(@RequestBody PatientDto PatientDto){
        log.info("The end point PatientDto has been used");
        return patientService.createPatient(PatientDto);
    }

    @PutMapping("/patient/{id}")
    //http://localhost:9000/api/patient/{{id}}
    public boolean updatePatientDto(@RequestBody PatientDto PatientDto,
                                     @PathVariable(name="id") long id){
        return patientService.updatePatient(PatientDto, id);
    }

    @DeleteMapping("/patient/{id}")
    //http://localhost:9000/api/patient/{{id}}
    public void deletePatientDto(@PathVariable(name="id") long id){
        patientService.deletePatientById(id);
    }

}
