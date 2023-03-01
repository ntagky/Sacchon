package gr.codehub.sacchon.app.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MeasurementsDto<K, V> {
    private K value;
    private V name;

    public MeasurementsDto(K measurement, V date) {
        this.value = measurement;
        this.name = date;
    }
}
