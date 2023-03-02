package gr.codehub.sacchon.app.service;

import gr.codehub.sacchon.app.dto.InsightsData;
import gr.codehub.sacchon.app.dto.MeasurementsDto;
import gr.codehub.sacchon.app.dto.PastCarbReadingsDto;
import gr.codehub.sacchon.app.dto.PatientDto;
import gr.codehub.sacchon.app.exception.*;
import gr.codehub.sacchon.app.model.ConsultationSeenStatus;
import gr.codehub.sacchon.app.model.Patient;
import gr.codehub.sacchon.app.repository.*;
import gr.codehub.sacchon.app.validators.EmailValidators;
import gr.codehub.sacchon.app.validators.RegistrationValidators;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final CarbsRepository carbsRepository;
    private final ConsultationRepository consultationRepository;
    private final GlucoseRepository glucoseRepository;
    private final GlucoseRecordRepository glucoseRecordRepository;

    private final EmailValidators emailValidators;

    private final RegistrationValidators registrationValidators;


    @Override
    public void deletePatientById(long patientId) throws PatientException {

        Optional<Patient> patientOptional = patientRepository.findById(patientId);

        if (patientOptional.isPresent()) {


            patientRepository.deletePatientAllergies(patientId);
            patientRepository.deletePatientMedications(patientId);
            patientRepository.deletePatientConditions(patientId);
            patientRepository.deleteCarbsByPatientId(patientId);
            patientRepository.deleteMedicationsByPatientId(patientId);
            patientRepository.deleteConsultationsByPatientId(patientId);
            patientRepository.deleteGlucoseRecordByPatientId(patientId);
            patientRepository.deleteGlucoseByPatientId(patientId);
            patientRepository.deletePatientById(patientId);
        }
        else throw new PatientException("Patient not found id= " + patientId);

    }


    @Override
    public List<PastCarbReadingsDto> getPreviousCarbReadingsByPatientIdBetweenDates(long id, LocalDate startingDate, LocalDate endingDate)
    throws PatientException,DateValidationException {

        if (startingDate == null || endingDate == null || startingDate.isAfter(endingDate)) {
            throw new DateValidationException("Invalid starting and/or ending date");
        }

        Optional<Patient> patientOptional = patientRepository.findById(id);
        if (patientOptional.isPresent()) {
        return carbsRepository.getCarbsReadingsBetweenDatesByPatientId(id,startingDate,endingDate);}
        else throw new PatientException("Patient not found id= " + id);
    }


    @Override
    public List<PatientDto> readPatientById(long id) throws PatientException  {

        Optional<Patient> patientOptional = patientRepository.findById(id);
        if (patientOptional.isPresent()) {
        return patientRepository.DisplayAccountData(id).stream().map(PatientDto::new)
                .collect(Collectors.toList());}
        else throw new PatientException("Patient not found id= " + id);}

    @Override
    public Long findDoctorIdByPatientId(long id) throws PatientException  {
        Optional<Patient> patientOptional = patientRepository.findById(id);
        if (patientOptional.isPresent()) {

        return patientRepository.findDoctorIdByPatientId(id);}

        else throw new PatientException("Patient not found id= " + id);}


    private Patient readPatientDb(long id) throws PatientException {
        Optional<Patient> patientOptional = patientRepository.findById(id);
        if (patientOptional.isPresent())
            return   patientOptional.get() ;
        else throw new PatientException("Patient not found id= " + id);
    }
