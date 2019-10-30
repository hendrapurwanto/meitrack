package imaniprima.meitrack.api.repository;

import imaniprima.meitrack.api.domain.Events;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventsRepository extends PagingAndSortingRepository<Events, Long>, QuerydslPredicateExecutor<Events> {
    Optional<Events> findByEventCode (Long eventCode);

    @Query(value="select*from events where event_code not in ('1008','1007','1005')",nativeQuery = true)
    List<Events> findAllCustom();
}
