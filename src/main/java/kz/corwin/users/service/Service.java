package kz.corwin.users.service;

import kz.corwin.users.entity.User;

import java.util.List;
import java.util.UUID;

public interface Service {
    void saveUser(User user) throws Exception;
    List<User> getUsers() throws Exception;
    User getUser(Integer id);
    void deleteUser(Integer id);
    String updateUser(Integer id, User user);
}
