package dev.scibaric.meterreadings.controller;

import dev.scibaric.meterreadings.dto.MeterReadingDTO;
import dev.scibaric.meterreadings.exception.ApiExceptionResponse;
import dev.scibaric.meterreadings.exception.ResourceNotFoundException;
import dev.scibaric.meterreadings.service.MeterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/meters")
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
    @Operation(summary = "Aggregates electricity consumption",
            description = "Aggregates electricity consumption by meter id and year")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Returns electricity consumption aggregated by meter and year",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MeterReadingDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Meter id or year not valid",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiExceptionResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Meter readings do not exist",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiExceptionResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Service error",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiExceptionResponse.class))})
    })
    @GetMapping("/{id}/consumption/aggregation/{year}")
    public ResponseEntity<MeterReadingDTO> aggregateConsumptionByMeterIdAndYear(
            @PathVariable @Parameter(description = "Meter id", example = "1") Long id,
            @PathVariable @Parameter(description = "Year", example = "2020") Integer year) {
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
    @Operation(summary = "Returns meter readings for year",
            description = "Returns meter readings for year by meter id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Returns meter readings for year",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MeterReadingDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Meter id or year not valid",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiExceptionResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Meter readings do not exist",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiExceptionResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Service error",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiExceptionResponse.class))})
    })
    @GetMapping("/{id}/{year}")
    public ResponseEntity<MeterReadingDTO> findByMeterIdAndYear(
            @PathVariable @Parameter(description = "Meter id", example = "1") Long id,
            @PathVariable @Parameter(description = "Year", example = "2020") Integer year) {
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
    @Operation(summary = "Returns meter readings for year and month",
            description = "Returns meter readings for year and month by meter id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Returns meter readings for year and month",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MeterReadingDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Meter id, year or month not valid",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiExceptionResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Meter readings do not exist",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiExceptionResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Service error",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiExceptionResponse.class))})
    })
    @GetMapping("/{id}/{year}/{month}")
    public ResponseEntity<MeterReadingDTO> findByMeterIdAndYearAndMonth(
            @PathVariable @Parameter(description = "Meter id", example = "1") Long id,
            @PathVariable @Parameter(description = "Year", example = "2020") Integer year,
            @PathVariable @Parameter(description = "Month", example = "2") Integer month) {
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
    @Operation(summary = "Save meter reading", description = "Save meter reading to database")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Save meter reading",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MeterReadingDTO.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Meter id, year, month not valid or meter reading already exist",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiExceptionResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Service error",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiExceptionResponse.class))})
    })
    @PostMapping("/reading")
    public ResponseEntity<MeterReadingDTO> saveMeterReading(@RequestBody MeterReadingDTO meterReadingDTO) {
        return ResponseEntity.created(null).body(service.saveMeterReading(meterReadingDTO));
    }

    /**
     * Method accepts parameter <b>meterReadingDTO</b> as JSON object. Object is then validated and if there is
     * meter reading for meter with id, year and month object is mapped to
     * {@link dev.scibaric.meterreadings.model.MeterReading}. {@link dev.scibaric.meterreadings.model.MeterReading} is
     * propagated to the database if everything is alright. {@link MeterReadingDTO} is returned and wrapped in
     * {@link ResponseEntity}. If meter reading is saved to database, service returns http status 201 CREATED.
     * Method can throw {@link IllegalArgumentException} if JSON object is not valid. Handling exceptions is left to
     * {@link ExceptionHandlerController}.
     *
     * @param meterReadingDTO JSON object for saving meter readings
     * @throws IllegalArgumentException If parameters do not satisfy requirements and if meter reading for meter id
     * year and month does not exist.
     * @return {@link ResponseEntity<MeterReadingDTO>}
     */
    @Operation(summary = "Update meter reading", description = "Update meter reading to database")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Update meter reading",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MeterReadingDTO.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Meter id, year, month not valid or meter reading for update does not exist",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiExceptionResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Service error",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiExceptionResponse.class))})
    })
    @PutMapping("/reading")
    public ResponseEntity<MeterReadingDTO> updateMeterReading(@RequestBody MeterReadingDTO meterReadingDTO) {
        return ResponseEntity.ok(service.updateMeterReading(meterReadingDTO));
    }

    /**
     * Method accepts parameter <b>id</b> as path variable. Method tries to delete meter reading with
     * provided id. If meter reading has been deleted from database, service returns http status 200 OK.
     * Method can throw {@link IllegalArgumentException} if id is not valid or meter reading with id does
     * not exist in database. Handling exceptions is left to {@link ExceptionHandlerController}.
     *
     * @param id Meter reading id
     * @throws IllegalArgumentException If parameter does not satisfy requirements and if meter reading id does
     * not exist.
     * @return {@link ResponseEntity<MeterReadingDTO>}
     */
    @Operation(summary = "Delete meter reading", description = "Delete meter reading by id from database")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Delete meter reading by id",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400",
                    description = "Meter reading id not valid or meter reading does not exist",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiExceptionResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Service error",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ApiExceptionResponse.class))})
    })
    @DeleteMapping("/reading/{id}")
    public ResponseEntity deleteMeterReadingById(
            @PathVariable
            @Parameter(description = "Meter reading id", example = "1") Long id) {
        service.deleteMeterReadingById(id);
        return ResponseEntity.ok(null);
    }
}
