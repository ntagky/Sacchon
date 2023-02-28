package gr.codehub.sacchon.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InsightsData {
    private LocalDate dateSigned;
    private long measurements;
    private long consultations;
    private int averageCarbs;
    private BigDecimal averageGlucose;
    private List<MeasurementsDto<Integer>> carbsList;
    private List<MeasurementsDto<BigDecimal>> glucoseList;
}
