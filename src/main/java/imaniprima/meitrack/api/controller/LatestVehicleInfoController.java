package imaniprima.meitrack.api.controller;

import com.querydsl.core.BooleanBuilder;
import imaniprima.meitrack.api.dao.LogAutomaticEventReportDAO;
import imaniprima.meitrack.api.domain.EventsLog;
import imaniprima.meitrack.api.domain.LatestVehicleInfo;
import imaniprima.meitrack.api.domain.QLatestVehicleInfo;
import imaniprima.meitrack.api.exception.ResourceNotFoundException;
import imaniprima.meitrack.api.repository.EventsLogRepository;
import imaniprima.meitrack.api.repository.LatestVehicleInfoRepository;
import imaniprima.meitrack.api.service.OtherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/latestvehicleinfo")
@Api(tags = "Latest Vehicle Info")
public class LatestVehicleInfoController {

    @Autowired
    private LatestVehicleInfoRepository latestVehicleInfoRepo;

    @Autowired
    private EventsLogRepository eventsLogRepo;

    @Autowired
    private LogAutomaticEventReportDAO logAutomaticEventReportDAO;

    @Autowired
    private OtherService otherService;

    @GetMapping(path="/getall")
    @ApiOperation("Get All Vehicles")
    public Page<LatestVehicleInfo> getLatestVehicleInfoAll(
            @RequestHeader("Authorization") String Authorization,
            @RequestParam(name="branch",required = false) String branch,
            Pageable pageRequest){
        try {

            QLatestVehicleInfo vehicleInfo = QLatestVehicleInfo.latestVehicleInfo;
            BooleanBuilder builder = new BooleanBuilder();
            if(branch != null){
                builder.and(vehicleInfo.branch.likeIgnoreCase(branch));
            }

            Page<LatestVehicleInfo> response = latestVehicleInfoRepo.findAll(builder,pageRequest);
            for(LatestVehicleInfo item : response){
                setDetails(item);
            }
            return response;
        }catch (Exception e){
            throw e;
        }
    }

    @GetMapping(path = "/{id}")
    @ApiOperation("Get Annualleave By Id")
    public Optional<LatestVehicleInfo> getAnnualleaveById(@RequestHeader("Authorization") String Authorization,
            @PathVariable(value = "id") Long id) {
        try {
            Optional<LatestVehicleInfo> response = latestVehicleInfoRepo.findById(id);
            if (response.isPresent()) {
                setDetails(response.get());
                return response;
            } else {
                throw new ResourceNotFoundException("Latest Vehicle Info", "Latest Vehicle Info", id);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private void setDetails(LatestVehicleInfo latestVehicleInfo) {
        Optional<EventsLog> eventLog = eventsLogRepo.findLatestEventLogByVehicleId(latestVehicleInfo.getId());
        if(eventLog.isPresent()){
            if(eventLog.get().getStarted()!=null) {
                latestVehicleInfo.setStarted(LocalDateTime.parse(eventLog.get().getStarted() +"T"+ eventLog.get().getTimes()));
            }
            if(eventLog.get().getEnded()!=null){
                latestVehicleInfo.setEnded(LocalDateTime.parse(eventLog.get().getEnded()+"T"+eventLog.get().getEndTimes()));
            }
            latestVehicleInfo.setTimestamp(otherService.convertUTCtoLocalTime(latestVehicleInfo.getTimestamp()));
        }
    }

}
