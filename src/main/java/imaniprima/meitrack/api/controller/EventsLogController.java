package imaniprima.meitrack.api.controller;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import imaniprima.meitrack.api.domain.Events;
import imaniprima.meitrack.api.domain.EventsLog;
import imaniprima.meitrack.api.domain.QEventsLog;
import imaniprima.meitrack.api.domain.Vehicles;
import imaniprima.meitrack.api.repository.EventsLogRepository;
import imaniprima.meitrack.api.repository.EventsRepository;
import imaniprima.meitrack.api.repository.VehiclesRepository;
import imaniprima.meitrack.api.service.StringReplaceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/eventslog")
@Api(tags = "Events Log")
public class EventsLogController {
    @Autowired
    private EventsLogRepository eventsLogRepo;

    @Autowired
    private EventsRepository eventsRepo;

    @Autowired
    private VehiclesRepository vehiclesRepo;

    @Autowired
    private StringReplaceService strReplaceService;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private void setDetails(EventsLog eventsLog){
        if(eventsLog.getEventCode()!=null){
            Optional<Events> events =  eventsRepo.findByEventCode(eventsLog.getEventCode());
            if(events.isPresent()){
                eventsLog.setEventId(events.get().getId());
                eventsLog.setEventName(events.get().getName());
            }
        }
        if(eventsLog.getVehicle()!=null) {
            Optional<Vehicles> vehicles = vehiclesRepo.findById(eventsLog.getVehicle());
            if (vehicles.isPresent()) {
                eventsLog.setVehicleBranch(vehicles.get().getBranch());
                eventsLog.setVehicleName(vehicles.get().getModel());
                eventsLog.setDriverName(vehicles.get().getDriver());
                eventsLog.setVehiclePlate(vehicles.get().getPlate());
                eventsLog.setVehicleType(vehicles.get().getType());
            }
        }

        if(eventsLog.getOutputValue()!=null){
            SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
            String time = df.format(new Date((long) ((Double.parseDouble(eventsLog.getOutputValue()))*60*60*1000)));
            eventsLog.setOutputValueText(time);
        }
    }

    public void  setDuration(EventsLog eventsLog) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeNow = LocalDateTime.now().format(formatter);
        String StartNow = strReplaceService.strReplace(timeNow,"T"," ");
        Integer digitEventTimes = eventsLog.getTimes().toString().length();
        if(digitEventTimes==5){
            eventsLog.setTimes(LocalTime.parse(eventsLog.getTimes()+":10"));
        }
        String dateStart = eventsLog.getStarted()+" "+eventsLog.getTimes();
//        System.err.println(StartNow);
//        System.out.println(dateStart);

