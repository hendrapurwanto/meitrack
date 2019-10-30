package imaniprima.meitrack.api.repository;

import imaniprima.meitrack.api.domain.Administrator;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministratorRepository extends PagingAndSortingRepository<Administrator, Long>, QuerydslPredicateExecutor<Administrator> {
}
