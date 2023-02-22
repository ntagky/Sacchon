package gr.codehub.sacchon.app.service;

import gr.codehub.sacchon.app.dto.PatientDto;
import gr.codehub.sacchon.app.exception.PatientException;
import gr.codehub.sacchon.app.model.Patient;
import gr.codehub.sacchon.app.repository.DoctorRepository;
import gr.codehub.sacchon.app.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    @Override
    public long getPatientCount(){
        long patientOptional = patientRepository.countPatients();
        return patientOptional;
    }


    @Override
    public void deletePatientById(long patientId) {

        patientRepository.deletePatientById(patientId);
    }


    @Override
    public PatientDto createPatient(PatientDto patientDto) {
        Patient patient = patientDto.asPatient();
        return new PatientDto(patientRepository.save(patient));
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
    public void registerPatient(PatientDto patientDto) {

        Patient patient = patientDto.asPatient();

        patientRepository.registerPatient(patient.getFirstName(), patient.getLastName(), patient.getPassword(), patient.getEmail(),
                patient.getMedicalRecordNumber(), patient.getAddress(), patient.getGender(), patient.getDateOfBirth(),
                patient.getBloodType(), patient.getDiabetesType(), patient.getHeight(), patient.getWeight());
    }

    @Override
    public void updateDoctorIdFromPatient(long patientId, long doctorId) {
        patientRepository.updateDoctorIdFromPatient(patientId, doctorId);
    }


}