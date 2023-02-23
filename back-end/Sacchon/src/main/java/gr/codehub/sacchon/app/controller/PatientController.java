package gr.codehub.sacchon.app.controller;

import gr.codehub.sacchon.app.dto.*;
import gr.codehub.sacchon.app.exception.CarbsException;
import gr.codehub.sacchon.app.exception.GlucoseException;
import gr.codehub.sacchon.app.exception.GlucoseRecordException;
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
    private GlucoseRecordService glucoseRecordService;
    private ConsultationService consultationService;
    private DoctorService doctorService;


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
    public List<CarbsFromPersonDto> writeCarbsToPatientById(@PathVariable(name="id") long id) {
        log.info("The end point PatientDto & CarbsDto has been used");
        return carbsService.readCarbsByPatientId(id);
    }

    @PostMapping("/patient/{id}/carbs")
    public Long createCarbsDtoFromPatientById(
            @PathVariable(name="id") long id,
            @RequestBody CarbsFromPersonDto carbsFromPersonDto
    ) {
        log.info("The end point PatientDto & CarbsDto has been used");
        return carbsService.createCarbsByPatientId(id, carbsFromPersonDto);
    }

    @PostMapping("/patient/carbs/{carbsId}/update/{measurement}")
    public boolean updateCarbsDtoFromCarbsId(
            @PathVariable(name="carbsId") long id,
            @PathVariable(name="measurement") int measurement
    ) {
        log.info("The end point PatientDto & CarbsDto has been used");
        return carbsService.updateCarbsById(id, measurement);
    }

    @DeleteMapping("/patient/carbs/{id}/delete")
    public void deleteCarbsById(@PathVariable(name="id") long id) throws CarbsException {
        carbsService.deleteCarbsById(id);
    }

    @PostMapping("/patient/glucose/record/{recordId}/update")
    public boolean updateGlucoseRecordDtoFromGlucoseId(
            @PathVariable(name="recordId") long recordId,
            @RequestBody GlucoseRecordUpdaterDto glucoseRecordUpdaterDto
    ) {
        log.info("The end point PatientDto & CarbsDto has been used");
        return glucoseRecordService.updateRecordById(recordId, glucoseRecordUpdaterDto);
    }

    @PostMapping("/patient/{id}/glucose")
    public Long createGlucoseDtoFromPatientById(
            @PathVariable(name="id") long id,
            @RequestBody GlucoseInitiatorDto glucoseInitiatorDto
    ) {
        log.info("The end point PatientDto & CarbsDto has been used");
        return glucoseService.createGlucoseByPatientId(id, glucoseInitiatorDto);
    }

    @DeleteMapping("/patient/glucose/record/{id}/delete")
    public void deleteGlucoseRecordById(@PathVariable(name="id") long id) throws GlucoseRecordException {
        glucoseRecordService.deleteGlucoseRecordById(id);
    }

    @DeleteMapping("/patient/glucose/{id}/delete")
    public void deleteGlucoseById(@PathVariable(name="id") long id) throws GlucoseException {
        glucoseService.deleteGlucoseById(id);
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

    // ConsultationDto contains every consultation info
    @GetMapping("/patient/{id}/consultation")
    public List<ConsultationDto> getConsultationOfPatientById(@PathVariable(name="id") long id) {
        log.info("The end point patient/{id}/consultation has been used");
        return consultationService.readConsultationByPatientId(id);
    }

    // ConsultationBasicInfoDto contains basic consultation info (medication, dosage etc)
    @GetMapping("/patient/{id}/consultationinfo")
    public List<ConsultationBasicInfoDto> getConsultationInfoByPatientId(@PathVariable(name="id") int id){
        return consultationService.findConsultationInfoByPatientId(id);
    }

//    @PostMapping("/signup/patient")
//    //http://localhost:9000/api/signup/patient
//    public ResponseEntity<?> createPatientDto(@RequestBody PatientDto PatientDto){
//        log.info("The end point PatientDto has been used");
//        patientService.registerPatient(PatientDto);
//        return ResponseEntity.ok().build();
//    }

    @PostMapping("/signup/patient")
    public long signUp(@RequestBody PatientDto patientDto) {
        LocalDate curDate = LocalDate.now();
        patientDto.setSignedDate(curDate);
        log.info("The end point signup/patient has been used");
        patientService.registerPatient(patientDto);
        return patientService.registerPatient(patientDto);
//        return ResponseEntity.ok(patientDto);
    }

    @GetMapping("/patient/{id}/doctor")
    public DoctorDto getDoctorByPatientId(@PathVariable("id") long id){
        long doctorId = patientService.findDoctorIdByPatientId(id);
        return doctorService.readDoctorNameAndEmailById(doctorId);
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
