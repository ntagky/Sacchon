package gr.codehub.sacchon.app.service;

import gr.codehub.sacchon.app.dto.GlucoseRecordDto;
import gr.codehub.sacchon.app.exception.GlucoseRecordException;
import gr.codehub.sacchon.app.model.GlucoseRecord;
import gr.codehub.sacchon.app.repository.GlucoseRecordRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GlucoseRecordServImpl implements GlucoseRecordService {
    private final GlucoseRecordRepository glucoseRecordRepository;

    @Override
    public GlucoseRecordDto createGlucoseRecord(GlucoseRecordDto carbsDto) {
        return new GlucoseRecordDto(
                glucoseRecordRepository.save(carbsDto.asGlucoseMeasurementRecord())
        );
    }
    @Override
    public List<GlucoseRecordDto> readGlucoseRecord() {
        return glucoseRecordRepository
                .findAll()
                .stream()
                .map(GlucoseRecordDto::new)
                .collect(Collectors.toList());
    }
    @Override
    public GlucoseRecordDto readGlucoseRecordById(int id) throws GlucoseRecordException {
        return new GlucoseRecordDto(readGlucoseRecordDb(id));
    }

    @Override
    public BigDecimal readAverageDailyGlucoseByGlucoseId(int id) {
        return glucoseRecordRepository
                .findGlucoseRecordsByGlucoseId(id);
    }

    private GlucoseRecord readGlucoseRecordDb(int id) throws GlucoseRecordException {
        Optional<GlucoseRecord> glucoseRecordOptional = glucoseRecordRepository.findById(id);
        if (glucoseRecordOptional.isPresent())
            return glucoseRecordOptional.get();
        throw new GlucoseRecordException("Glucose Record with id " + id + " does not exist.");
    }
    @Override
    public boolean updateGlucoseRecord(GlucoseRecordDto glucoseRecordDto, int id) throws GlucoseRecordException {
        GlucoseRecordDto dbGlucoseRecord = readGlucoseRecordById(id);
        dbGlucoseRecord.setId(id);
        dbGlucoseRecord.setMeasurement(glucoseRecordDto.getMeasurement());
        dbGlucoseRecord.setTime(glucoseRecordDto.getTime());
        dbGlucoseRecord.setGlucose(glucoseRecordDto.getGlucose());
        glucoseRecordRepository.save(dbGlucoseRecord.asGlucoseMeasurementRecord());
        return true;
    }
    @Override
    public boolean deleteGlucoseRecordById(int id) throws GlucoseRecordException {
        glucoseRecordRepository.delete(readGlucoseRecordDb(id));
        return true;
    }
}
