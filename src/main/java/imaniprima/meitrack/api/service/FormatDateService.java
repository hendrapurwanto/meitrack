package imaniprima.meitrack.api.service;

import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Service
public class FormatDateService {
    public String withMonthName(String actualDate)  {
        SimpleDateFormat dateWithMonthName = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            date = sdf.parse(actualDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateWithMonthName.format(date);
    }

    public String withMonthNameIn(String actualDate)  {
        String[] monthName = { "","Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember" };

        SimpleDateFormat dateWithMonthName = new SimpleDateFormat("dd MM yyyy", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            date = sdf.parse(actualDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String datef = dateWithMonthName.format(date);
        String[] parts = datef.split(" ");
        String month = monthName[Integer.parseInt(parts[1])];

        return parts[0]+" "+month+" "+parts[2];
    }

    public String withMonthNameDMY3In(String actualDate)  {
        String[] monthName = { "","Jan", "Feb", "Mar", "Apr", "Mei", "Jun", "Jul", "Agu", "Sep", "Okt", "Nov", "Des" };

        SimpleDateFormat dateWithMonthName = new SimpleDateFormat("dd MM yy", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");

        Date date = null;
        try {
            date = sdf.parse(actualDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String datef = dateWithMonthName.format(date);
        String[] parts = datef.split(" ");
        String month = monthName[Integer.parseInt(parts[1])];

        return parts[0]+""+month+""+parts[2];
    }

    public String monthNameChar3In(String actualDate)  {
        String[] monthName = { "","Jan", "Feb", "Mar", "Apr", "Mei", "Jun", "Jul", "Agu", "Sep", "Okt", "Nov", "Des" };

        SimpleDateFormat dateWithMonthName = new SimpleDateFormat("dd MM yyyy", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            date = sdf.parse(actualDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String datef = dateWithMonthName.format(date);
        String[] parts = datef.split(" ");
        String month = monthName[Integer.parseInt(parts[1])];

        return month;
    }

    public String withMonthNumber(String actualDate)  {
        SimpleDateFormat dateWithMonthName = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            date = sdf.parse(actualDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateWithMonthName.format(date);
    }

    public String withMonthAndYearNumber(String actualDate)  {
        SimpleDateFormat dateWithMonthName = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            date = sdf.parse(actualDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String datef = dateWithMonthName.format(date);
        String[] parts = datef.split("-");

        return parts[1]+"-"+parts[2];
    }
}
