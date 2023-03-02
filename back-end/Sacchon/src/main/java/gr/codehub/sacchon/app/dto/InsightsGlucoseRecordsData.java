package gr.codehub.sacchon.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InsightsGlucoseRecordsData {
    private List<MeasurementsDto<BigDecimal, LocalTime>> glucoseList;
}
