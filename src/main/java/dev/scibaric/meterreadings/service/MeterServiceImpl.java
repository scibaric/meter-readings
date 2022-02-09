package dev.scibaric.meterreadings.service;

import dev.scibaric.meterreadings.dto.MeterReadingDTO;
import dev.scibaric.meterreadings.exception.ResourceNotFoundException;
import dev.scibaric.meterreadings.model.Meter;
import dev.scibaric.meterreadings.model.MeterReading;
import dev.scibaric.meterreadings.repository.MeterReadingRepository;
import dev.scibaric.meterreadings.validator.Validator;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
public class MeterServiceImpl implements MeterService {

    private final MeterReadingRepository meterReadingRepository;

    private final Validator validator;

    public MeterServiceImpl(MeterReadingRepository meterReadingRepository, Validator validator) {
        this.meterReadingRepository = meterReadingRepository;
        this.validator = validator;
    }

    @Override
    public MeterReadingDTO aggregateConsumptionByMeterIdAndYear(Long id, Integer year) {
        validator.validateMeterId(id);
        validator.validateYear(year);

        Integer consumedPower = meterReadingRepository.aggregateConsumptionByMeterIdAndYear(id, year);

        if (isNull(consumedPower))
            throw new ResourceNotFoundException(String.format("Meter readings for meter id %d and year %d does not exist", id, year));

        MeterReadingDTO meter = new MeterReadingDTO();
        meter.setYear(year);
        meter.setTotal(consumedPower);

        return meter;
    }

    @Override
    public MeterReadingDTO findByMeterIdAndYear(Long id, Integer year) {
        validator.validateMeterId(id);
        validator.validateYear(year);

        List<MeterReading> meterReadings = meterReadingRepository.findMeterReadingsByMeterIdAndYear(id, year);

        if (CollectionUtils.isEmpty(meterReadings))
            throw new ResourceNotFoundException(String.format("Meter readings for meter id %d and year %d does not exist", id, year));

        Map<String, Integer> monthlyEnergyConsumption = meterReadings.stream()
                .collect(Collectors.toMap(this::meterReadingMonth, MeterReading::getEnergyConsumed));

        MeterReadingDTO meterReadingDTO = new MeterReadingDTO();
        meterReadingDTO.setYear(year);
        meterReadingDTO.setMonthlyEnergyConsumption(monthlyEnergyConsumption);

        return meterReadingDTO;
    }

    @Override
    public MeterReadingDTO findByMeterIdAndYearAndMonth(Long id, Integer year, Integer month) {
        validator.validateMeterId(id);
        validator.validateYear(year);
        validator.validateMonth(month);
        String m = Month.of(month).getDisplayName(TextStyle.FULL, Locale.ENGLISH);

        MeterReading meterReading = meterReadingRepository.findMeterReadingByMeterIdAndYearAndMonth(id, year, month);

        if (isNull(meterReading))
            throw new ResourceNotFoundException(String.format("Meter reading for meter id %d, year %d and month %s does not exist", id, year, m));

        return meterReadingToMeterReadingDTO(meterReading);
    }

    @Override
    public MeterReadingDTO saveMeterReading(MeterReadingDTO meterReadingDTO) {
        validator.validateMeterReadingDTO(meterReadingDTO);
        String m = Month.of(meterReadingDTO.getMonth()).getDisplayName(TextStyle.FULL, Locale.ENGLISH);

        MeterReading meterReading = meterReadingRepository.findMeterReadingByMeterIdAndYearAndMonth(
                meterReadingDTO.getMeterId(), meterReadingDTO.getYear(), meterReadingDTO.getMonth()
        );

        if (nonNull(meterReading))
            throw new IllegalArgumentException(
                    String.format("Meter reading for meter id %d, year %d and month %s already exists",
                            meterReadingDTO.getMeterId(), meterReadingDTO.getYear(), m));

        Meter meter = new Meter(meterReadingDTO.getMeterId());
        meterReading = new MeterReading(meter, meterReadingDTO.getYear(), meterReadingDTO.getMonth(), meterReadingDTO.getEnergyConsumed());
        meterReadingRepository.save(meterReading);

        return meterReadingDTO;
    }

    private MeterReadingDTO meterReadingToMeterReadingDTO(MeterReading meterReading) {
        String m = Month.of(meterReading.getMonth()).getDisplayName(TextStyle.FULL, Locale.ENGLISH);

        Map<String, Integer> monthlyEnergyConsumption = new HashMap<>();
        monthlyEnergyConsumption.put(m, meterReading.getEnergyConsumed());

        return new MeterReadingDTO(meterReading.getYear(), monthlyEnergyConsumption);
    }

    private String meterReadingMonth(MeterReading meterReading) {
        return Month.of(meterReading.getMonth()).getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    }
}
