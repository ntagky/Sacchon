package gr.codehub.sacchon.app.dto;

import gr.codehub.sacchon.app.model.Glucose;
import gr.codehub.sacchon.app.model.GlucoseRecord;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalTime;

@AllArgsConstructor
@Setter
@Getter
public class GlucoseRecordUpdaterDto {

    private int hour;
    private int minute;
    private int second;
    private BigDecimal measurement;

    public GlucoseRecord asGlucoseRecord(Glucose glucose) {
        return new GlucoseRecord(0L, LocalTime.of(hour, minute, second), measurement, glucose);
    }

}
