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
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("http://localhost:4200")
public class PatientController {

    private PatientService patientService;
    private CarbsService carbsService;
    private GlucoseService glucoseService;
    private GlucoseRecordService glucoseRecordService;
    private ConsultationService consultationService;
    private DoctorService doctorService;


    @GetMapping("/patient/{id}")
    //http://localhost:9000/patient/{{id}}
    public List<PatientDto> findPatientById(@PathVariable("id") long id){
        return patientService.readPatientById(id);
    }

    @DeleteMapping("/delete/{patientId}")
    //http://localhost:9000/delete/{{patientId}}
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

    @PutMapping("/patient/carbs/{carbsId}/update/{measurement}")
    public boolean updateCarbsDtoFromCarbsId(
            @PathVariable(name="carbsId") long id,
            @PathVariable(name="measurement") int measurement
    ) {
        log.info("The end point PatientDto & CarbsDto has been used");
        return carbsService.updateCarbsById(id, measurement);
    }

    @PutMapping("/patient/{id}/carbs/{date}/update/{measurement}")
    public boolean updateCarbsFromPatientIdAndDate(
            @PathVariable(name="id") int patientId,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable(name="date") LocalDate date,
            @PathVariable(name="measurement") int measurement) {
        log.info("The end point PatientDto & CarbsDto has been used");
        return carbsService.updateCarbsFromPatientIdAndDate(patientId, date, measurement);
    }

    @DeleteMapping("/patient/carbs/{id}/delete")
    public void deleteCarbsById(@PathVariable(name="id") long id) throws CarbsException {
        carbsService.deleteCarbsById(id);
    }

    @PutMapping("/patient/glucose/record/{recordId}/update")
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

    @PostMapping("/patient/{id}/glucose/{date}")
    public Long createGlucoseRecordForPatientInSpecificDate(
            @PathVariable(name="id") long patientId,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable(name="date") LocalDate date,
            @RequestBody GlucoseRecordUpdaterDto glucoseRecordUpdaterDto
    ) {
        log.info("The end point PatientDto & CarbsDto has been used");
        return glucoseService.createGlucoseByPatientIdAtDate(patientId, date, glucoseRecordUpdaterDto);
    }

    @DeleteMapping("/patient/glucose/record/{id}/delete")
    public void deleteGlucoseRecordById(@PathVariable(name="id") long id) throws GlucoseRecordException {
        glucoseRecordService.deleteGlucoseRecordById(id);
    }

    @DeleteMapping("/patient/glucose/{id}/delete")
    public void deleteGlucoseById(@PathVariable(name="id") long id) throws GlucoseException {
        glucoseService.deleteGlucoseById(id);
    }

