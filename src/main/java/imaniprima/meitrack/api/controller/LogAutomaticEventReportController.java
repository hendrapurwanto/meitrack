package imaniprima.meitrack.api.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.querydsl.core.BooleanBuilder;
import imaniprima.meitrack.api.dao.LogAutomaticEventReportDAO;
import imaniprima.meitrack.api.domain.ApiResponse;
import imaniprima.meitrack.api.domain.LogAutomaticEventReport;
import imaniprima.meitrack.api.dto.LogAutomaticEventReportDTO;
import imaniprima.meitrack.api.dto.LogAutomaticEventReportDetailDTO;
import imaniprima.meitrack.api.dto.LogAutomaticEventReportTemporaryDTO;
import imaniprima.meitrack.api.repository.LogAutomaticEventReportRepository;
import imaniprima.meitrack.api.service.GeoLocationService;
import imaniprima.meitrack.api.service.OtherService;
import imaniprima.meitrack.api.service.StringReplaceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping(path = "/logeventreport")
@Api(tags = "Log Automatic Event Report")
public class LogAutomaticEventReportController {

    @Autowired
    private LogAutomaticEventReportRepository logAutomaticEventReportRepo;

    @Autowired
    private LogAutomaticEventReportDAO logAutomaticEventReportDAO;

    @Autowired
    OtherService otherService;

    @Autowired
    GeoLocationService geoLocationService;

    @Autowired
    StringReplaceService strReplaceService;



    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private DateTimeFormatter formatterDateOnly = DateTimeFormatter.ofPattern("yyyy-MM-dd");




    @GetMapping(path="/getall")
    @ApiOperation("Get All Event Log")
    public List<LogAutomaticEventReportDTO> getLogEventReport(
        @RequestHeader("Authorization") String Authorization,
        @ApiParam(name = "startDate", value = "Format Date (Ex. 2019-05-01)", defaultValue = "2019-06-30")
        @RequestParam(name="startDate",required = false) String startDate,
        @ApiParam(name = "startTime", value = "Format Time (Ex. 00:00)", defaultValue = "00:00")
        @RequestParam(name="startTime",required = false) String startTime,
        @ApiParam(name = "endDate", value = "Format Date (Ex. 2019-05-31)", defaultValue = "2019-07-05")
        @RequestParam(name="endDate",required = false) String endDate,
        @ApiParam(name = "endTime", value = "Format Time (Ex. 23:59)", defaultValue = "23:59")
        @RequestParam(name="endTime",required = false) String endTime,
        @RequestParam (name="imeiNumber",required = false) String imeiNumber,
        @RequestParam (name="plate",required = false) String plate,
        @RequestParam (name="sort",required = false) String sort){
        try {

            LocalDate todayDate = LocalDate.now();
            Integer lastMonth = todayDate.lengthOfMonth();
            LocalDate dateStart;
            LocalDate dateEnd;
            String from;
            String to;


            if(sort == null){
                sort = "timestamp,desc";
            }

            Long numofDays;
            if(startDate != null && endDate != null ){
                 dateStart = LocalDate.parse(startDate,formatterDateOnly);
                 dateEnd = LocalDate.parse(endDate,formatterDateOnly);
                 numofDays = ChronoUnit.DAYS.between(dateStart,dateEnd);
            }else{
                dateStart = todayDate.withDayOfMonth(1);
                dateEnd = todayDate.withDayOfMonth(lastMonth);
                numofDays = ChronoUnit.DAYS.between(dateStart,dateEnd);
            }
            List<LogAutomaticEventReportDTO> listTemp = new ArrayList<>();
            int x = 0;
            imeiNumber = stringToSplit(imeiNumber);
            plate = stringToSplit(plate);
            for(int i = 0; i <= numofDays; i++){

                LocalDate dateIterate = dateStart.plusDays(i);
                from = dateIterate.toString()+" "+startTime+":00";
                to = dateIterate.toString()+" "+endTime+":00";

                from = otherService.convertLocalDateTimeToUTC(from);
                to = otherService.convertLocalDateTimeToUTC(to);

                List<LogAutomaticEventReportDTO> response = logAutomaticEventReportDAO.getLogEventReport(from,to,imeiNumber,plate,sort);

                for(LogAutomaticEventReportDTO value:response){
                    x+=1;
                    LogAutomaticEventReportDTO test = new LogAutomaticEventReportDTO();
                    LocalDateTime timeUTCStart = otherService.convertStringtoLocalDateTime(value.getLogTanggal().toString(),value.getStart().toString());
                    LocalDateTime timeUTCEnd = otherService.convertStringtoLocalDateTime(value.getLogTanggal().toString(),value.getEnd().toString());
                    LocalTime localTimeStart = otherService.convertDatetimetoLocaltime(timeUTCStart);
                    LocalTime localTimeEnd = otherService.convertDatetimetoLocaltime(timeUTCEnd);
                    LocalDate newLocalDate= otherService.convertDatetoLocalDate(timeUTCStart);
                    String logDate = value.getLogTanggal()+" "+value.getEnd();
                    Long imeiNumberParam = value.getImeiNumber();
                    Long valueMileage = getLatestMileage(logDate,imeiNumberParam);

                    //set new value
                    value.setStart(localTimeStart);
                    value.setEnd(localTimeEnd);
                    value.setLogTanggal(newLocalDate);
                    value.setMileage(valueMileage);


                    test.setId(x);
                    test.setLogTanggal(value.getLogTanggal());
                    test.setStart(value.getStart());
                    test.setEnd(value.getEnd());
                    test.setImeiNumber(value.getImeiNumber());
                    test.setPlate(value.getPlate());
                    test.setMileage(value.getMileage());

                    listTemp.add(test);



                }
            }
            return listTemp;

        }catch (Exception e){
            throw e;
        }
    }

