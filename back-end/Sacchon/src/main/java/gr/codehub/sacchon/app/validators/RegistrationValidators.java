package gr.codehub.sacchon.app.validators;

import gr.codehub.sacchon.app.dto.PatientDto;
import gr.codehub.sacchon.app.exception.RegisterValidationException;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

/**Validation regarding  empty register values and patient age
 */

@AllArgsConstructor
@Data
@Component
public class RegistrationValidators {
    public void validatePatientData(PatientDto patientDto) throws RegisterValidationException {
        if (org.apache.commons.lang3.StringUtils.isEmpty(patientDto.getFirstName()) ||
                org.apache.commons.lang3.StringUtils.isEmpty(patientDto.getLastName()) ||
                org.apache.commons.lang3.StringUtils.isEmpty(patientDto.getEmail()) ||
                org.apache.commons.lang3.StringUtils.isEmpty(patientDto.getMedicalRecordNumber())) {
            throw new RegisterValidationException("Required fields are missing or empty.");
        }

        LocalDate today = LocalDate.now();
        LocalDate dateOfBirth = patientDto.getDateOfBirth();
        if (dateOfBirth != null && dateOfBirth.plusYears(18).isAfter(today)) {
            throw new RegisterValidationException("Patient must be at least 18 years old.");
        }
    }
}