    @DeleteMapping("/patient/{id}/glucose/{date}/delete")
    public void deletePatientGlucoseByDate(
            @PathVariable(name="id") long id,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable(name="date") LocalDate date
    ) throws GlucoseException {
        glucoseService.deleteGlucoseByPatientIdAndDate(id, date);
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

    @GetMapping("/patient/{id}/data/query")
    public List<PersonDataDto> getPersonDataPaginating(
            @PathVariable("id") long id,
            @RequestParam(name="start") long startingPosition,
            @RequestParam(name="step") long step
    ){
        List<PersonDataDto> personDataDtoList = new ArrayList<>();
        LocalDate assignedDate = patientService.findDateAssignedFromPatientId(id);
        LocalDate indexDate = LocalDate.now().minusDays(startingPosition);
        long index = startingPosition;

        while (index < startingPosition + step) {

            if (indexDate.isBefore(assignedDate))
                break;

            ConsultationPureDto consultationPureDto = consultationService.findConsultationIdInAndStatusSpecificDate(
                    id,
                    indexDate,
                    indexDate.minusMonths(1)
            );

            personDataDtoList.add(new PersonDataDto(
                    indexDate,
                    indexDate.getDayOfWeek().toString(),
                    carbsService.readCarbsByPatientIdInSpecificDate(id, indexDate),
                    glucoseService.readDailyAverageGlucoseByPatientIdOnSpecificDates(
                            id,
                            indexDate,
                            indexDate
                    ).stream().findFirst().orElseGet(() -> new BigDecimal(0)),
                    glucoseRecordService.readGlucoseRecordCountByGlucoseId(glucoseService.findGlucoseIdInSpecificDateByPatientId(id, indexDate)),
                    consultationPureDto.getId(),
                    consultationPureDto.getStatus()
            ));

            indexDate = indexDate.minusDays(1);
            index++;
        }
        return personDataDtoList;
    }

    @GetMapping("/patient/{id}/data/paginator")
    public long getDateCountFromPerson(
            @PathVariable("id") long id,
            @RequestParam("step") long step
    ){
        return patientService.findDateAssignedFromPatientId(id).until(LocalDate.now(), ChronoUnit.DAYS) / step + 1;
    }

    @GetMapping("/patient/{id}/glucose/{date}")
    public List<GlucoseRecordFromDayDto> getGlucoseDtoFromPatientByIdInSpecificDate(
            @PathVariable(name="id") long patientId,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable(name="date") LocalDate date
    ) {
        log.info("The end point PatientDto & GlucoseDto has been used");
        Long glucoseId = glucoseService.findGlucoseIdInSpecificDateByPatientId(patientId, date);
        if (glucoseId == null)
            return null;

        return glucoseRecordService.readGlucoseRecordByGlucoseId(glucoseId);
    }

    @DeleteMapping("/patient/{id}/carbs/{date}")
    public boolean deleteCarbsFromPatientInSpecificDate(
            @PathVariable(name="id") long id,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable(name="date") LocalDate date
    ){
        patientService.deleteCarbsFromPatientInSpecificDate(id, date);
        return true;
    }

    @PutMapping("/patient/update/{id}")
    public boolean updatePatientDto(@RequestBody PatientDto patientDto,
                                   @PathVariable(name="id") int id){
        log.info("The end point doctor with id has been used");
        return false;
//         doctorService.updateDoctor(doctor, id);
    }

  @PutMapping("/patient/update/{id}/email")
    public void updatePatientEmail(@PathVariable(name="id") int id,
                                                @RequestParam("email") String email){
        patientService.updateEmailByPatientId(id,email);
  }
    @PutMapping("/patient/update/{id}/firstname")
    public void updatePatientFirstName(@PathVariable(name="id") int id,
                                   @RequestParam("firstName") String firstName){
        patientService.updateFirstNameByPatientId(id,firstName);
    }
    @PutMapping("/patient/update/{id}/lastname")
    public void updatePatientLastName(@PathVariable(name="id") int id,
                                       @RequestParam("lastName") String lastName){
        patientService.updateLastNameByPatientId(id,lastName);
    }

    @PutMapping("/patient/update/{id}/address")
    public void updatePatientAddress(@PathVariable(name="id") int id,
                                      @RequestParam("address") String address){
        patientService.updateAddressByPatientId(id,address);
    }

    @PutMapping("/patient/update/{id}/gender")
    public void updatePatientGender(@PathVariable(name="id") int id,
                                     @RequestParam("gender") String gender){
        patientService.updateGenderByPatientId(id,gender);
    }

    @PutMapping("/patient/update/{id}/phonenumber")
    public void updatePatientPhoneNumber(@PathVariable(name="id") int id,
                                         @RequestParam("phonenumber") String phonenumber){
        patientService.updatePhoneNumberByPatientId(id,phonenumber);
    }

    @PutMapping("/patient/update/{id}/medicalrecordnumber")
    public void updatePatientMedicalRecordNumber(@PathVariable(name="id") int id,
                                                 @RequestParam("medicalRecordNumber") String medicalRecordNumber){
        patientService.updateMedicalRecordNumberByPatientId(id,medicalRecordNumber);
    }

    @PutMapping("/patient/update/{id}/height")
    public void updatePatientHeight(@PathVariable(name="id") int id,
                                                 @RequestParam("height") int height){
        patientService.updateHeightByPatientId(id,height);
    }

    @PutMapping("/patient/update/{id}/weight")
    public void updatePatientWeight(@PathVariable(name="id") int id,
                                                 @RequestParam("weight") double weight){
        patientService.updateWeightByPatientId(id,weight);
    }
    @PutMapping("/patient/update/{id}/birthdate")
    public void updatePatientWeight(@PathVariable(name="id") int id,
                                    @RequestParam("birthDate") LocalDate birthDate){
        patientService.updateBirthDateByPatientId(id,birthDate);
    }

}
