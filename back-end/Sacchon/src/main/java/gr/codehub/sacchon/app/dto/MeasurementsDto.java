package gr.codehub.sacchon.app.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
public class MeasurementsDto<K> {
    private K value;
    private LocalDate name;

    public MeasurementsDto(K measurement, LocalDate date) {
        this.value = measurement;
        this.name = date;
    }
}
