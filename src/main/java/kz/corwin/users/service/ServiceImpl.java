package kz.corwin.users.service;

import kz.corwin.users.entity.User;
import kz.corwin.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@org.springframework.stereotype.Service
@Slf4j
@Repository
@RequiredArgsConstructor
public class ServiceImpl implements Service {
    private final UsersRepository usersRepository;

    @Override
    @Transactional
    public void saveUser(User user)  {
        usersRepository.save(user);
    }

    public List<User> getUsers(){
        return usersRepository.findAll();
    }

    @Override
    public User getUser(Integer id) {
        return usersRepository.findById(id).get();
    }

    public void deleteUser(Integer id){
        usersRepository.deleteById(id);
    }

    public String updateUser(Integer id,User user){
        if (!usersRepository.existsById(id)){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        User newUser = usersRepository.findById(id).get();
        newUser.setUsername(user.getUsername());
        newUser.setSurname(user.getSurname());
        newUser.setCountry(user.getCountry());
        newUser.setDateOfBirdth(user.getDateOfBirdth());
        newUser.setRelationshipStatus(user.getRelationshipStatus());
        usersRepository.save(newUser);

        return String.format("Пользователь %s успешно изменен",newUser.getSurname());

    }
}
