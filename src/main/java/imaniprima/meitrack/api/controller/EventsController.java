package imaniprima.meitrack.api.controller;

import imaniprima.meitrack.api.dao.EventsDAO;
import imaniprima.meitrack.api.domain.Events;
import imaniprima.meitrack.api.exception.ResourceNotFoundException;
import imaniprima.meitrack.api.repository.EventsRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/events")
@Api(tags = "Events")
public class EventsController {
    @Autowired
    private EventsRepository eventsRepo;

    @Autowired
    private EventsDAO eventsDAO;

    @GetMapping(path="/getall")
    @ApiOperation("Get All Events")
    public Iterable<Events> getEventsAll(@RequestHeader("Authorization") String Authorization){
        try {
            Iterable<Events> response = eventsRepo.findAll();
            return response;
        }catch (Exception e){
            throw e;
        }
    }

    @GetMapping(path = "/{id}")
    @ApiOperation("Get Filter By Id")
    public Optional<Events> getAnnualleaveById(
            @RequestHeader("Authorization") String Authorization,
            @PathVariable(value = "id") Long id) {
        try {
            Optional<Events> response = eventsRepo.findById(id);
            if (response.isPresent()) {

                return response;
            } else {
                throw new ResourceNotFoundException("Event", "Event Id", id);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping(path = "/{eventCode}")
    @ApiOperation("Get Filter By Code")
    public Optional<Events> getAnnualleaveByCode(
            @RequestHeader("Authorization") String Authorization,
           @PathVariable(value = "eventCode") Long eventCode) {
        try {
            Optional<Events> response = eventsRepo.findByEventCode(eventCode);
            if (response.isPresent()) {

                return response;
            } else {
                throw new ResourceNotFoundException("Event", "Event Code", eventCode);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping(path="/getcustom")
    @ApiOperation("Get All Events")
    public List<Events> getEventCustom(@RequestHeader("Authorization") String Authorization){
        try {
            List<Events> response = eventsRepo.findAllCustom();
            for(Events values:response){

                Long totalEvent = getTotalEvent(values.getEventCode());
                values.setTotalEvents(totalEvent);
            }
            return response;
        }catch (Exception e){
            throw e;
        }
    }

    public Long getTotalEvent(Long eventCode){
        Long totalEvent = eventsDAO.getTotalEventLog(eventCode);
        return totalEvent;
    }
}
