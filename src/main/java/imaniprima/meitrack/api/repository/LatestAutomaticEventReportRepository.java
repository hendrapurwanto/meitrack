package imaniprima.meitrack.api.repository;

import imaniprima.meitrack.api.domain.LatestAutomaticEventReport;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LatestAutomaticEventReportRepository extends PagingAndSortingRepository<LatestAutomaticEventReport, Long>, QuerydslPredicateExecutor<LatestAutomaticEventReport> {

    Optional<LatestAutomaticEventReport> findByImeiNumber (Long imei);
}
