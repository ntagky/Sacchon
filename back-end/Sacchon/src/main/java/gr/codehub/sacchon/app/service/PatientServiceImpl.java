package gr.codehub.sacchon.app.service;

import gr.codehub.sacchon.app.dto.InsightsData;
import gr.codehub.sacchon.app.dto.MeasurementsDto;
import gr.codehub.sacchon.app.dto.PastCarbReadingsDto;
import gr.codehub.sacchon.app.dto.PatientDto;
import gr.codehub.sacchon.app.exception.PatientException;
import gr.codehub.sacchon.app.model.Patient;
import gr.codehub.sacchon.app.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Service
@AllArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final CarbsRepository carbsRepository;
    private final ConsultationRepository consultationRepository;
    private final GlucoseRepository glucoseRepository;
    private final GlucoseRecordRepository glucoseRecordRepository;

    @Override
    public long getPatientCount(){
        long patientOptional = patientRepository.countPatients();
        return patientOptional;
    }

    @Override
    public void deletePatientById(long patientId) {

        //DBO REMOVAL

        patientRepository.deleteDboPatientAllergies(patientId);
        patientRepository.deleteDboPatientMedications(patientId);
        patientRepository.deleteDboPatientConditions(patientId);
//        patientRepository.deleteDboConsultationMedications(patientId);

        //SCHEMA REMOVAL

        patientRepository.deleteCarbsByPatientId(patientId);
        patientRepository.deleteMedicationsByPatientId(patientId);
        patientRepository.deleteConsultationsByPatientId(patientId);
        patientRepository.deleteGlucoseRecordByPatientId(patientId);
        patientRepository.deleteGlucoseByPatientId(patientId);
        patientRepository.deletePatientById(patientId);

    }


    @Override
    public List<PastCarbReadingsDto> getPreviousCarbReadingsByPatientIdBetweenDates(long id, LocalDate startingDate, LocalDate endingDate) {
        return carbsRepository.getCarbsReadingsBetweenDatesByPatientId(id,startingDate,endingDate);
    }

    @Override
    public List<PatientDto> readPatient() {
        return patientRepository
                .findAll()
                .stream()
                .map(PatientDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<PatientDto> readPatientById(long id)  {
        return patientRepository.DisplayAccountData(id).stream().map(PatientDto::new)            .collect(Collectors.toList());}

    @Override
    public long findDoctorIdByPatientId(long id) {
        return patientRepository.findDoctorIdByPatientId(id);
    }

    private Patient readPatientDb(long id) throws PatientException {
        Optional<Patient> patientOptional = patientRepository.findById(id);
        if (patientOptional.isPresent())
            return   patientOptional.get() ;
        throw new PatientException("Customer not found id= " + id);
    }
    @Override
    public boolean updatePatient(PatientDto patient, long id) {
        boolean action;
        try {
            Patient dbpatient = readPatientDb(id);
            //// dbpatient.setName(patient.getName());  //todo:implement
            ///  dbpatient.setEmail(customer.getEmail());
            patientRepository.save(dbpatient);
            action = true;
        } catch (PatientException e) {
            action = false;
        }
        return action;
    }

//    @Override
//    public boolean deletePatientById(long id) {
//        boolean action;
//        try {
//            Patient dbCustomer = readPatientDb(id);
//            patientRepository.delete(dbCustomer);
//            action = true;
//        } catch (PatientException e) {
//            action = false;
//        }
//        return action;
//    }

    @Override
    public long registerPatient(PatientDto patientDto) {

        Patient patient = patientDto.asPatient();

        patientRepository.createPatient(patient.getFirstName(), patient.getLastName(), patient.getPassword(), patient.getEmail(),
                patient.getMedicalRecordNumber(), patient.getAddress(), patient.getGender(), patient.getDateOfBirth(),
                patient.getBloodType().name(), patient.getDiabetesType().name(), patient.getHeight(), patient.getWeight(), patient.getSignedDate()
                ,patient.getPhoneNumber());

        return patientRepository.save(patient).getId();
    }

    @Override
    public void updateDoctorIdFromPatient(long patientId, long doctorId) {
        patientRepository.updateDoctorIdFromPatient(patientId, doctorId);
    }

    @Override
    public LocalDate findDateAssignedFromPatientId(long id) {
        return patientRepository.findDateAssignedFromPatientId(id);
    }

    @Override
    public void deleteCarbsFromPatientInSpecificDate(long id, LocalDate dateGiven) {
        carbsRepository.deleteCarbsFromPatientInSpecificDate(id, dateGiven);
    }

    @Override
    public InsightsData getInsightsData(long id, LocalDate startingDate, LocalDate endingDate) {
        LocalDate dateIndex = startingDate;

        List<MeasurementsDto<Integer>> carbsList = new ArrayList<>();
        List<MeasurementsDto<BigDecimal>> glucoseList = new ArrayList<>();

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
                startingDate,
                carbsList.size() + glucoseList.size(),
                consultationCount,
                averageCarbs,
                glucoseAverage,
                carbsList,
                glucoseList
        );
    }


}