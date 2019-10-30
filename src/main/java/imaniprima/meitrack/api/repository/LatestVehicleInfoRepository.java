package imaniprima.meitrack.api.repository;

import imaniprima.meitrack.api.domain.LatestVehicleInfo;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LatestVehicleInfoRepository extends PagingAndSortingRepository<LatestVehicleInfo, Long>, QuerydslPredicateExecutor<LatestVehicleInfo> {
}
