package imaniprima.meitrack.api.controller;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import imaniprima.meitrack.api.domain.*;
import imaniprima.meitrack.api.repository.EventsLogRepository;
import imaniprima.meitrack.api.repository.EventsRepository;
import imaniprima.meitrack.api.repository.RawMessageRepository;
import imaniprima.meitrack.api.repository.VehiclesRepository;
import imaniprima.meitrack.api.service.StringReplaceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping(path = "/raw_message")
@Api(tags = "Raw Message")
public class RawMessageController {
    @Autowired
    private RawMessageRepository rawMessageRepo;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @GetMapping(path="")
    @ApiOperation("Get All Raw Message")
    public Iterable<RawMessage> getRawMessage(
        @RequestHeader("Authorization") String Authorization,
        @RequestParam(name="message",required = false) String message,
        @ApiParam(name = "type", value = "reply", defaultValue = "reply")
        @RequestParam(name="type",required = false) String type,
        @ApiParam(name = "valid", value = "True", defaultValue = "true")
        @RequestParam(name="valid",required = false) Boolean valid,
        @PageableDefault(page = 0, size = 10)
        @SortDefault.SortDefaults({
                @SortDefault(sort = "id", direction = Sort.Direction.DESC)
        }) Pageable pageRequest) {
        try {

            QRawMessage rawMessage = QRawMessage.rawMessage;
            BooleanBuilder builder = new BooleanBuilder();
            if(message!=null) {
                builder.and(rawMessage.message.likeIgnoreCase("%"+message+"%"));
            }
            if(type!=null) {
                builder.and(rawMessage.type.eq(type));
            }
            if(valid!=null) {
                builder.and(rawMessage.valid.eq(valid));
            }

            Iterable<RawMessage> response = rawMessageRepo.findAll(builder, pageRequest);
            return response;
        }catch (Exception e){
            throw e;
        }
    }
}
