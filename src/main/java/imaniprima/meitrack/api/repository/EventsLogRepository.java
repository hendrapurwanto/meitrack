package imaniprima.meitrack.api.repository;

import imaniprima.meitrack.api.domain.EventsLog;
import imaniprima.meitrack.api.domain.LogAutomaticEventReport;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventsLogRepository extends PagingAndSortingRepository<EventsLog, Long>, QuerydslPredicateExecutor<EventsLog> {


    @Query(value="SELECT a.*,v.plate from log_automatic_event_report a left join vehicles v on a.imei_number = v.imei where TO_CHAR(timestamp, 'YYYY-MM-DD') in (:logDate) order by a.timestamp::DATE asc",nativeQuery = true)
    List<LogAutomaticEventReport> findDetailLogReportByDate(@Param("logDate") String logDate );

    @Query(value="select*from events_log where vehicle = :vehicleId order by id desc limit 1",nativeQuery = true)
    Optional<EventsLog> findLatestEventLogByVehicleId(@Param("vehicleId") Long vehicleId);
}