    @GetMapping(path="/getdetail")
    @ApiOperation("Get Detail Event Log")
    public List<LogAutomaticEventReportDetailDTO> getDetailEventLog(@RequestHeader("Authorization") String Authorization,
            @ApiParam(name = "startDate", value = "Format Date (Ex. 2019-05-01)", defaultValue = "2019-05-01")
            @RequestParam(name="startDate",required = false) String startDate,
            @ApiParam(name = "startTime", value = "Format Time (Ex. 00:00)", defaultValue = "00:00")
            @RequestParam(name="startTime",required = false) String startTime,
            @ApiParam(name = "endDate", value = "Format Date (Ex. 2019-05-31)", defaultValue = "2019-05-31")
            @RequestParam(name="endDate",required = false) String endDate,
            @ApiParam(name = "endTime", value = "Format Time (Ex. 23:59)", defaultValue = "23:59")
            @RequestParam(name="endTime",required = false) String endTime,
            @RequestParam (name="imeiNumber",required = false) String imeiNumber,
            @RequestParam (name="plate",required = false) String plate,
            @RequestParam (name="sort",required = false) String sort){

        LocalDate todayDate = LocalDate.now();
        String from = todayDate+" 00:00:00";
        String to = todayDate+" 23:59:00";

        if(startDate != null && startTime != null){
            from = startDate+" "+startTime+":00";
            from = otherService.convertLocalDateTimeToUTC(from);

        }
        if(endDate != null && endTime != null){
            to = endDate+" "+endTime+":00";
            to = otherService.convertLocalDateTimeToUTC(to);
        }
        if(sort == null){
            sort = "timestamp,desc";
        }

        imeiNumber = stringToSplit(imeiNumber);
        plate = stringToSplit(plate);

        try {
            List<LogAutomaticEventReportDetailDTO> response = logAutomaticEventReportDAO.getDetailLogEventReport(from,to,imeiNumber,plate,sort);
            for(LogAutomaticEventReportDetailDTO value:response){
                LocalDateTime timestampToLocaltime = otherService.convertUTCtoLocalTime(value.getTimestamp());
                value.setTimestamp(timestampToLocaltime);
            }
        return response;
        }catch (Exception e){
            throw e;
        }

    }

