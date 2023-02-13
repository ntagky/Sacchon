package gr.codehub.sacchon.app.service;

import gr.codehub.sacchon.app.dto.CarbsDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarbsServImpl implements CarbsService {
    @Override
    public CarbsDto createCarbs(CarbsDto carbsDto) {
        return null;
    }

    @Override
    public List<CarbsDto> readCarbs() {
        return null;
    }

    @Override
    public CarbsDto readCarbsById(int id) {
        return null;
    }

    @Override
    public boolean updateCarbs(CarbsDto carbsDto, int id) {
        return false;
    }

    @Override
    public boolean deleteCarbsById(int id) {
        return false;
    }
}
