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
public class CarbsDto implements Measurement<Long> {

    private int id;
    private final String UNITS = "g";
    private LocalDate date;
    private double measurement;

    public CarbsDto(Carbs carbs) {
        if (carbs == null)
            return;
        date = carbs.getDate();
        measurement = carbs.getMeasurement();
    }

    public Carbs asCarbs() {
        return new Carbs(id, date, measurement);
    }

}
