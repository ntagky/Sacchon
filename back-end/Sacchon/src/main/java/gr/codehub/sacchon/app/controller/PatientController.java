package gr.codehub.sacchon.app.controller;

import gr.codehub.sacchon.app.dto.*;
import gr.codehub.sacchon.app.exception.*;
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

/**
  The patient controllers contain CRUD controllers that enable the user to achieve the following
  functionalities in the application:
  _____________________________________________________________________________________________
  • view their account
  • sign up for an account
  • remove the account
  • store their data
       -> blood glucose level (date, time, measured in mg/dL)
       -> daily carbonates intake (date, measured in grams)
  • view
       -> their average daily blood glucose level over a user-specified period
       -> their average carb intake over a user-specified period
       -> the current and past consultations from doctors
  • update /modify incorrect submitted data
  • delete incorrect submitted data

   RestController: This annotation is used to define the class as a Spring Rest Controller
   AllArgsConstructor: This annotation is used to generate a constructor with arguments for all fields in the class
   Slf4j: This annotation is used to enable logging in the class using the Simple Logging Facade for Java (SLF4J) API

   @author Christos Tzoulias
   @version 1.0
   @since 2023-02-28
 */
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
    public List<PatientDto> findPatientById(@PathVariable("id") long id) throws PatientException {
        log.info("The end point /patient/id has been used.");
        return patientService.readPatientById(id);
    }

    @DeleteMapping("/delete/{patientId}")
    public ResponseEntity<?> deletePatientById(@PathVariable("patientId") long patientId) throws PatientException {
        log.info("The end point /delete/patientId has been used.");
        patientService.deletePatientById(patientId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/patient/{id}/carbs")
    public List<CarbsFromPersonDto> writeCarbsToPatientById(@PathVariable(name="id") long id) {
        log.info("The end point /patient/id/carbs has been used.");
        return carbsService.readCarbsByPatientId(id);
    }

    @PostMapping("/patient/{id}/carbs")
    public Long createCarbsDtoFromPatientById(
            @PathVariable(name="id") long id,
            @RequestBody CarbsFromPersonDto carbsFromPersonDto
    ) {
        log.info("The end point /patient/id/carbs has been used.");
        return carbsService.createCarbsByPatientId(id, carbsFromPersonDto);
    }

    @PutMapping("/patient/carbs/{carbsId}/update/{measurement}")
    public boolean updateCarbsDtoFromCarbsId(
            @PathVariable(name="carbsId") long id,
            @PathVariable(name="measurement") int measurement
    ) {
        log.info("The end point /patient/carbs/id/update/measurement has been used.");
        return carbsService.updateCarbsById(id, measurement);
    }

    @PutMapping("/patient/{id}/carbs/{date}/update/{measurement}")
    public boolean updateCarbsFromPatientIdAndDate(
            @PathVariable(name="id") int patientId,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable(name="date") LocalDate date,
            @PathVariable(name="measurement") int measurement) {
        log.info("The end point /patient/id/carbs/date/update/measurement has been used.");
        return carbsService.updateCarbsFromPatientIdAndDate(patientId, date, measurement);
    }

    @DeleteMapping("/patient/carbs/{id}/delete")
    public void deleteCarbsById(@PathVariable(name="id") long id) throws CarbsException {
        log.info("The end point /patient/carbs/id/delete has been used.");
        carbsService.deleteCarbsById(id);
    }

    @PutMapping("/patient/glucose/record/{recordId}/update")
    public boolean updateGlucoseRecordDtoFromGlucoseId(
            @PathVariable(name="recordId") long recordId,
            @RequestBody GlucoseRecordUpdaterDto glucoseRecordUpdaterDto
    ) {
        log.info("The end point /patient/glucose/record/recordId/update has been used.");
        return glucoseRecordService.updateRecordById(recordId, glucoseRecordUpdaterDto);
    }

    @PostMapping("/patient/{patientId}/glucose/{date}/record/create")
    public long createGlucoseRecordDtoFromGlucoseId(
            @PathVariable(name="patientId") long patientId,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable(name="date") LocalDate date,
            @RequestBody GlucoseRecordUpdaterDto glucoseRecordUpdaterDto
    ) {
        log.info("The end point /patient/patientId/glucose/date/record/create has been used.");
        return glucoseRecordService.createGlucoseRecordForPatientOnSpecificDate(patientId, date, glucoseRecordUpdaterDto);
    }

    @PostMapping("/patient/{id}/glucose")
    public Long createGlucoseDtoFromPatientById(
            @PathVariable(name="id") long id,
            @RequestBody GlucoseInitiatorDto glucoseInitiatorDto
    ) {
        log.info("The end point /patient/id/glucose has been used.");
        return glucoseService.createGlucoseByPatientId(id, glucoseInitiatorDto);
    }

    @PostMapping("/patient/{id}/glucose/{date}")
    public Long createGlucoseRecordForPatientInSpecificDate(
            @PathVariable(name="id") long patientId,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable(name="date") LocalDate date,
            @RequestBody GlucoseRecordUpdaterDto glucoseRecordUpdaterDto
    ) {
        log.info("The end point /patient/id/glucose/date has been used.");
        return glucoseService.createGlucoseByPatientIdAtDate(patientId, date, glucoseRecordUpdaterDto);
    }

    @DeleteMapping("/patient/glucose/record/{id}/delete")
    public void deleteGlucoseRecordById(@PathVariable(name="id") long id) throws GlucoseRecordException {
        log.info("The end point /patient/glucose/record/id/delete has been used.");
        glucoseRecordService.deleteGlucoseRecordById(id);
    }

    @DeleteMapping("/patient/glucose/{id}/delete")
    public void deleteGlucoseById(@PathVariable(name="id") long id) throws GlucoseException {
        log.info("The end point /patient/glucose/{id}/delete has been used.");
        glucoseService.deleteGlucoseById(id);
    }

    @DeleteMapping("/patient/{id}/glucose/{date}/delete")
    public void deletePatientGlucoseByDate(
            @PathVariable(name="id") long id,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable(name="date") LocalDate date
    ) throws GlucoseException {
        log.info("The end point /patient/id/glucose/date/delete has been used.");
        glucoseService.deleteGlucoseByPatientIdAndDate(id, date);
    }

    @GetMapping("/patient/{id}/carbs/query")
    public Integer getDailyAverageCarbsByPatientIdOnSpecificDates(
            @PathVariable(name="id") long patientId,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(name="start") LocalDate startingDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(name="end") LocalDate endingDate
    ) {
        log.info("The end point /patient/id/carbs/query has been used.");
        return carbsService.readAverageCarbsIntakeByPatientIdOnSpecificDates(
                patientId, startingDate, endingDate);
    }

    @GetMapping("/patient/{id}/glucose/query")
    public List<BigDecimal> getDailyAverageGlucoseByPatientIdOnSpecificDates(
            @PathVariable(name="id") long patientId,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(name="start") LocalDate startingDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(name="end") LocalDate endingDate
            ) {
        log.info("The end point /patient/id/glucose/query has been used.");
        return glucoseService.readDailyAverageGlucoseByPatientIdOnSpecificDates(
                patientId, startingDate, endingDate);
    }

    @GetMapping("/patient/{id}/carbs/query/all")
    public List<PastCarbReadingsDto> getPastCarbReadingsByPatientIdOnSpecificDates(
            @PathVariable(name="id") long patientId,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(name="start") LocalDate startingDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(name="end") LocalDate endingDate
    ) throws PatientException, DateValidationException {
        log.info("The end point /patient/id/carbs/query/all has been used.");
        return patientService.getPreviousCarbReadingsByPatientIdBetweenDates(
                patientId, startingDate, endingDate);
    }

    @GetMapping("/patient/{id}/glucose")
    public List<GlucoseFromPersonDto> getGlucoseDtoFromPatientById(@PathVariable(name="id") long id) {
        log.info("The end point /patient/id/glucose has been used.");
        return glucoseService.readGlucoseByPatientId(id);
    }

    // ConsultationDto contains every consultation info
    @GetMapping("/patient/{id}/consultation")
    public List<ConsultationDto> getConsultationOfPatientById(@PathVariable(name="id") long id) {
        log.info("The end point patient/id/consultation has been used.");
        return consultationService.readConsultationByPatientId(id);
    }

    // ConsultationBasicInfoDto contains basic consultation info (medication, dosage etc)
    @GetMapping("/patient/{id}/consultationinfo")
    public List<ConsultationBasicInfoDto> getConsultationInfoByPatientId(@PathVariable(name="id") int id){
        log.info("The end point /patient/id/consultationinfo has been used.");
        return consultationService.findConsultationInfoByPatientId(id);
    }

    @PostMapping("/signup/patient")
    public long signUp(@RequestBody PatientDto patientDto) throws RegistrationException,RegisterValidationException {
        LocalDate curDate = LocalDate.now();
        patientDto.setSignedDate(curDate);
        log.info("The end point /signup/patient has been used");
        return patientService.registerPatient(patientDto);
//        return ResponseEntity.ok(patientDto);
    }

    @GetMapping("/patient/{id}/doctor")
    public DoctorDto getDoctorByPatientId(@PathVariable("id") long id) throws PatientException{
        log.info("The end point /patient/id/doctor has been used.");
        long doctorId = patientService.findDoctorIdByPatientId(id);
        return doctorService.readDoctorNameAndEmailById(doctorId);
    }

//    @PutMapping("/patient/{id}")
//    public boolean updatePatientDto(@RequestBody PatientDto PatientDto,
//                                     @PathVariable(name="id") long id){
//        log.info("The end point /patient/id has been used.");
//        return patientService.updatePatient(PatientDto, id);
//    }

    @DeleteMapping("/patient/{id}")
    public void deletePatientDto(@PathVariable(name="id") long id) throws PatientException{
        log.info("The end point /patient/id has been used.");
        patientService.deletePatientById(id);
    }

    @GetMapping("/patient/{id}/signed-date")
    public LocalDate getPersonsAssignedDate(@PathVariable("id") long id) throws PatientException {
        log.info("The end point /patient/id/signed-date has been used.");
        return patientService.findDateAssignedFromPatientId(id);
    }

    @GetMapping("/patient/{id}/data/query")
    public List<PersonDataDto> getPersonDataPaginating (
            @PathVariable("id") long id,
            @RequestParam(name="start") long startingPosition,
            @RequestParam(name="step") long step
    )throws PatientException{
        log.info("The end point /patient/id/data/query has been used.");
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
                    glucoseRecordService.readGlucoseRecordCountByGlucoseId(
                            glucoseService.findGlucoseIdInSpecificDateByPatientId(id, indexDate)),
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
    )throws PatientException{
        log.info("The end point /patient/id/data/paginator has been used.");
        return patientService.findDateAssignedFromPatientId(id).until(LocalDate.now(), ChronoUnit.DAYS) / step + 1;
    }

    @GetMapping("/patient/{id}/glucose/{date}")
    public List<GlucoseRecordFromDayDto> getGlucoseDtoFromPatientByIdInSpecificDate(
            @PathVariable(name="id") long patientId,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable(name="date") LocalDate date
    ) {
        log.info("The end point /patient/id/glucose/date has been used.");
        Long glucoseId = glucoseService.findGlucoseIdInSpecificDateByPatientId(patientId, date);
        if (glucoseId == null)
            return null;

        return glucoseRecordService.readGlucoseRecordByGlucoseId(glucoseId);
    }

    @DeleteMapping("/patient/{id}/carbs/{date}")
    public boolean deleteCarbsFromPatientInSpecificDate(
            @PathVariable(name="id") long id,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable(name="date") LocalDate date
    )throws PatientException, DateValidationException{
        patientService.deleteCarbsFromPatientInSpecificDate(id, date);
        return true;
    }
    @PutMapping("/patient/update/{id}/email")
    public void updatePatientEmail(@PathVariable(name="id") int id,
                                                @RequestParam("email") String email)
            throws PatientException{
        patientService.updateEmailByPatientId(id,email);
    }
    @PutMapping("/patient/update/{id}/firstname")
    public void updatePatientFirstName(@PathVariable(name="id") int id,
                                   @RequestParam("firstName") String firstName)
            throws PatientException{
        patientService.updateFirstNameByPatientId(id,firstName);
    }
    @PutMapping("/patient/update/{id}/lastname")
    public void updatePatientLastName(@PathVariable(name="id") int id,
                                       @RequestParam("lastName") String lastName) throws PatientException{
        patientService.updateLastNameByPatientId(id,lastName);
    }

    @PutMapping("/patient/update/{id}/address")
    public void updatePatientAddress(@PathVariable(name="id") int id,
                                      @RequestParam("address") String address)
            throws PatientException{
        patientService.updateAddressByPatientId(id,address);
    }

    @PutMapping("/patient/update/{id}/gender")
    public void updatePatientGender(@PathVariable(name = "id") int id,
                                    @RequestParam("gender") String gender)
            throws PatientException {
        patientService.updateGenderByPatientId(id, gender);
    }

    @PutMapping("/patient/update/{id}/phonenumber")
    public void updatePatientPhoneNumber(@PathVariable(name="id") int id,
                                         @RequestParam("phonenumber") String phonenumber)
            throws PatientException{
        patientService.updatePhoneNumberByPatientId(id,phonenumber);
    }

    @PutMapping("/patient/update/{id}/medicalrecordnumber")
    public void updatePatientMedicalRecordNumber(@PathVariable(name="id") int id,
                                                 @RequestParam("medicalRecordNumber") String medicalRecordNumber)
            throws PatientException{
        patientService.updateMedicalRecordNumberByPatientId(id,medicalRecordNumber);
    }

    @PutMapping("/patient/update/{id}/height")
    public void updatePatientHeight(@PathVariable(name = "id") int id,
                                    @RequestParam("height") int height)
            throws PatientException {
        patientService.updateHeightByPatientId(id, height);
    }

    @PutMapping("/patient/update/{id}/weight")
    public void updatePatientWeight(@PathVariable(name="id") int id,
                                    @RequestParam("weight") double weight)
            throws PatientException{
        patientService.updateWeightByPatientId(id,weight);
    }
    @PutMapping("/patient/update/{id}/birthdate")
    public void updatePatientWeight(@PathVariable(name="id") int id,
                                    @RequestParam("birthDate") LocalDate birthDate)
            throws PatientException{
        patientService.updateBirthDateByPatientId(id,birthDate);
    }


    @GetMapping("/patient/{id}/insights/query")
    public InsightsData getTotalMeasurements(
            @PathVariable("id") long id,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(name="start") LocalDate startingDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam(name="end") LocalDate endingDate
    ){
        log.info("The end point /patient/id/insights/query has been used.");
        return patientService.getInsightsData(id, startingDate, endingDate);
    }

    @GetMapping("/patient/{id}/glucose/{date}/insights")
    public InsightsGlucoseRecordsData getGlucoseRecordsFromPatientByIdInSpecificDate(
            @PathVariable(name="id") long patientId,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable(name="date") LocalDate date
    ) {
        log.info("The end point /patient/id/glucose/date/insights has been used.");
        Long glucoseId = glucoseService.findGlucoseIdInSpecificDateByPatientId(patientId, date);
        if (glucoseId == null)
            return null;

        return glucoseRecordService.readGlucoseRecordByGlucoseIdForInsights(glucoseId);
    }

}
