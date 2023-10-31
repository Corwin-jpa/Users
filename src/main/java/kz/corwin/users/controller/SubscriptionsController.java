package kz.corwin.users.controller;

import kz.corwin.users.entity.Subscription;
import kz.corwin.users.service.SubscriptionsService;
import kz.corwin.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/subscriptions")
@RequiredArgsConstructor
public class SubscriptionsController {
    private final UserService userService;
    private final SubscriptionsService subscriptionsService;

    @PostMapping
    public String saveSubscription (@RequestBody Subscription subscription){
        return subscriptionsService.saveSubscription(subscription);
    }

    @GetMapping("/{id}")
    public List<Subscription> getSubs(@PathVariable int id) {
        return subscriptionsService.getSubs(id);
    }

    @DeleteMapping
    public String deleteSub (@RequestBody Subscription subscription){
        return subscriptionsService.deleteSub(subscription);
    }
}
