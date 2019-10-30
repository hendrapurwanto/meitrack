package imaniprima.meitrack.api.controller;

import imaniprima.meitrack.api.domain.Events;
import imaniprima.meitrack.api.domain.LatestAutomaticEventReport;
import imaniprima.meitrack.api.domain.Vehicles;
import imaniprima.meitrack.api.exception.ResourceNotFoundException;
import imaniprima.meitrack.api.repository.EventsRepository;
import imaniprima.meitrack.api.repository.LatestAutomaticEventReportRepository;
import imaniprima.meitrack.api.repository.VehiclesRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.Optional;

@RestController
@RequestMapping(path = "/vehicles")
@Api(tags = "Vehicles")
public class VehiclesController {

    @Autowired
    private VehiclesRepository vehiclesRepo;

    @Autowired
    private EventsRepository eventsRepo;

    @Autowired
    private LatestAutomaticEventReportRepository latestAutomaticEventReportRepo;

    @GetMapping(path="/getall")
    @ApiOperation("Get All Vehicles")
    public Page<Vehicles> getVehiclesAll(@RequestHeader("Authorization") String Authorization,Pageable pageRequest){
        try {
            Page<Vehicles> response = vehiclesRepo.findAll(pageRequest);
            for(Vehicles vehicle : response){
                setDetails(vehicle);
            }
            return response;
        }catch (Exception e){
            throw e;
        }
    }

    @GetMapping(path = "/{id}")
    @ApiOperation("Get Vehicles By Id")
    public Optional<Vehicles> getVehicleById(@RequestHeader("Authorization") String Authorization,
            @PathVariable(value = "id") Long id) {
        try {
            Optional<Vehicles> response = vehiclesRepo.findById(id);
            if (response.isPresent()) {
                setDetails(response.get());
                return response;
            } else {
                throw new ResourceNotFoundException("Vehicles", "Vehicles Id", id);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping(path ="")
    @ApiOperation("Create Vehicles")
    public Vehicles addVehicles(@RequestHeader("Authorization") String Authorization,@RequestBody Vehicles vehicles){
        try {
            Vehicles addVehicles = vehiclesRepo.save(vehicles);
            setDetails(addVehicles);
            return addVehicles;
        }catch (Exception e){
            throw e;
        }
    }

    @PutMapping(path="/{id}")
    @ApiOperation("Update Vehicles")
    public Vehicles updateVehiclesById(@RequestHeader("Authorization") String Authorization,@RequestBody Vehicles vehicles,
       @PathVariable Long id){
        try {
            Optional<Vehicles> vhc = vehiclesRepo.findById(id);
            if(vhc.isPresent()){
                vhc.get().setId(id);
                vhc.get().setBranch(vehicles.getBranch()!=null?vehicles.getBranch():vhc.get().getBranch());
                vhc.get().setCargoH(vehicles.getCargoH() != null?vehicles.getCargoH():vhc.get().getCargoH());
                vhc.get().setCargoL(vehicles.getCargoL() != null?vehicles.getCargoL():vhc.get().getCargoL());
                vhc.get().setCargoW(vehicles.getCargoW() != null?vehicles.getCargoW():vhc.get().getCargoW());
                vhc.get().setCkbFleet(vehicles.getCkbFleet() != null?vehicles.getCkbFleet():vhc.get().getCkbFleet());
                vhc.get().setGps(vehicles.getGps() != null?vehicles.getGps():vhc.get().getGps());
                vhc.get().setImei(vehicles.getImei() != null?vehicles.getImei():vhc.get().getImei());
                vhc.get().setModel(vehicles.getModel() != null?vehicles.getModel():vhc.get().getModel());
                vhc.get().setOperation(vehicles.getOperation() != null?vehicles.getOperation():vhc.get().getOperation());
                vhc.get().setTonCan(vehicles.getTonCan() != null?vehicles.getTonCan():vhc.get().getTonCan());
                vhc.get().setYearMade(vehicles.getYearMade() != null?vehicles.getYearMade():vhc.get().getYearMade());
                vhc.get().setPlate(vehicles.getPlate() != null?vehicles.getPlate():vhc.get().getPlate());

                Vehicles resp = vehiclesRepo.save(vhc.get());
                return resp;
            }else {
                throw new ResourceNotFoundException("Vehicles", "Vehicles Id", id);
            }
        }catch (Exception e){
            throw e;
        }
    }

    @DeleteMapping(path = "/{id}")
    @ApiOperation("Delete By Id")
    public boolean deleteById(@RequestHeader("Authorization") String Authorization,@PathVariable Long id){
        try {
            Optional<Vehicles> response = vehiclesRepo.findById(id);
            if(response.isPresent()){
                vehiclesRepo.deleteById(id);
                return true;
            }else {
                throw new ResourceNotFoundException("Vehicles","Vehicles Id",id);
            }
        }catch (Exception e){
            throw e;
        }
    }

    private void setDetails (Vehicles vehicles) {
        Optional<LatestAutomaticEventReport> laReport = latestAutomaticEventReportRepo.findByImeiNumber(vehicles.getImei());
        if(laReport.isPresent()){
            vehicles.setDtlLatestEvent(laReport.get());
            Optional<Events> event = eventsRepo.findById(laReport.get().getEventCode());
            if(event.isPresent()){
                laReport.get().setEventName(event.get().getName());
                laReport.get().setEventDescription(event.get().getDescription());
            }
        }
    }

}
