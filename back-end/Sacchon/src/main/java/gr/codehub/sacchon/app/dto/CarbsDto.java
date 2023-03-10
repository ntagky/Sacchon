package gr.codehub.sacchon.app.dto;

import gr.codehub.sacchon.app.model.Carbs;
import gr.codehub.sacchon.app.model.Measurement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Setter
@Getter
public class CarbsDto implements Measurement<Integer> {

    private Long id;
    private final String UNITS = "g";
    private LocalDate date;
    private Integer measurement;
    private PatientDto patientDto;

    public CarbsDto(Carbs carbs) {
        if (carbs == null)
            return;
        id = carbs.getId();
        date = carbs.getDate();
        measurement = carbs.getMeasurement();
        patientDto = new PatientDto(carbs.getPatient());
    }

    public Carbs asCarbs() {
        return new Carbs(
                id,
                date,
                measurement,
                patientDto.asPatient()
        );
    }

}