    @GetMapping(path="/getdetailpublic")
    @ApiOperation("Get Detail Event Log Public")
    public List<LogAutomaticEventReportDetailDTO> getDetailEventLogPublic(
        @ApiParam(name = "startDate", value = "Format Date (Ex. 2019-05-01)", defaultValue = "2019-05-01")
        @RequestParam(name="startDate",required = false) String startDate,
        @ApiParam(name = "startTime", value = "Format Time (Ex. 00:00)", defaultValue = "00:00")
        @RequestParam(name="startTime",required = false) String startTime,
        @ApiParam(name = "endDate", value = "Format Date (Ex. 2019-05-31)", defaultValue = "2019-05-31")
        @RequestParam(name="endDate",required = false) String endDate,
        @ApiParam(name = "endTime", value = "Format Time (Ex. 23:59)", defaultValue = "23:59")
        @RequestParam(name="endTime",required = false) String endTime,
        @RequestParam (name="imeiNumber",required = false) String imeiNumber,
        @RequestParam (name="plate",required = false) String plate,
        @RequestParam (name="sort",required = false) String sort){

        LocalDate todayDate = LocalDate.now();
        String from = todayDate+" 00:00:00";
        String to = todayDate+" 23:59:00";

        if(startDate != null && startTime != null){
            from = startDate+" "+startTime+":00";
            from = otherService.convertLocalDateTimeToUTC(from);

        }
        if(endDate != null && endTime != null){
            to = endDate+" "+endTime+":00";
            to = otherService.convertLocalDateTimeToUTC(to);
        }
        if(sort == null){
            sort = "timestamp,desc";
        }

        imeiNumber = stringToSplit(imeiNumber);
        plate = stringToSplit(plate);

        try {
            List<LogAutomaticEventReportDetailDTO> response = logAutomaticEventReportDAO.getDetailLogEventReport(from,to,imeiNumber,plate,sort);
            for(LogAutomaticEventReportDetailDTO value:response){
                LocalDateTime timestampToLocaltime = otherService.convertUTCtoLocalTime(value.getTimestamp());
                value.setTimestamp(timestampToLocaltime);
            }
            return response;
        }catch (Exception e){
            throw e;
        }

    }

    @GetMapping(path="/getdetailpublic/download")
    @ApiOperation("Get Detail Event Log Public Download")
    public List<LogAutomaticEventReportDetailDTO> getDetailEventLogPublicDownload(
            @ApiParam(name = "startDate", value = "Format Date (Ex. 2019-05-01)", defaultValue = "2019-05-01")
            @RequestParam(name="startDate",required = false) String startDate,
            @ApiParam(name = "startTime", value = "Format Time (Ex. 00:00)", defaultValue = "00:00")
            @RequestParam(name="startTime",required = false) String startTime,
            @ApiParam(name = "endDate", value = "Format Date (Ex. 2019-05-31)", defaultValue = "2019-05-31")
            @RequestParam(name="endDate",required = false) String endDate,
            @ApiParam(name = "endTime", value = "Format Time (Ex. 23:59)", defaultValue = "23:59")
            @RequestParam(name="endTime",required = false) String endTime,
            @RequestParam (name="imeiNumber",required = false) String imeiNumber,
            @RequestParam (name="plate",required = false) String plate,
            @RequestParam (name="sort",required = false) String sort,
            HttpServletResponse httpResponse) throws IOException {

        LocalDate todayDate = LocalDate.now();
        String from = todayDate+" 00:00:00";
        String to = todayDate+" 23:59:00";

        if(startDate != null && startTime != null){
            from = startDate+" "+startTime+":00";
            from = otherService.convertLocalDateTimeToUTC(from);

        }
        if(endDate != null && endTime != null){
            to = endDate+" "+endTime+":00";
            to = otherService.convertLocalDateTimeToUTC(to);
        }
        if(sort == null){
            sort = "timestamp,desc";
        }

        imeiNumber = stringToSplit(imeiNumber);
        plate = stringToSplit(plate);

        try {
            List<LogAutomaticEventReportDetailDTO> response = logAutomaticEventReportDAO.getDetailLogEventReport(from,to,imeiNumber,plate,sort);
            for(LogAutomaticEventReportDetailDTO value:response){
                LocalDateTime timestampToLocaltime = otherService.convertUTCtoLocalTime(value.getTimestamp());
                value.setTimestamp(timestampToLocaltime);
            }
            exportToExcell(httpResponse, response);
            return response;
        }catch (Exception e){
            throw e;
        }

    }

