package gr.codehub.sacchon.app.service;

import gr.codehub.sacchon.app.dto.PastCarbReadingsDto;
import gr.codehub.sacchon.app.dto.PatientDto;
import gr.codehub.sacchon.app.exception.PatientException;
import gr.codehub.sacchon.app.model.Patient;
import gr.codehub.sacchon.app.repository.CarbsRepository;
import gr.codehub.sacchon.app.repository.DoctorRepository;
import gr.codehub.sacchon.app.repository.GlucoseRepository;
import gr.codehub.sacchon.app.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final CarbsRepository carbsRepository;



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

//        patientRepository.createPatient(patient.getFirstName(), patient.getLastName(), patient.getPassword(), patient.getEmail(),
//                patient.getMedicalRecordNumber(), patient.getAddress(), patient.getGender(), patient.getDateOfBirth(),
//                patient.getBloodType().name(), patient.getDiabetesType().name(), patient.getHeight(), patient.getWeight(), patient.getSignedDate()
//                ,patient.getPhoneNumber());

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
    public void updateEmailByPatientId(long id, String email) {
        patientRepository.updateEmailFromPatientById(id,email);
    }

    @Override
    public void updateFirstNameByPatientId(long id, String firstName) {
        patientRepository.updateFirstNameFromPatientById(id,firstName);
    }
    @Override
    public void updateLastNameByPatientId(long id, String lastName) {
        patientRepository.updateLastNameFromPatientById(id,lastName);
    }

    @Override
    public void updateGenderByPatientId(long id, String gender) {
        patientRepository.updateGenderFromPatientById(id,gender);
    }

    @Override
    public void updateHeightByPatientId(long id, int height) {
        patientRepository.updateHeightFromPatientById(id,height);
    }

    @Override
    public void updateWeightByPatientId(long id, double weight) {
        patientRepository.updateWeightFromPatientById(id,weight);
    }

    @Override
    public void updateMedicalRecordNumberByPatientId(long id, String medicalRecordNumber) {
        patientRepository.updateMedicalRecordNumberFromPatientById(id,medicalRecordNumber);
    }

    @Override
    public void updatePhoneNumberByPatientId(long id, String phoneNumber) {
        patientRepository.updatePhoneNumberFromPatientById(id,phoneNumber);
    }

    @Override
    public void updateBirthDateByPatientId(long id, LocalDate birthDate) {
        patientRepository.updateBirthDateFromPatientById(id,birthDate);
    }

    @Override
    public void updateAddressByPatientId(long id, String address) {
        patientRepository.updateAddressFromPatientById(id,address);
    }





}