//    @Override
//    public boolean updatePatient(PatientDto patient, long id) {
//        boolean action;
//        try {
//            Patient dbpatient = readPatientDb(id);
//            //// dbpatient.setName(patient.getName());  //todo:implement
//            ///  dbpatient.setEmail(customer.getEmail());
//            patientRepository.save(dbpatient);
//            action = true;
//        } catch (PatientException e) {
//            action = false;
//        }
//        return action;
//    }


    @Override
    public long registerPatient(PatientDto patientDto) throws RegistrationException,RegisterValidationException {

        try {
            Optional<String> existingEmailOptional = patientRepository.findPatientByEmail(patientDto.getEmail());
            if (existingEmailOptional.isPresent()) {
                throw new RegistrationException("Patient with email " + patientDto.getEmail() + " already exists.");
            }

            registrationValidators.validatePatientData(patientDto);
            Patient patient = patientDto.asPatient();
            return patientRepository.save(patient).getId();
        } catch (RegisterValidationException e) {
            throw new RegisterValidationException("Something went wrong!");
        }
    }

    @Override
    public void updateDoctorIdFromPatient(long patientId, long doctorId){
        patientRepository.updateDoctorIdFromPatient(patientId, doctorId);
    }

    @Override
    public LocalDate findDateAssignedFromPatientId(long id) throws PatientException {
        Optional<Patient> patientOptional = patientRepository.findById(id);
        if (patientOptional.isPresent())
            return patientRepository.findDateAssignedFromPatientId(id);
        else throw new PatientException("Patient not found id= " + id);

    }

    @Override
    public void deleteCarbsFromPatientInSpecificDate(long id, LocalDate dateGiven)
            throws PatientException, DateValidationException {

        if (dateGiven == null) {
            throw new DateValidationException("Invalid date value/format");
        }
        Optional<Patient> patientOptional = patientRepository.findById(id);
        if (patientOptional.isPresent()) {
            carbsRepository.deleteCarbsFromPatientInSpecificDate(id, dateGiven);
        } else throw new PatientException("Patient not found id= " + id);

    }

    @Override
    public void updateEmailByPatientId(long id, String email) throws PatientException {
        Optional<Patient> patientOptional = patientRepository.findById(id);
        if (!emailValidators.isValidEmail(email)) {
            throw new PatientException("Invalid email.");

        }
        if (patientOptional.isPresent()) {
            patientRepository.updateEmailFromPatientById(id, email);
        }
        else throw new PatientException("Patient not found id= " + id);
    }

    @Override
    public void updateFirstNameByPatientId(long id, String firstName)
    throws PatientException{

        Optional<Patient> patientOptional = patientRepository.findById(id);
        if (patientOptional.isPresent()) {
        patientRepository.updateFirstNameFromPatientById(id,firstName);}
        else throw new PatientException("Patient not found id= " + id);
    }
    @Override
    public void updateLastNameByPatientId(long id, String lastName)
            throws PatientException{
        Optional<Patient> patientOptional = patientRepository.findById(id);
        if (patientOptional.isPresent()) {
        patientRepository.updateLastNameFromPatientById(id,lastName);}
        else throw new PatientException("Patient not found id= " + id);
    }

    @Override
    public void updateGenderByPatientId(long id, String gender)  throws PatientException {
        Optional<Patient> patientOptional = patientRepository.findById(id);
        if (patientOptional.isPresent()) {
        patientRepository.updateGenderFromPatientById(id,gender);}
        else throw new PatientException("Patient not found id= " + id);
    }

    @Override
    public void updateHeightByPatientId(long id, int height)  throws PatientException {
        Optional<Patient> patientOptional = patientRepository.findById(id);
        if (patientOptional.isPresent()) {
        patientRepository.updateHeightFromPatientById(id,height);}
        else throw new PatientException("Patient not found id= " + id);
    }

    @Override
    public void updateWeightByPatientId(long id, double weight) throws PatientException {
        Optional<Patient> patientOptional = patientRepository.findById(id);
        if (patientOptional.isPresent()) {
            patientRepository.updateWeightFromPatientById(id, weight);
        } else throw new PatientException("Patient not found id= " + id);
    }

    @Override
    public void updateMedicalRecordNumberByPatientId(long id, String medicalRecordNumber)
            throws PatientException {
        Optional<Patient> patientOptional = patientRepository.findById(id);
        if (patientOptional.isPresent()) {
            patientRepository.updateMedicalRecordNumberFromPatientById(id, medicalRecordNumber);
        } else throw new PatientException("Patient not found id= " + id);
    }

    @Override
    public void updatePhoneNumberByPatientId(long id, String phoneNumber) throws PatientException {
        Optional<Patient> patientOptional = patientRepository.findById(id);
        if (patientOptional.isPresent()) {
            patientRepository.updatePhoneNumberFromPatientById(id, phoneNumber);
        } else throw new PatientException("Patient not found id= " + id);
    }

    @Override
    public void updateBirthDateByPatientId(long id, LocalDate birthDate) throws PatientException {
        Optional<Patient> patientOptional = patientRepository.findById(id);
        if (patientOptional.isPresent()) {
            patientRepository.updateBirthDateFromPatientById(id, birthDate);
        } else throw new PatientException("Patient not found id= " + id);
    }

    @Override
    public void updateAddressByPatientId(long id, String address) throws PatientException {
        Optional<Patient> patientOptional = patientRepository.findById(id);
        if (patientOptional.isPresent()) {
            patientRepository.updateAddressFromPatientById(id, address);
        } else throw new PatientException("Patient not found id= " + id);
    }

    @Override
    public InsightsData getInsightsData(long id, LocalDate startingDate, LocalDate endingDate) {
        String daysDescription = (LocalDate.now().equals(endingDate) ? "Last " : "In ") + ChronoUnit.DAYS.between(startingDate, endingDate) + " days";

        LocalDate dateIndex = startingDate;

        List<MeasurementsDto<Integer, LocalDate>> carbsList = new ArrayList<>();
        List<MeasurementsDto<BigDecimal, LocalDate>> glucoseList = new ArrayList<>();

        Long longHolder;
        while (dateIndex.isBefore(endingDate)) {
            // Get average glucose of the day
            longHolder = glucoseRepository.findGlucoseIdInSpecificDateByPatientId(id, dateIndex);

            glucoseList.add(new MeasurementsDto<>(
                    longHolder != null ? glucoseRecordRepository.findAverageGlucoseRecordsByGlucoseId(longHolder) : new BigDecimal(0),
                    dateIndex
            ));

            // Get carbs of the day
            carbsList.add(new MeasurementsDto<>(
                    carbsRepository.readCarbsByPatientIdInSpecificDate(id, dateIndex),
                    dateIndex
            ));

            dateIndex = dateIndex.plusDays(1);
        }

        BigDecimal glucoseAverage = new BigDecimal(0);
        if (glucoseList.size() > 0) {
            glucoseAverage = glucoseList.stream().map(MeasurementsDto::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);
            glucoseAverage = glucoseAverage.divide(new BigDecimal(glucoseList.size()), MathContext.DECIMAL128);
        }

        Long consultationCount = consultationRepository.findConsultationCountByPatientId(id);
        consultationCount = consultationCount != null ? consultationCount : 0;

        int averageCarbs = 0;
        if (carbsList.size() > 0)
            averageCarbs = carbsList.stream().map(MeasurementsDto::getValue).mapToInt(Integer::intValue).sum() / carbsList.size();

        return new InsightsData(
                daysDescription,
                carbsList.size() + glucoseList.size(),
                consultationCount,
                averageCarbs,
                glucoseAverage,
                carbsList,
                glucoseList
        );
    }

    @Override
    public void updateSeenConsultation(long consultationId, int status) throws ConsultationException {
        try {
            consultationRepository.updateSeenConsultationById(consultationId, status);
        } catch (Exception c) {
            throw new ConsultationException("Could not found consultation to update.");
        }
    }
}