package kz.corwin.users.repository;

import kz.corwin.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


@org.springframework.stereotype.Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Override
    List<User> findAll();
}