        Date d1 = null;
        Date d2  = null;
        try {
            d1 = format.parse(StartNow);
            d2 = format.parse(dateStart);

            long diff = d2.getTime() - d1.getTime();
            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);
            diffDays = Math.abs(diffDays);
            diffHours = Math.abs(diffHours);
            diffMinutes = Math.abs(diffMinutes);
            diffSeconds = Math.abs(diffSeconds);
            eventsLog.setDuration(diffDays+" Hari "+diffHours+" Jam "+diffMinutes+" Menit "+diffSeconds+" Detik");
        }catch (Exception e){
            throw e;
        }

    }

    @GetMapping(path="/getall")
    @ApiOperation("Get All Events")
    public Iterable<EventsLog> getEventsLogAll(@RequestHeader("Authorization") String Authorization,
            @ApiParam(name = "startDate", value = "Format Date (Ex. 2019-05-01)", defaultValue = "2019-05-01")
            @RequestParam(name="startDate",required = false) String startDate,
            @ApiParam(name = "startTime", value = "Format Time (Ex. 00:00)", defaultValue = "00:00")
            @RequestParam(name="startTime",required = false) String startTime,
            @ApiParam(name = "endDate", value = "Format Date (Ex. 2019-05-31)", defaultValue = "2019-05-31")
            @RequestParam(name="endDate",required = false) String endDate,
            @ApiParam(name = "endTime", value = "Format Time (Ex. 23:59)", defaultValue = "23:59")
            @RequestParam(name="endTime",required = false) String endTime,
            @RequestParam(name="event",required = false) Long event,
            @RequestParam(name="vehicle",required = false) Long vehicle,
            @RequestParam(name="eventCode",required = false) Long eventCode) throws ParseException {
        try {
            QEventsLog eventsLog = QEventsLog.eventsLog;
            OrderSpecifier<LocalDate> orderSpecifier = QEventsLog.eventsLog.started.asc();
            BooleanBuilder builder = new BooleanBuilder();
            LocalDate todayDate = LocalDate.now();
            Integer lastMonth = todayDate.lengthOfMonth();
            String defaultTimeFrom = "00:00:00";
            String defaultTimeTO = "23:59:00";
            if(startDate != null && endDate != null){
                LocalDate from = LocalDate.parse(startDate);
                LocalDate to = LocalDate.parse(endDate);
                LocalTime timeFrom = LocalTime.parse(startTime);
                LocalTime timeTo = LocalTime.parse(endTime);
                builder.and(eventsLog.started.between(from,to).and(eventsLog.times.between(timeFrom,timeTo)));

            }else{
                LocalDate from = todayDate.withDayOfMonth(1);
                LocalDate to = todayDate.withDayOfMonth(lastMonth);
                LocalTime timeFrom = LocalTime.parse(defaultTimeFrom);
                LocalTime timeTo = LocalTime.parse(defaultTimeTO);
                builder.and(eventsLog.started.between(from,to).and(eventsLog.times.between(timeFrom,timeTo)));
            }
            /*if(startDate != null && startTime != null){
                LocalDate from = LocalDate.parse(startDate);
                LocalDate to = LocalDate.parse(endDate);
                LocalTime timeFrom = LocalTime.parse(startTime);
                LocalTime timeTo = LocalTime.parse(endTime);
                builder.and(eventsLog.started.between(from,to).and(eventsLog.times.between(timeFrom,timeTo)));
            }*/

            /*if(startDate != null && startTime == null){
                builder.and(eventsLog.started.eq(LocalDate.parse(startDate)));
            }

            if(endDate != null && endTime == null){
                builder.and(eventsLog.ended.eq(LocalDate.parse(endDate)));
            }

            if(startTime != null && endTime == null){
                LocalTime timeFrom = LocalTime.parse(startTime);
                LocalTime timeTo = LocalTime.parse(endTime);
                builder.and(eventsLog.times.between(timeFrom,timeTo));
                builder.and(eventsLog.endTimes.between(timeFrom,timeTo));
            }*/

            if(vehicle!=null){
                builder.and(eventsLog.vehicle.eq(vehicle));
            }else{
                List<Long> listVehicle = vehiclesRepo.findListIdVehicle();
                builder.and(eventsLog.vehicle.in(listVehicle));
            }

            if(event != null){
                builder.and(eventsLog.eventId.eq(event));
            }

            if(eventCode != null){
                builder.and(eventsLog.eventCode.eq(eventCode));
            }

            Iterable<EventsLog> response = eventsLogRepo.findAll(builder);
            for(EventsLog value:response){
                setDetails(value);
                setDuration(value);
            }
            return response;
        }catch (Exception e){
            throw e;
        }
    }

    @GetMapping(path="/getallpublic")
    @ApiOperation("Get All Events Public")
    public Iterable<EventsLog> getEventsLogAllPublic(
       @ApiParam(name = "startDate", value = "Format Date (Ex. 2019-05-01)", defaultValue = "2019-05-01")
       @RequestParam(name="startDate",required = false) String startDate,
       @ApiParam(name = "startTime", value = "Format Time (Ex. 00:00)", defaultValue = "00:00")
       @RequestParam(name="startTime",required = false) String startTime,
       @ApiParam(name = "endDate", value = "Format Date (Ex. 2019-05-31)", defaultValue = "2019-05-31")
       @RequestParam(name="endDate",required = false) String endDate,
       @ApiParam(name = "endTime", value = "Format Time (Ex. 23:59)", defaultValue = "23:59")
       @RequestParam(name="endTime",required = false) String endTime,
       @RequestParam(name="event",required = false) Long event,
       @RequestParam(name="vehicle",required = false) Long vehicle,
       @RequestParam(name="eventCode",required = false) Long eventCode) throws ParseException {
        try {
            QEventsLog eventsLog = QEventsLog.eventsLog;
            OrderSpecifier<LocalDate> orderSpecifier = QEventsLog.eventsLog.started.asc();
            BooleanBuilder builder = new BooleanBuilder();
            LocalDate todayDate = LocalDate.now();
            Integer lastMonth = todayDate.lengthOfMonth();
            String defaultTimeFrom = "00:00:00";
            String defaultTimeTO = "23:59:00";
            if(startDate != null && endDate != null){
                LocalDate from = LocalDate.parse(startDate);
                LocalDate to = LocalDate.parse(endDate);
                LocalTime timeFrom = LocalTime.parse(startTime);
                LocalTime timeTo = LocalTime.parse(endTime);
                builder.and(eventsLog.started.between(from,to).and(eventsLog.times.between(timeFrom,timeTo)));

            }else{
                LocalDate from = todayDate.withDayOfMonth(1);
                LocalDate to = todayDate.withDayOfMonth(lastMonth);
                LocalTime timeFrom = LocalTime.parse(defaultTimeFrom);
                LocalTime timeTo = LocalTime.parse(defaultTimeTO);
                builder.and(eventsLog.started.between(from,to).and(eventsLog.times.between(timeFrom,timeTo)));
            }

            if(vehicle!=null){
                builder.and(eventsLog.vehicle.eq(vehicle));
            }else{
                List<Long> listVehicle = vehiclesRepo.findListIdVehicle();
                builder.and(eventsLog.vehicle.in(listVehicle));
            }

            if(event != null){
                builder.and(eventsLog.eventId.eq(event));
            }

            if(eventCode != null){
                builder.and(eventsLog.eventCode.eq(eventCode));
            }

            Iterable<EventsLog> response = eventsLogRepo.findAll(builder);
            for(EventsLog value:response){
                setDetails(value);
                setDuration(value);
            }
            return response;
        }catch (Exception e){
            throw e;
        }
    }
}
