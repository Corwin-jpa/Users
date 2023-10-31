package kz.corwin.users.controller;

import kz.corwin.users.entity.User;
import kz.corwin.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public User getUser(@PathVariable Integer id) {
        return userService.getUser(id);
    }

    @PostMapping
    public String saveUser(@RequestBody User user) throws Exception {
        return userService.saveUser(user);
    }
    
    @GetMapping
    public List<User> getUsers() throws Exception{
        return userService.getUsers();
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Integer id){
        return userService.deleteUser(id);
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable Integer id, @RequestBody User user){
        return userService.updateUser(id,user);
    }


}