    @PostMapping("/updateaddress")
    public ApiResponse updateAddress(
            @ApiParam(name = "startDate", value = "Format Date (Ex. 2019-05-01)", defaultValue = "2019-05-01")
            @RequestParam(name="startDate",required = false) String startDate,
            @ApiParam(name = "endDate", value = "Format Date (Ex. 2019-05-31)", defaultValue = "2019-05-31")
            @RequestParam(name="endDate",required = false) String endDate
    ){
        LocalDate todayDate = LocalDate.now();
        String from = "";
        String to = "";
        if(startDate != null){
            from = startDate+" 00:00:00";
        }else{
            from  = todayDate+" 00:00:00";
        }
        if(endDate != null){
            to = endDate+" 23:59:00";
        }else{
            to = todayDate+" 23:59:00";
        }

        Integer rows = 0;
        try {
            List<LogAutomaticEventReportTemporaryDTO> response = logAutomaticEventReportDAO.getTempLogEventReport(from,to);
            for(LogAutomaticEventReportTemporaryDTO value:response){
                    String longitude = value.getLongitude().toString();
                    String latitude = value.getLatitude().toString();
                    JsonNode dataImporter = geoLocationService.getDataByUrl(longitude,latitude);
                    String locationName = dataImporter.get("display_name").asText();

                     rows = logAutomaticEventReportDAO.updateRecord(value.getId(),locationName);
//                    System.out.println(rows + " row(s) updated.");

            }
            LocalDateTime newtime = LocalDateTime.now();

            return new ApiResponse(200, "Update data is succesfully");
        }catch (Exception e){
            throw e;
        }
    }

    @PostMapping("/updateaddress_onoff")
    public ApiResponse updateAddress_v2(
        @ApiParam(name = "startDate", value = "Format Date (Ex. 2019-05-01)", defaultValue = "2019-05-01")
        @RequestParam(name="startDate",required = false) String startDate,
        @ApiParam(name = "endDate", value = "Format Date (Ex. 2019-05-31)", defaultValue = "2019-05-31")
        @RequestParam(name="endDate",required = false) String endDate
    ){
        /*LocalDate todayDate = LocalDate.now().minusDays(1);
        String from  = todayDate+" 00:00:00", to = todayDate+" 23:59:00";
        if(startDate != null){
            from = startDate+" 00:00:00";
        }

        if(endDate != null){
            to = endDate+" 23:59:00";
        }*/

        try {
//            List<LogAutomaticEventReportTemporaryDTO> response = logAutomaticEventReportDAO.getTempLogEventReportCodeOnOff(from,to);
            List<LogAutomaticEventReportTemporaryDTO> response = logAutomaticEventReportDAO.getTempLogEventReportCodeOnOffPeriod();
            for(LogAutomaticEventReportTemporaryDTO value:response){
                JsonNode dataImporter = geoLocationService.getDataByUrl(value.getLongitude().toString(),value.getLatitude().toString());
                try {
                    String locationName = dataImporter.get("display_name").asText();
                    if(locationName==null){
                        locationName = "Tidak diketahui";
                    }
                    logAutomaticEventReportDAO.updateRecord(value.getId(),locationName);
                }catch (Exception e){
                    throw e;
                }
            }
            return new ApiResponse(200, "Update data is succesfully");
        }catch (Exception e){
            throw e;
        }
    }

