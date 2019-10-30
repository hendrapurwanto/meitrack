package imaniprima.meitrack.api.repository;

import imaniprima.meitrack.api.domain.Users;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends PagingAndSortingRepository<Users, Long>, QuerydslPredicateExecutor<Users> {

    Optional<Users> findByUsername (String username);
    Users findMapById (Long id);
    Users findMapByUsername (String username);
    Optional<Users> findByEmail(String email);
}
