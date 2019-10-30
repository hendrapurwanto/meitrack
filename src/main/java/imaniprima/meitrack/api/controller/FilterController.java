package imaniprima.meitrack.api.controller;

import com.querydsl.core.BooleanBuilder;
import imaniprima.meitrack.api.domain.Filter;
import imaniprima.meitrack.api.domain.QFilter;
import imaniprima.meitrack.api.exception.ResourceNotFoundException;
import imaniprima.meitrack.api.repository.FilterRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/filter")
@Api(tags = "Filter")
public class FilterController {

    @Autowired
    private FilterRepository filterRepo;

    @GetMapping(path = "getall")
    @ApiOperation("Get All Filter")
    public Iterable<Filter> getAllUsers(
            @RequestHeader("Authorization") String Authorization,
            @RequestParam(name = "code", required = false) String code
    ) {
        try {
            QFilter filter = QFilter.filter;
            BooleanBuilder builder = new BooleanBuilder();

            if (code != null)
                builder.and(filter.code.likeIgnoreCase(code));

            Iterable<Filter> response = filterRepo.findAll();

            return response;
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping(path = "/{id}")
    @ApiOperation("Get Filter By Id")
    public Optional<Filter> getAnnualleaveById(
            @RequestHeader("Authorization") String Authorization,
         @PathVariable(value = "id") Long id) {
        try {
            Optional<Filter> response = filterRepo.findById(id);
            if (response.isPresent()) {

                return response;
            } else {
                throw new ResourceNotFoundException("Filter", "Filter Id", id);
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
