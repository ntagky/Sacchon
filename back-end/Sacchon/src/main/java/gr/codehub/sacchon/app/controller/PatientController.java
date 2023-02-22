package gr.codehub.sacchon.app.controller;

import gr.codehub.sacchon.app.dto.*;
import gr.codehub.sacchon.app.service.*;
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
    private DoctorServices doctorServices;



    @GetMapping("/patient/{id}")
    //http://localhost:9000/api/{{id}}
    public List<PatientDto> findPatientById(@PathVariable("id") long id){
        return patientService.readPatientById(id);
    }

    @DeleteMapping("/{patientId}")   //<?> for generic response, could also be not found
    public ResponseEntity<?> deletePatientById(@PathVariable("patientId") long patientId) {
        patientService.deletePatientById(patientId);
        return ResponseEntity.noContent().build();
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

    @GetMapping("/patient/{id}/carbs/query/all")
    public List<PastCarbReadingsDto> getPastCarbReadingsByPatientIdOnSpecificDates(
            @PathVariable(name="id") long patientId,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(name="start") LocalDate startingDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(name="end") LocalDate endingDate
    ) {
        return patientService.getPreviousCarbReadingsByPatientIdBetweenDates(
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

    @GetMapping("/patient/{id}/consultationinfo")
    public List<ConsultationBasicInfoDto> getConsultationInfoByPatientId(@PathVariable(name="id") int id){
        return consultationService.findConsultationInfoByPatientId(id);
    }

    @PostMapping("/signup/patient")
    //http://localhost:9000/api/signup/patient]
    public ResponseEntity<?> createPatientDto(@RequestBody PatientDto PatientDto){
        log.info("The end point PatientDto has been used");
        patientService.registerPatient(PatientDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/patient/{id}/doctor")
    public DoctorDto getDoctorByPatientId(@PathVariable("id") long id){
    long doctorId = patientService.findDoctorIdByPatientId(id);
    return doctorServices.readDoctorNameAndEmailById(doctorId);
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
