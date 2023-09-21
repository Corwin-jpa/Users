package kz.corwin.users.controller;

import kz.corwin.users.entity.User;
import kz.corwin.users.service.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class Controller {
    private final Service service;

    @GetMapping("/{id}")
    public User getUser(@PathVariable Integer id) {
        return service.getUser(id);
    }

    @PostMapping
    public void saveUser(@RequestBody User user) throws Exception {
        service.saveUser(user);
    }
    
    @GetMapping
    public List<User> getUsers() throws Exception{
        return service.getUsers();
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id){
        service.deleteUser(id);
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable Integer id, @RequestBody User user){
        return service.updateUser(id,user);
    }
}
