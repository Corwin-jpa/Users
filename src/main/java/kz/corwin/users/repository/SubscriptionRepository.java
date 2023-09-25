package kz.corwin.users.repository;

import kz.corwin.users.entity.Subscription;
import kz.corwin.users.entity.SubscriptionId;
import kz.corwin.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, SubscriptionId> {

    @Query(value = "select subscriptions.publisher_id, subscriptions.subscriber_id from subscriptions \n "+
            "where subscriptions.publisher_id = :publisherId",nativeQuery = true)
    List<Subscription> getSubs(@Param("publisherId") int publisherId);
}
