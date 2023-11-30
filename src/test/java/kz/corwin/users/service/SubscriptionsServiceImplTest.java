package kz.corwin.users.service;

import kz.corwin.users.entity.Subscription;
import kz.corwin.users.entity.SubscriptionId;
import kz.corwin.users.entity.User;
import kz.corwin.users.repository.SubscriptionsRepository;
import kz.corwin.users.repository.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class SubscriptionsServiceImplTest {

    @Mock
    SubscriptionsRepository subscriptionsRepository;

    @InjectMocks
    SubscriptionsServiceImpl subscriptionsService;

    @org.junit.jupiter.api.Test
    void saveSubscription() {
        User user1 = new User("Ivan", "Ivanov");
        User user2 = new User("Fedor", "Fedorov");
        User savedUser1 = new User(1, "Ivan", "Ivanov");
        User savedUser2 = new User(2, "Fedor", "Fedorov");

        UserRepository userRepository = Mockito.mock(UserRepository.class);

        Mockito.when(userRepository.save(user1)).thenReturn(savedUser1);
        Mockito.when(userRepository.save(user2)).thenReturn(savedUser2);

        UserServiceImpl userService = new UserServiceImpl(userRepository);

        userService.saveUser(user1);
        userService.saveUser(user2);

        Subscription subscription = new Subscription();
        subscription.setPublisherId(1);
        subscription.setSubscriberId(2);
        Subscription subscriptionCreated = new Subscription(subscription.getPublisherId(), subscription.getSubscriberId());

        Mockito.when(subscriptionsRepository.save(subscription)).thenReturn(subscriptionCreated);
        subscriptionsService.saveSubscription(subscription);
        assertNotNull(subscriptionCreated.getPublisherId());
        assertNotNull(subscriptionCreated.getSubscriberId());
        assertEquals(1, subscriptionCreated.getPublisherId());
        assertEquals(2, subscriptionCreated.getSubscriberId());
    }

    @org.junit.jupiter.api.Test
    void getSubs() {
        Subscription subscription1 = new Subscription();
        subscription1.setSubscriberId(2);
        subscription1.setPublisherId(4);

        Subscription subscription2 = new Subscription();
        subscription2.setSubscriberId(5);
        subscription2.setPublisherId(4);

        when(subscriptionsRepository.getSubs(4)).thenReturn(Arrays.asList(subscription1, subscription2));
        List<Subscription> subs = subscriptionsService.getSubs(4);
        assertEquals(subs.get(0).getPublisherId(), 4);
        assertEquals(subs.get(1).getPublisherId(), 4);
        assertEquals(subs.get(0).getSubscriberId(), 2);
        assertEquals(subs.get(1).getSubscriberId(), 5);

        assertNotEquals(subs.get(0).getPublisherId(), 0);
        assertNotEquals(subs.get(1).getPublisherId(), 0);
        assertNotEquals(subs.get(0).getSubscriberId(), 0);
        assertNotEquals(subs.get(1).getSubscriberId(), 0);
    }

    @org.junit.jupiter.api.Test
    void deleteSub() {
        Subscription subscription = new Subscription(1, 2);
        SubscriptionId subscriptionId = new SubscriptionId(1, 2);
        when(subscriptionsRepository.findById(subscriptionId)).thenReturn(Optional.of(subscription));
        subscriptionsService.deleteSub(subscription);
        verify(subscriptionsRepository, times(1)).delete(subscription);
        ArgumentCaptor<Subscription> subArgumentCaptor = ArgumentCaptor.forClass(Subscription.class);
        verify(subscriptionsRepository).delete(subArgumentCaptor.capture());
        Subscription subDeleted = subArgumentCaptor.getValue();
        assertNotNull(subDeleted);
        assertEquals(subscription, subDeleted);
    }
}