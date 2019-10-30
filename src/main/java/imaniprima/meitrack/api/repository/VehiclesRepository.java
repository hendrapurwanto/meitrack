package imaniprima.meitrack.api.repository;

import imaniprima.meitrack.api.domain.Vehicles;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface VehiclesRepository extends PagingAndSortingRepository<Vehicles, Long>, QuerydslPredicateExecutor<Vehicles> {
    @Query(value = "select id from vehicles order by id asc", nativeQuery = true)
    List<Long> findListIdVehicle ();
}
