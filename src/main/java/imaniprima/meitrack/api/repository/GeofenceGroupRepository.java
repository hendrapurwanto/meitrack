package imaniprima.meitrack.api.repository;

import imaniprima.meitrack.api.domain.GeofenceGroup;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeofenceGroupRepository extends PagingAndSortingRepository<GeofenceGroup, Long>, QuerydslPredicateExecutor<GeofenceGroup> {

}
