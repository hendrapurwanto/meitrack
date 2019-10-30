package imaniprima.meitrack.api.repository;

import imaniprima.meitrack.api.domain.GeofenceArea;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GeofenceAreaRepository extends PagingAndSortingRepository<GeofenceArea, Long>, QuerydslPredicateExecutor<GeofenceArea> {

    List<GeofenceArea> findByGroupId(Long groupId);
}
