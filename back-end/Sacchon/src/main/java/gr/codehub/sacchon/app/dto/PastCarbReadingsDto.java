package gr.codehub.sacchon.app.dto;

import gr.codehub.sacchon.app.model.Carbs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Setter
@Getter
public class PastCarbReadingsDto  {

    private LocalDate date;
    private Integer measurement;

    public PastCarbReadingsDto(Carbs carbs) {
        if (carbs == null)
            return;
        date = carbs.getDate();
        measurement = carbs.getMeasurement();
    }
}
