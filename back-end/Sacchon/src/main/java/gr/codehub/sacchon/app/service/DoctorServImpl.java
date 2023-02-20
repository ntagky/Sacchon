package gr.codehub.sacchon.app.service;

import gr.codehub.sacchon.app.dto.DoctorDto;
import gr.codehub.sacchon.app.dto.DoctorNameAndEmailDto;
import gr.codehub.sacchon.app.dto.GlucoseFromPersonDto;
import gr.codehub.sacchon.app.exception.DoctorException;
import gr.codehub.sacchon.app.model.Doctor;
import gr.codehub.sacchon.app.repository.DoctorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DoctorServImpl implements DoctorServices{

    private final DoctorRepository doctorRepository;

    @Override
    public DoctorDto createDoctor(DoctorDto doctorDto){
        //validation
        Doctor doctor = doctorDto.asDoctor();
        return new DoctorDto(doctorRepository.save(doctor));
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
    public DoctorDto readDoctor(int id) throws DoctorException {
        return new DoctorDto(readDoctorDb(id));
    }

    @Override
    public DoctorNameAndEmailDto findDoctorNameAndEmailByPatientId(int id) {
        return new DoctorNameAndEmailDto(doctorRepository.findDoctorNameAndEmailById(id));
    }

    // private method created for internal use
    private Doctor readDoctorDb(int id) throws DoctorException{
        Optional<Doctor> doctorOptional = doctorRepository.findById(id);
        if (doctorOptional.isPresent())
            return doctorOptional.get();
        throw new DoctorException("Doctor with id " + id + "is not found!");
    }

    @Override
    public boolean updateDoctor(DoctorDto doctor, int id){
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
    public boolean deleteDoctor(int id) {
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

}
