package es.middleware.meteo.temperature.infrastructure.inbound.rest;

import es.middleware.meteo.temperature.application.port.input.DeleteCurrentTemperature;
import es.middleware.meteo.temperature.application.port.input.GetCurrentTemperatureCache;
import es.middleware.meteo.temperature.application.service.CurrentTemperatureQueryService;
import es.middleware.meteo.temperature.infrastructure.inbound.rest.mapper.TemperatureDtoMapper;
import es.middleware.meteo.temperature.infrastructure.inbound.rest.model.TemperatureDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Temperature", description = "Middleware Temperature API")
@RequestMapping("/temperature")
public class TemperatureController {

    private final CurrentTemperatureQueryService currentTemperatureQueryService;

    private final GetCurrentTemperatureCache getCurrentTemperatureCache;

    private final DeleteCurrentTemperature deleteCurrentTemperature;

    public TemperatureController(GetCurrentTemperatureCache getCurrentTemperatureCache,
                                 DeleteCurrentTemperature deleteCurrentTemperature,
                                 CurrentTemperatureQueryService currentTemperatureQueryService) {
        this.currentTemperatureQueryService = currentTemperatureQueryService;
        this.getCurrentTemperatureCache = getCurrentTemperatureCache;
        this.deleteCurrentTemperature = deleteCurrentTemperature;
    }

    @GetMapping("/current")
    @Operation(summary = "Returns current temperature for provided coordinates",
                description = "Returns current temperature for provided longitude and latitude")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = { @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TemperatureDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class)) }) })
    public ResponseEntity<TemperatureDto> getTemperature(@RequestParam @Min(-90) @Max(90) double latitude,
                                                         @RequestParam @Min(-180) @Max(180) double longitude) {

        final var temperature = currentTemperatureQueryService.getCurrentTemperature(latitude, longitude);

        return temperature.map(value -> ResponseEntity.status(HttpStatus.OK).body(TemperatureDtoMapper.mapToDto(value))).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @DeleteMapping("/current")
    @Operation(summary = "Deletes current temperature from cache for provided coordinates",
            description = "Deletes current temperature from cache provided longitude and latitude")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class)) }) })
    public ResponseEntity<String> deleteTemperatureFromCache(@RequestParam @Min(-90) @Max(90) double latitude,
                                                         @RequestParam @Min(-180) @Max(180) double longitude) {

        if(getCurrentTemperatureCache
                .getCurrentTemperatureFromCacheIncludingExpired(latitude, longitude)
                .isPresent()) {
            deleteCurrentTemperature.deleteCurrentTemperature(latitude, longitude);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

}
