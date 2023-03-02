package gr.codehub.sacchon.app.service;

import gr.codehub.sacchon.app.dto.PortalDto;
import gr.codehub.sacchon.app.model.UserType;
import gr.codehub.sacchon.app.repository.ChiefDoctorRepository;
import gr.codehub.sacchon.app.repository.DoctorRepository;
import gr.codehub.sacchon.app.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PortalServiceImpl implements PortalService {
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final ChiefDoctorRepository chiefDoctorRepository;

    @Override
    public PortalDto findRegisteredUser(String email, String password) {
        Long id;

        System.out.println(email + " " + password);

        id = patientRepository.findPatientByEmailAndPassword(email, password);
        System.out.println(id);
        if (id != null) {
            System.out.println("123");
            return new PortalDto(id, UserType.PATIENT);
        }

        id = doctorRepository.findDoctorByEmailAndPassword(email, password);
        if (id != null)
            return new PortalDto(id, UserType.DOCTOR);

        id = chiefDoctorRepository.findChiefByEmailAndPassword(email, password);
        if (id != null)
            return new PortalDto(id, UserType.CHIEF);

        return new PortalDto(-1, null);
    }
}
