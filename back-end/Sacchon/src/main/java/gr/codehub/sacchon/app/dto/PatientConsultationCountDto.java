package gr.codehub.sacchon.app.dto;

import gr.codehub.sacchon.app.model.Patient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PatientConsultationCountDto extends PatientDto {

    private long consultationCount;

    public PatientConsultationCountDto(Patient patient, long consultationCount) {
        super(patient);
        this.consultationCount = consultationCount;
    }
}
