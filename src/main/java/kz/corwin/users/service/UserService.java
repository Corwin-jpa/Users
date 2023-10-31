package kz.corwin.users.service;

import kz.corwin.users.entity.Subscription;
import kz.corwin.users.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    String saveUser(User user) throws Exception;
    List<User> getUsers() throws Exception;
    User getUser(Integer id);
    String deleteUser(Integer id);
    String updateUser(Integer id, User user);
}
