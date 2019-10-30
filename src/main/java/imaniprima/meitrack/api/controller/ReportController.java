package imaniprima.meitrack.api.controller;

import imaniprima.meitrack.api.dto.LogAutomaticEventReportDetailDTO;
import imaniprima.meitrack.api.service.JasperReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("report")
@Api(tags = "Report")
public class ReportController {

    @Autowired
    private JasperReportService jasperReportService;



    @GetMapping(path = "/report_ckb/download")
    public void exportReportCKB(
        @RequestParam("Authorization") String Authorization,
        @ApiParam(name = "startDate", value = "Format Date (Ex. 2019-02-01)", defaultValue = "2019-02-01")
        @RequestParam(name="startDate",required = false) String startDate,
        @ApiParam(name = "endDate", value = "Format Date (Ex. 2019-06-01)", defaultValue = "2019-06-01")
        @RequestParam(name="endDate",required = false) String endDate,
        HttpServletResponse response
    ) throws IOException, JRException, SQLException {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("startDate", java.sql.Date.valueOf(startDate));
        parameters.put("endDate", java.sql.Date.valueOf(endDate));

        String jasperFile = "report_ckb";
        String excellFile = "Report CKB - "+startDate+" - "+endDate;
        jasperReportService.exportToExcell(response, jasperFile, excellFile, parameters);
    }


}
