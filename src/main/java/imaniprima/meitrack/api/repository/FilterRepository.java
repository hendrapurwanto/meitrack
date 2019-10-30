package imaniprima.meitrack.api.repository;

import imaniprima.meitrack.api.domain.Filter;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilterRepository extends PagingAndSortingRepository<Filter, Long>, QuerydslPredicateExecutor<Filter> {
}
