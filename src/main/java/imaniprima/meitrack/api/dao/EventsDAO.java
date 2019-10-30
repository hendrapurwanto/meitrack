package imaniprima.meitrack.api.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
@Slf4j
public class EventsDAO {
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Long getTotalEventLog(Long eventCode){
        String sql = "";
        String where = "";

        LocalDate todayDate = LocalDate.now();
        /*Integer lastMonth = todayDate.lengthOfMonth();
        LocalDate from = todayDate.withDayOfMonth(1);
        LocalDate to = todayDate.withDayOfMonth(lastMonth);
        System.err.println(todayDate.withDayOfMonth(1));
        System.err.println(lastMonth);*/
        if(eventCode != null){
            where = " where event_code="+eventCode+" and started ='"+todayDate+"'";
        }
        if(eventCode != null){
            sql = "select count(event_code) from events_log"+where;
        }
        System.err.println(sql);
        try {
            Long result = jdbcTemplate.queryForObject(sql, Long.class);
            return  result;
        }catch (EmptyResultDataAccessException ex){
            return null;
        }
    }
}
