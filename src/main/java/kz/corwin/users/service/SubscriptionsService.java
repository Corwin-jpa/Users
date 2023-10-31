package kz.corwin.users.service;

import kz.corwin.users.entity.Subscription;

import java.util.List;

public interface SubscriptionsService {
    String saveSubscription(Subscription subscription);

    List<Subscription> getSubs(int id);

    String deleteSub(Subscription subscription);
}
