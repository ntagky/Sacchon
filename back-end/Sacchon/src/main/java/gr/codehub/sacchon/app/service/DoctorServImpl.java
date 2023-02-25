package gr.codehub.sacchon.app.service;

import gr.codehub.sacchon.app.dto.ConsultationWriterDto;
import gr.codehub.sacchon.app.dto.DoctorDto;
import gr.codehub.sacchon.app.dto.PatientDto;
import gr.codehub.sacchon.app.exception.DoctorException;
import gr.codehub.sacchon.app.model.Consultation;
import gr.codehub.sacchon.app.model.Doctor;
import gr.codehub.sacchon.app.model.Patient;
import gr.codehub.sacchon.app.repository.ConsultationRepository;
import gr.codehub.sacchon.app.repository.DoctorRepository;
import gr.codehub.sacchon.app.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DoctorServImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final ConsultationRepository consultationRepository;
    private final PatientRepository patientRepository;

    @Override
    public DoctorDto createDoctor(DoctorDto doctorDto){
        //validation
        Doctor doctor = doctorDto.asDoctor();
        return new DoctorDto(doctorRepository.save(doctor));
    }

    @Override
    public long registerDoctor(DoctorDto doctorDto) {
        Doctor doctor = doctorDto.asDoctor();
        doctorRepository.registerDoctor(
                doctor.getFirstName(),
                doctor.getLastName(),
                doctor.getPassword(),
                doctor.getEmail(),
                doctor.getSignedDate(),
                doctor.getPhoneNumber());

        return doctorRepository.save(doctor).getId();
    }

    @Override
    public List<DoctorDto> readDoctor(){
        return doctorRepository
                .findAll()
                .stream()
                .map(DoctorDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<DoctorDto> readDoctorByEmailNativeService(String match) {
       return doctorRepository
               .findDoctorByEmailNative(match)
               .stream()
               .map(DoctorDto::new)
               .collect(Collectors.toList());
    }

    @Override
    public List<PatientDto> readPatientsWithNoConsultation(LocalDate dateGiven) {

        List<Long> allPatients = patientRepository.findAll()
                .stream()
                .map(p -> p.getId())
                .collect(Collectors.toList());

        List<Long> patientsActiveConsultation = consultationRepository.findPatientWithActiveConsultation(dateGiven);

        List<PatientDto> inactivePatients = new ArrayList<>();
        allPatients.forEach(
                idAll -> {
                    if (patientsActiveConsultation.stream().noneMatch(id -> id.equals(idAll)))
                        inactivePatients.add(
                                new PatientDto(patientRepository.findById(idAll).get())
                        );
                }
        );
        return inactivePatients;
    }

    @Override
    public DoctorDto readDoctor(long id) throws DoctorException {
        return new DoctorDto(readDoctorDb(id));
    }

    @Override
    public DoctorDto readDoctorNameAndEmailById(long id) {
        return new DoctorDto(doctorRepository.findDoctorNameAndEmailById(id));
    }

    @Override
    public DoctorDto readDoctorById(long id) {
        return new DoctorDto(doctorRepository.DisplayAccountData(id));
    }

    // private method created for internal use
    private Doctor readDoctorDb(long id) throws DoctorException{
        Optional<Doctor> doctorOptional = doctorRepository.findById(id);
        if (doctorOptional.isPresent())
            return doctorOptional.get();
        throw new DoctorException("Doctor with id " + id + "is not found!");
    }


    @Override
    public boolean updateDoctor(DoctorDto doctor, long id){
        boolean action;
        try {
            Doctor dbDoctor = readDoctorDb(id);
//            dbDoctor.setConsultations(doctor.getConsultations());
//            dbDoctor.setPatients(doctor.getPatients);
            doctorRepository.save(dbDoctor);
            action = true;
        } catch (DoctorException e){
            action = false;
        }
        return action;
    }

    @Override
    public boolean deleteDoctor(long id) {
        boolean action;
        try {
            Doctor dbDoctor = readDoctorDb(id);
            doctorRepository.delete(dbDoctor);
            action = true;
        } catch (DoctorException e) {
            action = false;
        }
        return action;
    }

    @Override
    public void deleteDoctorById(long id) {
        patientRepository.makeDoctorIdNullOnDoctorDelete(id);
        consultationRepository.makeDoctorIdNullOnDoctorDelete(id);
        doctorRepository.deleteDoctorById(id);
    }

    @Override
    public long createConsultation(ConsultationWriterDto consultationWriterDto) {
        Optional<Doctor> doctor = doctorRepository.findById(consultationWriterDto.getDoctorId());
        Optional<Patient> patient = patientRepository.findById(consultationWriterDto.getPatientId());
        if (doctor.isEmpty() || patient.isEmpty())
            return -1;

        return consultationRepository.save(
                new Consultation(
                        0L,
                        doctor.get().getFirstName(),
                        doctor.get().getLastName(),
                        doctor.get().getEmail(),
                        consultationWriterDto.getDateCreated(),
                        false,
                        consultationWriterDto.getMedications(),
                        consultationWriterDto.getDetails(),
                        doctor.get(),
                        patient.get()
                )
        ).getId();
    }

}
