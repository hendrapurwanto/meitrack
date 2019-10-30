package imaniprima.meitrack.api.controller;

import imaniprima.meitrack.api.domain.GeofenceArea;
import imaniprima.meitrack.api.exception.ResourceNotFoundException;
import imaniprima.meitrack.api.repository.GeofenceAreaRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/geofencearea")
@Api(tags = "Geofence Area")
public class GeofenceAreaController {
    @Autowired
    GeofenceAreaRepository geofenceAreaRepo;

    @GetMapping(path = "/{id}")
    @ApiOperation("Get Geofence Area By Id")
    public Optional<GeofenceArea> getGeofenceAreaById(@RequestHeader("Authorization") String Authorization,
                                                      @PathVariable(value = "id") Long id) {
        try {
            Optional<GeofenceArea> response = geofenceAreaRepo.findById(id);
            if (response.isPresent()) {
              return response;
            } else {
                throw new ResourceNotFoundException("Geofence Area", "Geofence Area Id", id);
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
