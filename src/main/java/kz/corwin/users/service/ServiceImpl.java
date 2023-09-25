package kz.corwin.users.service;

import kz.corwin.users.entity.Subscription;
import kz.corwin.users.entity.SubscriptionId;
import kz.corwin.users.entity.User;
import kz.corwin.users.repository.SubscriptionRepository;
import kz.corwin.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.security.InvalidKeyException;
import java.util.List;

@org.springframework.stereotype.Service
@Slf4j
@Repository
@RequiredArgsConstructor
public class ServiceImpl implements Service {
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;

    @Override
    @Transactional
    public String saveUser(User user)  {
        userRepository.save(user);
        return String.format("Пользователь %s %s успешно добавлен",user.getSurname(),user.getUsername());
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    @Override
    public User getUser(Integer id) {
        return userRepository.findById(id).get();
    }

    public String deleteUser(Integer id){
        User deletedUser = userRepository.findById(id).get();
        userRepository.deleteById(id);
        return String.format("Пользователь %s %s успешно удален",deletedUser.getSurname(),deletedUser.getUsername());
    }

    public String updateUser(Integer id,User user){
        if ((!userRepository.existsById(id))&&(user.getDeleted()==Boolean.FALSE)){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        User newUser = userRepository.findById(id).get();
        newUser.setUsername(user.getUsername());
        newUser.setSurname(user.getSurname());
        newUser.setCountry(user.getCountry());
        newUser.setDateOfBirdth(user.getDateOfBirdth());
        newUser.setRelationshipStatus(user.getRelationshipStatus());
        userRepository.save(newUser);

        return String.format("Пользователь %s успешно изменен",newUser.getSurname());
    }

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

        subscriptionRepository.save(newSubscription);

        return String.format("Пользователь %s успешно подписался на пользователя %s",newSubscription.getSubscriberId(),newSubscription.getPublisherId());

    }

    public List<Subscription> getSubs(int id) {
        return subscriptionRepository.getSubs(id);
    }

    public String deleteSub(Subscription subscription){
        Subscription deletedSubscription = new Subscription();
        deletedSubscription.setPublisherId(subscription.getPublisherId());
        deletedSubscription.setSubscriberId(subscription.getSubscriberId());
        subscriptionRepository.delete(subscription);
        return String.format("Пользователь %s больше не подписан на пользователя %s",deletedSubscription.getSubscriberId(),deletedSubscription.getPublisherId());
    }


}
