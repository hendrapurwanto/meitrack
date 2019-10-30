package imaniprima.meitrack.api.service;


import com.querydsl.core.BooleanBuilder;
import imaniprima.meitrack.api.dto.LogAutomaticEventReportDetailDTO;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class OtherService {

    @Autowired
    StringReplaceService strService;

    private final Path reportExcelDir = Paths.get("report-excel/");
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private DateTimeFormatter newFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private DateTimeFormatter newFormatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public String encode(String value) throws UnsupportedEncodingException {
        // Getting MIME encoder
        Base64.Encoder encoder = Base64.getMimeEncoder();
        String message = "\n"+value;
        String eStr = encoder.encodeToString(message.getBytes());

        /*String asB64 = Base64.getEncoder().encodeToString(value.getBytes("utf-8"));
        System.out.println("Encode : "+asB64);
        return asB64;*/

        /*Base64.Decoder decoder = Base64.getMimeDecoder();
        // Decoding MIME encoded message
        String dStr = new String(decoder.decode(eStr));
        System.out.println("Decoded message: "+dStr);*/

        return eStr;
    }

    public String decode(String value) throws UnsupportedEncodingException {
        Base64.Decoder decoder = Base64.getMimeDecoder();
        // Decoding MIME encoded message
        String dStr = new String(decoder.decode(value));
        String text = dStr.replace("\n", "").replace("\r", "");
        System.out.println("Decoded message: "+text);
        return text;
    }

    public Double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public Integer sumDayByRangeDate(LocalDate from, LocalDate to){
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        /*String from = "2019-02-01";
        String to = String.valueOf(LocalDate.now());*/

        try {
            Date dateBefore = myFormat.parse(String.valueOf(from));
            Date dateAfter = myFormat.parse(String.valueOf(to));
            long difference = dateAfter.getTime() - dateBefore.getTime();
            float daysBetween = (difference / (1000*60*60*24));
            /* You can also convert the milliseconds to days using this method
             * float daysBetween =
             *         TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS)
             */
            System.out.println("Number of Days between dates: "+daysBetween);
            return Integer.valueOf((int) daysBetween);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void exportToExcel (HttpServletResponse response, Workbook workbook, String fileName) throws IOException {
        //write the output to file
        FileOutputStream fileOut = new FileOutputStream(fileName);
        workbook.write(fileOut);
        fileOut.close();

        //closing workbook
        workbook.close();

        //Download File
//        File file = new File(reportExcelDir + "\\" + fileName);
        File file = new File(fileName);
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));
        response.setContentLength((int) file.length());
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }

    public String convertLocalDateTimeToUTC(String localdateTIme){
        ZoneId zoneAsia = ZoneId.of( "Asia/Jakarta" );
        ZoneId zoneUTC = ZoneId.of( "UTC" );
//        if(localdateTIme.length())
        LocalDateTime originDate = LocalDateTime.parse(localdateTIme, formatter);
        ZonedDateTime timelocal = ZonedDateTime.of(originDate,zoneAsia);
        ZonedDateTime utcTime = timelocal.withZoneSameInstant(zoneUTC);
        String utcTimetoString = utcTime.toString();
        utcTimetoString = strService.strReplace(utcTimetoString,"T"," ");
        utcTimetoString = strService.strReplace(utcTimetoString,"Z[U C]",":00");
        return utcTimetoString;

    }

    public LocalDateTime convertUTCtoLocalTime(LocalDateTime localdateTIme){
        ZoneId zoneAsia = ZoneId.of( "Asia/Jakarta" );
        ZoneId zoneUTC = ZoneId.of( "UTC" );
        ZonedDateTime timeUTC = ZonedDateTime.of(localdateTIme,zoneUTC);
        ZonedDateTime timeLocal = timeUTC.withZoneSameInstant(zoneAsia);
        LocalDateTime localDateTime = timeLocal.toLocalDateTime();

        return localDateTime;

    }
    public LocalDateTime convertStringtoLocalDateTime(String tanggal,String time){
        String localdatetime = tanggal+" "+time;
        if(localdatetime.length() == 16){
            localdatetime = localdatetime+":00";
        }
        LocalDateTime newDateTime = LocalDateTime.parse(localdatetime,formatter);
        newDateTime = convertUTCtoLocalTime(newDateTime);
        return newDateTime;
    }

    public LocalTime convertDatetimetoLocaltime(LocalDateTime localDateTime){
        String localDateTimeString  = localDateTime.toString();
        String[] newLocalDateTimeString = localDateTimeString.split("T");
        if(newLocalDateTimeString[1].length() == 5){
            newLocalDateTimeString[1] = newLocalDateTimeString[1]+":00";
        }
        LocalTime newLocaltime = LocalTime.parse(newLocalDateTimeString[1],newFormatter);
        return newLocaltime;

    }
    public LocalDate convertDatetoLocalDate(LocalDateTime localDateTime){
        String localDateTimeString  = localDateTime.toString();
        String[] newLocalDateTimeString = localDateTimeString.split("T");

        LocalDate newLocaldate = LocalDate.parse(newLocalDateTimeString[0],newFormatterDate);
        return newLocaldate;

    }


}
