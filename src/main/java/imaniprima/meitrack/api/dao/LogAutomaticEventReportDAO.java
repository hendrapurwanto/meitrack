package imaniprima.meitrack.api.dao;

import imaniprima.meitrack.api.dto.LogAutomaticEventReportDTO;
import imaniprima.meitrack.api.dto.LogAutomaticEventReportDetailDTO;
import imaniprima.meitrack.api.dto.LogAutomaticEventReportTemporaryDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class LogAutomaticEventReportDAO {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    JdbcTemplate jdbcTemplate;

    private static final String updateSql = "UPDATE log_automatic_event_report  SET address_location = ? WHERE id = ?";


    public List<LogAutomaticEventReportDTO> getLogEventReport(String startDate,String endDate, String imeiNumber, String plate,String sort){
        String where = "";



        /*String whereImei = "";
        String wherePlate = "";*/

        if(startDate != null && endDate != null){

            where = " where a.timestamp >='"+startDate+"' and a.timestamp <='"+endDate+"'";
        }
        if(imeiNumber != null){
            where += " and a.imei_number in("+imeiNumber+")";

        }
        if(plate != null){
            where += " and v.plate in("+plate+")";
        }

        String sql = "\n" +
                " SELECT a.timestamp::DATE, MIN(a.timestamp) as min_timetamp, MAX(a.timestamp) as max_timestamp,a.imei_number,  v.plate\n" +
                "    FROM log_automatic_event_report a left join vehicles v on a.imei_number = v.imei"+where+"\n"+
                "    GROUP BY a.timestamp::DATE,a.imei_number,v.plate order by a.timestamp::DATE desc";

//        System.err.println(sql);
        try {

            List<LogAutomaticEventReportDTO> logAutomaticEventReportDTOS = namedParameterJdbcTemplate.query(sql, new RowMapper<LogAutomaticEventReportDTO>() {
                @Override
                public LogAutomaticEventReportDTO mapRow(ResultSet resultSet, int i) throws SQLException {
                    LogAutomaticEventReportDTO logAutomaticEventReportDTO = new LogAutomaticEventReportDTO();

                    logAutomaticEventReportDTO.setLogTanggal(resultSet.getDate("timestamp").toLocalDate());
                    logAutomaticEventReportDTO.setStart(resultSet.getTime("min_timetamp").toLocalTime());
                    logAutomaticEventReportDTO.setEnd(resultSet.getTime("max_timestamp").toLocalTime());
                    logAutomaticEventReportDTO.setImeiNumber(resultSet.getLong("imei_number"));
                    logAutomaticEventReportDTO.setPlate(resultSet.getString("plate"));


                    return logAutomaticEventReportDTO;
                }
            });
            return logAutomaticEventReportDTOS;
        }catch (EmptyResultDataAccessException ex){
            return new ArrayList<LogAutomaticEventReportDTO>();
        }
    }

    public List<LogAutomaticEventReportDetailDTO> getDetailLogEventReport(String from, String to, String imeiNumber, String plate,String sort){
        String where = "";
        String whereImei = "";
        String wherePlate = "";
        String orderBy = "";
        String[] sorts = sort.split(",");
        String orderIndex = sorts[0];
        String sortIndex = sorts[1];
        String orderString;


        if(orderIndex.equals("id")){
            orderString = "a.id "+sortIndex;
        }else if(orderIndex.equals("timestamp")){
            orderString = "a.timestamp "+sortIndex;
        }else{
            orderString = "a.id desc";
        }

        orderBy = " order by "+orderString;

        if(from != null && to != null){

            where = " where  a.timestamp>='"+from+"' and a.timestamp<='"+to+"'";

        }

        if(imeiNumber != null){
            whereImei = " and a.imei_number in("+imeiNumber+")";

        }
        if(plate != null){
            wherePlate = " and v.plate="+plate;
        }

        String sql = "SELECT a.*,v.plate from log_automatic_event_report a left join vehicles v on a.imei_number = v.imei" +where+whereImei+wherePlate+orderBy;

//        System.err.println(sql);
        try {
            List<LogAutomaticEventReportDetailDTO> logAutomaticEventReportDetailDTOS = namedParameterJdbcTemplate.query(sql, new RowMapper<LogAutomaticEventReportDetailDTO>() {
                @Override
                public LogAutomaticEventReportDetailDTO mapRow(ResultSet resultSet, int i) throws SQLException {
                    LogAutomaticEventReportDetailDTO logAutomaticEventReportDetailDTO = new LogAutomaticEventReportDetailDTO();
                    logAutomaticEventReportDetailDTO.setTimestamp(resultSet.getTimestamp("timestamp").toLocalDateTime());
                    logAutomaticEventReportDetailDTO.setId(resultSet.getLong("id"));
                    logAutomaticEventReportDetailDTO.setImeiNumber(resultSet.getLong("imei_number"));
                    logAutomaticEventReportDetailDTO.setPlate(resultSet.getString("plate"));
                    logAutomaticEventReportDetailDTO.setEventCode(resultSet.getInt("event_code"));
                    logAutomaticEventReportDetailDTO.setLatitude(resultSet.getDouble("latitude"));
                    logAutomaticEventReportDetailDTO.setLongitude(resultSet.getDouble("longitude"));
                    logAutomaticEventReportDetailDTO.setPositioningStatus(resultSet.getString("positioning_status"));
                    logAutomaticEventReportDetailDTO.setNumberOfSatellites(resultSet.getInt("number_of_satellites"));
                    logAutomaticEventReportDetailDTO.setGsmSignalNumber(resultSet.getInt("gsm_signal_number"));
                    logAutomaticEventReportDetailDTO.setSpeed(resultSet.getInt("speed"));
                    logAutomaticEventReportDetailDTO.setDirection(resultSet.getInt("direction"));
                    logAutomaticEventReportDetailDTO.setHdop(resultSet.getDouble("hdop"));
                    logAutomaticEventReportDetailDTO.setAltitude(resultSet.getInt("altitude"));
                    logAutomaticEventReportDetailDTO.setMileage(resultSet.getLong("mileage"));
                    logAutomaticEventReportDetailDTO.setRuntime(resultSet.getLong("runtime"));
                    logAutomaticEventReportDetailDTO.setBaseStationInfo(resultSet.getString("base_station_info"));
                    logAutomaticEventReportDetailDTO.setIoPortStatus(resultSet.getString("io_port_status"));
                    logAutomaticEventReportDetailDTO.setAnalogInputValue(resultSet.getString("analog_input_value"));
                    logAutomaticEventReportDetailDTO.setRaw(resultSet.getLong("raw"));
                    logAutomaticEventReportDetailDTO.setFuelPercentage(resultSet.getString("fuel_percentage"));
                    logAutomaticEventReportDetailDTO.setGeom(resultSet.getString("geom"));
                    logAutomaticEventReportDetailDTO.setIgnition(resultSet.getString("ignition"));
                    logAutomaticEventReportDetailDTO.setStatus(resultSet.getInt("status"));
                    logAutomaticEventReportDetailDTO.setAddressLocation(resultSet.getString("address_location"));




                    return logAutomaticEventReportDetailDTO;
                }
            });
            return logAutomaticEventReportDetailDTOS;
        }catch (EmptyResultDataAccessException ex){
            return new ArrayList<LogAutomaticEventReportDetailDTO>();
        }
    }

    public List<LogAutomaticEventReportTemporaryDTO> getTempLogEventReport(String from, String to){
        String where = "";
        LocalDate todayDate = LocalDate.now();
        String todayString = todayDate+" 00:00:00";
        if(from != null && to != null){
            where = " where  a.timestamp >='"+from+"' and a.timestamp<='"+to+"' and a.address_location isnull";
        }

        String sql = "select a.id, a.longitude, a.latitude from log_automatic_event_report a"+ where;

        try {
            List<LogAutomaticEventReportTemporaryDTO> logAutomaticEventReportTemporaryDTOS = namedParameterJdbcTemplate.query(sql, new RowMapper<LogAutomaticEventReportTemporaryDTO>() {
                @Override
                public LogAutomaticEventReportTemporaryDTO mapRow(ResultSet resultSet, int i) throws SQLException {
                    LogAutomaticEventReportTemporaryDTO logAutomaticEventReportDetailDTO = new LogAutomaticEventReportTemporaryDTO();
                    logAutomaticEventReportDetailDTO.setId(resultSet.getLong("id"));
                    logAutomaticEventReportDetailDTO.setLatitude(resultSet.getDouble("latitude"));
                    logAutomaticEventReportDetailDTO.setLongitude(resultSet.getDouble("longitude"));
                    return logAutomaticEventReportDetailDTO;
                }
            });
            return logAutomaticEventReportTemporaryDTOS;
        }catch (EmptyResultDataAccessException ex){
            return new ArrayList<LogAutomaticEventReportTemporaryDTO>();
        }
    }

    public List<LogAutomaticEventReportTemporaryDTO> getTempLogEventReportCodeOnOff(String from, String to){

        String sql = "select a.id, a.longitude, a.latitude from log_automatic_event_report a " +
                "where a.timestamp >='"+from+"' and a.timestamp<='"+to+"' and " +
                "a.address_location is null and a.event_code in (11,3)";

        System.err.println(sql);

        try {
            List<LogAutomaticEventReportTemporaryDTO> logAutomaticEventReportTemporaryDTOS = namedParameterJdbcTemplate.query(sql, new RowMapper<LogAutomaticEventReportTemporaryDTO>() {
                @Override
                public LogAutomaticEventReportTemporaryDTO mapRow(ResultSet resultSet, int i) throws SQLException {
                    LogAutomaticEventReportTemporaryDTO logAutomaticEventReportDetailDTO = new LogAutomaticEventReportTemporaryDTO();
                    logAutomaticEventReportDetailDTO.setId(resultSet.getLong("id"));
                    logAutomaticEventReportDetailDTO.setLatitude(resultSet.getDouble("latitude"));
                    logAutomaticEventReportDetailDTO.setLongitude(resultSet.getDouble("longitude"));
                    return logAutomaticEventReportDetailDTO;
                }
            });
            return logAutomaticEventReportTemporaryDTOS;
        }catch (EmptyResultDataAccessException ex){
            return new ArrayList<LogAutomaticEventReportTemporaryDTO>();
        }
    }

    public List<LogAutomaticEventReportTemporaryDTO> getTempLogEventReportCodeOnOffPeriod(){

        String sql = "select a.id, a.longitude, a.latitude from log_automatic_event_report a " +
                "where a.timestamp >='2019-06-01 00:00:00' and a.timestamp<='2019-12-31 23:59:59' and " +
                "a.address_location is null and a.event_code in (11,3) order by timestamp desc limit 200";

        System.err.println(sql);

        try {
            List<LogAutomaticEventReportTemporaryDTO> logAutomaticEventReportTemporaryDTOS = namedParameterJdbcTemplate.query(sql, new RowMapper<LogAutomaticEventReportTemporaryDTO>() {
                @Override
                public LogAutomaticEventReportTemporaryDTO mapRow(ResultSet resultSet, int i) throws SQLException {
                    LogAutomaticEventReportTemporaryDTO logAutomaticEventReportDetailDTO = new LogAutomaticEventReportTemporaryDTO();
                    logAutomaticEventReportDetailDTO.setId(resultSet.getLong("id"));
                    logAutomaticEventReportDetailDTO.setLatitude(resultSet.getDouble("latitude"));
                    logAutomaticEventReportDetailDTO.setLongitude(resultSet.getDouble("longitude"));
                    return logAutomaticEventReportDetailDTO;
                }
            });
            return logAutomaticEventReportTemporaryDTOS;
        }catch (EmptyResultDataAccessException ex){
            return new ArrayList<LogAutomaticEventReportTemporaryDTO>();
        }
    }

    public Long getLatestMileage(String logDate, Long imeiNumberParam){
        String sql = "";
        if(logDate != null){
            sql = "select a.mileage from log_automatic_event_report a where a.timestamp='"+logDate+"' and a.imei_number="+imeiNumberParam+" LIMIT 1";
        }
        try {
            Long result = jdbcTemplate.queryForObject(sql, Long.class);
            return  result;
        }catch (EmptyResultDataAccessException ex){
            return null;
        }
    }

    public Integer updateRecord(Long id, String addressLocation){
        Object[] params = { addressLocation, id};
        int[] types = {Types.VARCHAR, Types.BIGINT};
        int rows = jdbcTemplate.update(updateSql,params,types);
        return rows;
    }

}
