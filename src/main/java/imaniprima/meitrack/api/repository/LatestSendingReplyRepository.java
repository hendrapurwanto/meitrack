package imaniprima.meitrack.api.repository;


import imaniprima.meitrack.api.domain.LatestSendingReply;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LatestSendingReplyRepository extends PagingAndSortingRepository<LatestSendingReply, Long>, QuerydslPredicateExecutor<LatestSendingReply> {
}
