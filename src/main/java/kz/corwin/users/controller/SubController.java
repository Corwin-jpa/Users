package kz.corwin.users.controller;

import kz.corwin.users.entity.Subscription;
import kz.corwin.users.entity.User;
import kz.corwin.users.service.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/subscriptions")
@RequiredArgsConstructor
public class SubController {
    private final Service service;

    @PostMapping
    public String saveSubscription (@RequestBody Subscription subscription){
        return service.saveSubscription(subscription);
    }

    @GetMapping("/{id}")
    public List<Subscription> getSubs(@PathVariable int id) {
        return service.getSubs(id);
    }

    @DeleteMapping
    public String deleteSub (@RequestBody Subscription subscription){
        return service.deleteSub(subscription);
    }
}
