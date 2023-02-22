package gr.codehub.sacchon.app.dto;

import gr.codehub.sacchon.app.model.Carbs;
import gr.codehub.sacchon.app.model.Measurement;
import gr.codehub.sacchon.app.model.Patient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Setter
@Getter
public class CarbsFromPersonDto implements Measurement<Integer> {

    private Long id;
    private final String UNITS = "g";
    private LocalDate date;
    private Integer measurement;

    public CarbsFromPersonDto(Carbs carbs) {
        if (carbs == null)
            return;
        id = carbs.getId();
        date = carbs.getDate();
        measurement = carbs.getMeasurement();
    }

    public Carbs asCarbs(Patient patient) {
        return new Carbs(
                id,
                date,
                measurement,
                patient
        );
    }
}