    public Long getLatestMileage(String logDate, Long imeiNumberParam){
        Long valueMileage = logAutomaticEventReportDAO.getLatestMileage(logDate,imeiNumberParam);
        return valueMileage;
    }

    @RequestMapping(value="/exportexcell", method = RequestMethod.GET)
    public void downloadLogDetail(
            @ApiParam(name = "startDate", value = "Format Date (Ex. 2019-06-01)", defaultValue = "2019-06-01")
            @RequestParam(name="startDate",required = false) String startDate,
            @ApiParam(name = "startTime", value = "Format Time (Ex. 00:00)", defaultValue = "00:00")
            @RequestParam(name="startTime",required = false) String startTime,
            @ApiParam(name = "endDate", value = "Format Date (Ex. 2019-06-30)", defaultValue = "2019-06-30")
            @RequestParam(name="endDate",required = false) String endDate,
            @ApiParam(name = "endTime", value = "Format Time (Ex. 23:59)", defaultValue = "23:59")
            @RequestParam(name="endTime",required = false) String endTime,
            @RequestParam (name="imeiNumber",required = false) String imeiNumber,
            @RequestParam (name="plate",required = false) String plate,
            HttpServletResponse response) throws IOException {




        LocalDate todayDate = LocalDate.now();
        Integer lastMonth = todayDate.lengthOfMonth();
        LocalDate dateFirst = todayDate.withDayOfMonth(1);
        LocalDate dateLast = todayDate.withDayOfMonth(lastMonth);
        String dateFirstToString = dateFirst.toString();
        String dateLastToString = dateLast.toString();
        String sort = "timestamp,desc";
        String from = todayDate+" 00:00:00";
        String to = todayDate+" 23:59:00";
        if(startDate != null && startTime != null){
            from = startDate+" "+startTime+":00";
            from = otherService.convertLocalDateTimeToUTC(from);
        }
        if(endDate != null && endTime != null){
            to = endDate+" "+endTime+":00";
            to = otherService.convertLocalDateTimeToUTC(to);
        }

        imeiNumber = stringToSplit(imeiNumber);
        plate = stringToSplit(plate);

        List<LogAutomaticEventReportDetailDTO> dataReport = logAutomaticEventReportDAO.getDetailLogEventReport(from,to,imeiNumber,plate,sort);

        exportToExcell(response, dataReport);
    }

