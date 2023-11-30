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

import javax.transaction.Transactional;
import java.util.List;

@org.springframework.stereotype.Service
@Slf4j
@Repository
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public String saveUser(User user)   {
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




}
