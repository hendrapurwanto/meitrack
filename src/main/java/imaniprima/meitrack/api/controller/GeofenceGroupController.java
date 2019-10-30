package imaniprima.meitrack.api.controller;

import imaniprima.meitrack.api.domain.GeofenceArea;
import imaniprima.meitrack.api.domain.GeofenceGroup;
import imaniprima.meitrack.api.dto.GeofenceGroupDTO;
import imaniprima.meitrack.api.repository.GeofenceAreaRepository;
import imaniprima.meitrack.api.repository.GeofenceGroupRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/geofencegroup")
@Api(tags = "Geofence Group")
public class GeofenceGroupController {
    @Autowired
    GeofenceGroupRepository geofenceGroupRepo;

    @Autowired
    GeofenceAreaRepository geofenceAreaRepo;

    private void setDetails (GeofenceGroup geofenceGroup) {
        List<GeofenceArea> responseData = geofenceAreaRepo.findByGroupId(geofenceGroup.getId());
        if(responseData.size() >0){
            geofenceGroup.setGeofenceArea(responseData);

        }
    }
    @GetMapping(path = "getall")
    @ApiOperation("Get All Geofence Group")
    public Iterable<GeofenceGroup> getAllUsers(
            @RequestHeader("Authorization") String Authorization

    ) {
        try {
//            QFilter filter = QFilter.filter;
//            BooleanBuilder builder = new BooleanBuilder();

//            if (code != null)
//                builder.and(filter.code.likeIgnoreCase(code));

            Iterable<GeofenceGroup> response = geofenceGroupRepo.findAll();
            for(GeofenceGroup value:response){
                setDetails(value);
            }
            return response;
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping(path = "getOptions")
    @ApiOperation("Get All Geofence Group for Options")
    public List<GeofenceGroupDTO> getOptions(@RequestHeader("Authorization") String Authorization){

        try {
            List<GeofenceGroupDTO> geofenceGroupDTO = new ArrayList<GeofenceGroupDTO>();
            Iterable<GeofenceGroup> response = geofenceGroupRepo.findAll();
            for(GeofenceGroup values:response){
                GeofenceGroupDTO geofenceGroupDTO1 = new GeofenceGroupDTO();
                geofenceGroupDTO1.setId(values.getId());
                geofenceGroupDTO1.setName(values.getName());
                geofenceGroupDTO.add(geofenceGroupDTO1);
            }
            return geofenceGroupDTO;

        }catch (Exception e){
            throw e;

        }
    }
}
