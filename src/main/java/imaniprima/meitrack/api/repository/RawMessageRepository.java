package imaniprima.meitrack.api.repository;

import imaniprima.meitrack.api.domain.LatestAutomaticEventReport;
import imaniprima.meitrack.api.domain.RawMessage;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RawMessageRepository extends PagingAndSortingRepository<RawMessage, Long>, QuerydslPredicateExecutor<RawMessage> {

}
