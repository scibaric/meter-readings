package dev.scibaric.meterreadings.controller;

import dev.scibaric.meterreadings.dto.MeterReadingDTO;
import dev.scibaric.meterreadings.exception.ResourceNotFoundException;
import dev.scibaric.meterreadings.service.MeterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/meter")
public class MeterController {

    private final MeterService service;

    public MeterController(MeterService service) {
        this.service = service;
    }

    /**
     * Method accepts parameters <b>id</b> and <b>year</b>. Returns aggregated consumption per requested meter id and
     * year. Returned results are stored in MeterReadingDTO and wrapped in {@link ResponseEntity} object. If everything
     * is ok, response is returned with http status is 200 OK. Method can throw {@link IllegalArgumentException} if
     * parameters do not satisfy requirements. Another exception that can be thrown is
     * {@link ResourceNotFoundException} if results are not found. Handling exceptions is left to
     * {@link dev.scibaric.meterreadings.controller.ExceptionHandlerController}
     *
     * @param id Meter id
     * @param year Year
     * @throws IllegalArgumentException If parameters do not satisfy requirements.
     * @throws ResourceNotFoundException If results are not found
     * @return ResponseEntity<MeterReadingDTO>
     */
    @GetMapping("/{id}/consumption/aggregation/year/{year}")
    public ResponseEntity<MeterReadingDTO> aggregateConsumptionByMeterIdAndYear(@PathVariable Long id, @PathVariable Integer year) {
        return ResponseEntity.ok(service.aggregateConsumptionByMeterIdAndYear(id, year));
    }

    /**
     * Method accepts parameters <b>id</b> and <b>year</b>. Return results requested by meter id and year.
     * Returned results are stored in {@link MeterReadingDTO} and wrapped in {@link ResponseEntity} object. If everything is ok,
     * response is returned with http status is 200 OK. Method can throw {@link IllegalArgumentException} if parameters
     * do not satisfy requirements. Another exception that can be thrown is {@link ResourceNotFoundException} if
     * results are not found. Handling exceptions is left to
     * {@link ExceptionHandlerController}.
     *
     * @param id Meter id
     * @param year Year
     * @throws IllegalArgumentException If parameters do not satisfy requirements.
     * @throws ResourceNotFoundException If results are not found
     * @return {@link ResponseEntity<MeterReadingDTO>}
     */
    @GetMapping("/{id}/year/{year}")
    public ResponseEntity<MeterReadingDTO> findByMeterIdAndYear(@PathVariable Long id, @PathVariable Integer year) {
        return ResponseEntity.ok(service.findByMeterIdAndYear(id, year));
    }

    /**
     * Method accepts parameters <b>id</b>, <b>year</b> and <b>month</b>. Return results requested by meter id, year
     * and month. Returned results are stored in {@link MeterReadingDTO} and wrapped in {@link ResponseEntity} object.
     * If everything is ok, response is returned with http status is 200 OK.
     * Method can throw {@link IllegalArgumentException} if parameters do not satisfy requirements. Another exception
     * that can be thrown is {@link ResourceNotFoundException} if results are not found. Handling exceptions is left to
     * {@link ExceptionHandlerController}.
     *
     * @param id Meter id
     * @param year Year
     * @param month Month
     * @throws IllegalArgumentException If parameters do not satisfy requirements.
     * @throws ResourceNotFoundException If results are not found
     * @return {@link ResponseEntity<MeterReadingDTO>}
     */
    @GetMapping("/{id}/year/{year}/month/{month}")
    public ResponseEntity<MeterReadingDTO> findByMeterIdAndYearAndMonth(@PathVariable Long id,
                                                                        @PathVariable Integer year,
                                                                        @PathVariable Integer month) {
        return ResponseEntity.ok(service.findByMeterIdAndYearAndMonth(id, year, month));
    }

    /**
     * Method accepts parameter <b>meterReadingDTO</b> as JSON object. Object is then validated and if there is no
     * meter reading for meter with id, year and month object is mapped to
     * {@link dev.scibaric.meterreadings.model.MeterReading}. {@link dev.scibaric.meterreadings.model.MeterReading} is
     * propagated to the database if everything is alright. {@link MeterReadingDTO} is returned and wrapped in
     * {@link ResponseEntity}. If meter reading is saved to database, service returns http status 201 CREATED.
     * Method can throw {@link IllegalArgumentException} if JSON object is not valid. Handling exceptions is left to
     * {@link ExceptionHandlerController}.
     *
     * @param meterReadingDTO JSON object for saving meter readings
     * @throws IllegalArgumentException If parameters do not satisfy requirements and if meter reading for meter id
     * year and month exist.
     * @return {@link ResponseEntity<MeterReadingDTO>}
     */
    @PostMapping("/reading")
    public ResponseEntity<MeterReadingDTO> saveMeterReading(@RequestBody MeterReadingDTO meterReadingDTO) {
        return ResponseEntity.created(null).body(service.saveMeterReading(meterReadingDTO));
    }

}
