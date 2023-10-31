package kz.corwin.users.service;

import kz.corwin.users.entity.Subscription;
import kz.corwin.users.entity.User;
import kz.corwin.users.repository.SubscriptionsRepository;
import kz.corwin.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@org.springframework.stereotype.Service
@Slf4j
@Repository
@RequiredArgsConstructor
public class SubscriptiobsServiceImpl implements SubscriptionsService {
    private final UserRepository userRepository;
    private final SubscriptionsRepository subscriptionsRepository;

    public String saveSubscription (Subscription subscription){
        if (subscription.getPublisherId()==subscription.getSubscriberId()){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        User user = userRepository.findById(subscription.getPublisherId()).get();
        if (user.getDeleted()==Boolean.TRUE){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        User subUser = userRepository.findById(subscription.getSubscriberId()).get();
        if (subUser.getDeleted()==Boolean.TRUE){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Subscription newSubscription = new Subscription();
        newSubscription.setPublisherId(subscription.getPublisherId());
        newSubscription.setSubscriberId(subscription.getSubscriberId());

        subscriptionsRepository.save(newSubscription);

        return String.format("Пользователь %s успешно подписался на пользователя %s",newSubscription.getSubscriberId(),newSubscription.getPublisherId());

    }

    public List<Subscription> getSubs(int id) {
        return subscriptionsRepository.getSubs(id);
    }

    public String deleteSub(Subscription subscription){
        Subscription deletedSubscription = new Subscription();
        deletedSubscription.setPublisherId(subscription.getPublisherId());
        deletedSubscription.setSubscriberId(subscription.getSubscriberId());
        subscriptionsRepository.delete(subscription);
        return String.format("Пользователь %s больше не подписан на пользователя %s",deletedSubscription.getSubscriberId(),deletedSubscription.getPublisherId());
    }
}