    public void exportToExcell(HttpServletResponse response, List<LogAutomaticEventReportDetailDTO> dataReport) throws IOException {
        Workbook workbook  = new XSSFWorkbook();
        CreationHelper createHelper = workbook.getCreationHelper();
        String fileName = "Log Automatic Event Report.xlsx";
        Sheet sheet = workbook.createSheet("Log Automatic Event Report");
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,1));
        //Style coloumn
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);

        Font titleFont = workbook.createFont();
        titleFont.setColor(IndexedColors.RED.getIndex());
        titleFont.setBold(true);
        titleFont.setFontHeight((short)(8*30));

        CellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setWrapText(true);
        titleStyle.setFont(titleFont);

        CellStyle cellHeader = workbook.createCellStyle();
        cellHeader.setFont(headerFont);
        cellHeader.setAlignment(HorizontalAlignment.CENTER);
        cellHeader.setVerticalAlignment(VerticalAlignment.CENTER);
        cellHeader.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        cellHeader.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cellHeader.setBorderBottom(BorderStyle.MEDIUM);
        cellHeader.setBorderTop(BorderStyle.MEDIUM);
        cellHeader.setBorderLeft(BorderStyle.MEDIUM);
        cellHeader.setBorderRight(BorderStyle.MEDIUM);
        cellHeader.setWrapText(true);

        Font listFont = workbook.createFont();
        listFont.setColor(IndexedColors.BLACK.getIndex());
        CellStyle listStyle = workbook.createCellStyle();
        listStyle.setFont(listFont);
        listStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        listStyle.setBorderBottom(BorderStyle.THIN);
        listStyle.setBorderTop(BorderStyle.THIN);
        listStyle.setBorderLeft(BorderStyle.THIN);
        listStyle.setBorderRight(BorderStyle.THIN);

        CellStyle list2Style = workbook.createCellStyle();
        list2Style.setFont(listFont);
        list2Style.setAlignment(HorizontalAlignment.CENTER);
        list2Style.setVerticalAlignment(VerticalAlignment.CENTER);
        list2Style.setBorderBottom(BorderStyle.THIN);
        list2Style.setBorderTop(BorderStyle.THIN);
        list2Style.setBorderLeft(BorderStyle.THIN);
        list2Style.setBorderRight(BorderStyle.THIN);

        int row = 0;
        int col = 0;

        //row for title
        Row titleRow = sheet.createRow(row);
        titleRow.createCell(col);
        titleRow.getCell(col).setCellValue("Event Log Report");
        titleRow.getCell(col).setCellStyle(titleStyle);


        row += 1;
        Row headerRow = sheet.createRow(row);
        String[] coloumns = {"No", "Imei Number", "Event Code", "Latitude", "Longitude", "Timestamp", "Positioning Status", "Number of Satellites", "Gsm Signal Number", "Speed", "Direction", "HDOP","Altitude","Mileage","Runtime","Base Station Info","IO Port Status","Analog Input Value","Raw","Fuel Percentage","Geom","Plate","Ignition","Status","Address Location"};
        for (col = 0; col < coloumns.length; col++) {
            Cell cell = headerRow.createCell(col);
            cell.setCellValue(coloumns[col]);
//
            headerRow.getCell(col).setCellStyle(cellHeader);

        }

        int rowIdx = row+1;
        int number = 1;

        if(dataReport.size() > 0){
            for(LogAutomaticEventReportDetailDTO acp : dataReport){

                Row rowCell = sheet.createRow(rowIdx++);
                sheet.setColumnWidth(0, 2000);
                sheet.setColumnWidth(1, 4000);
                sheet.setColumnWidth(2, 4000);
                sheet.setColumnWidth(3, 7000);
                sheet.setColumnWidth(4, 4000);
                sheet.setColumnWidth(5, 4000);
                sheet.setColumnWidth(6, 4000);
                sheet.setColumnWidth(7, 4000);
                sheet.setColumnWidth(8, 4000);
                sheet.setColumnWidth(9, 4000);
                sheet.setColumnWidth(10, 4000);
                sheet.setColumnWidth(11, 4000);
                sheet.setColumnWidth(12, 4000);
                sheet.setColumnWidth(13, 4000);
                sheet.setColumnWidth(14, 4000);
                sheet.setColumnWidth(15, 4000);
                sheet.setColumnWidth(16, 4000);
                sheet.setColumnWidth(17, 4000);
                sheet.setColumnWidth(18, 7000);
                sheet.setColumnWidth(19, 4000);
                sheet.setColumnWidth(20, 4000);
                sheet.setColumnWidth(21, 4000);
                sheet.setColumnWidth(22, 4000);
                sheet.setColumnWidth(23, 4000);
                sheet.setColumnWidth(24, 4000);

                rowCell.createCell(0).setCellValue(number++);
                rowCell.createCell(1).setCellValue(acp.getImeiNumber());
                rowCell.createCell(2).setCellValue(acp.getEventCode());
                rowCell.createCell(3).setCellValue(acp.getLatitude());
                rowCell.createCell(4).setCellValue(acp.getLongitude());
                rowCell.createCell(5).setCellValue(acp.getTimestamp().toString());
                rowCell.createCell(6).setCellValue(acp.getPositioningStatus());
                rowCell.createCell(7).setCellValue(acp.getNumberOfSatellites());
                rowCell.createCell(8).setCellValue(acp.getGsmSignalNumber());
                rowCell.createCell(9).setCellValue(acp.getSpeed());
                rowCell.createCell(10).setCellValue(acp.getDirection());
                rowCell.createCell(11).setCellValue(acp.getHdop());
                rowCell.createCell(12).setCellValue(acp.getAltitude());
                rowCell.createCell(13).setCellValue(acp.getMileage());
                rowCell.createCell(14).setCellValue(acp.getRuntime());
                rowCell.createCell(15).setCellValue(acp.getBaseStationInfo());
                rowCell.createCell(16).setCellValue(acp.getIoPortStatus());
                rowCell.createCell(17).setCellValue(acp.getAnalogInputValue());
                rowCell.createCell(18).setCellValue(acp.getRaw());
                rowCell.createCell(19).setCellValue(acp.getFuelPercentage());
                rowCell.createCell(20).setCellValue(acp.getGeom());
                rowCell.createCell(21).setCellValue(acp.getPlate());
                if(acp.getIgnition() == "1"){
                    rowCell.createCell(22).setCellValue("ON");
                }else{
                    rowCell.createCell(22).setCellValue("OFF");
                }

                rowCell.createCell(23).setCellValue(acp.getStatus());
                rowCell.createCell(24).setCellValue(acp.getAddressLocation());

                rowCell.getCell(0).setCellStyle(list2Style);
                rowCell.getCell(1).setCellStyle(list2Style);
                rowCell.getCell(2).setCellStyle(listStyle);
                rowCell.getCell(3).setCellStyle(list2Style);
                rowCell.getCell(4).setCellStyle(list2Style);
                rowCell.getCell(5).setCellStyle(list2Style);
                rowCell.getCell(6).setCellStyle(listStyle);
                rowCell.getCell(7).setCellStyle(list2Style);
                rowCell.getCell(8).setCellStyle(list2Style);
                rowCell.getCell(9).setCellStyle(list2Style);
                rowCell.getCell(10).setCellStyle(list2Style);
                rowCell.getCell(11).setCellStyle(list2Style);
                rowCell.getCell(12).setCellStyle(list2Style);
                rowCell.getCell(13).setCellStyle(list2Style);
                rowCell.getCell(14).setCellStyle(list2Style);
                rowCell.getCell(15).setCellStyle(list2Style);
                rowCell.getCell(16).setCellStyle(list2Style);
                rowCell.getCell(17).setCellStyle(listStyle);
                rowCell.getCell(18).setCellStyle(list2Style);
                rowCell.getCell(19).setCellStyle(list2Style);
                rowCell.getCell(20).setCellStyle(list2Style);
                rowCell.getCell(21).setCellStyle(listStyle);
                rowCell.getCell(22).setCellStyle(list2Style);
                rowCell.getCell(23).setCellStyle(list2Style);
                rowCell.getCell(24).setCellStyle(list2Style);

            }
        }

        otherService.exportToExcel(response, workbook, fileName);
    }



    public String stringToSplit(String string){
        if(string!=null){

            String[] tempArray;

            /* delimiter */
            String delimiter = ",";

            /* given string will be split by the argument delimiter provided. */
            tempArray = string.split(delimiter);
            String tempString = "";

            /* print substrings */
            for (int i = 0; i < tempArray.length; i++) {

                if(i==(tempArray.length-1)){
                    tempString+="'"+tempArray[i]+"'";
                }else{
                    tempString+="'"+tempArray[i]+"',";
                }
            };
  

            return tempString;
        }else{
            return null;
        }
    }

}
