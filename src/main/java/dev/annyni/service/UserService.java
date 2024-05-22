package dev.annyni.service;

import dev.annyni.dao.UserDao;
import dev.annyni.dao.impl.UserDaoImpl;
import dev.annyni.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserService {

    private final UserDao userDao = UserDaoImpl.getInstance();

    private static final UserService INSTANCE = new UserService();

    public static UserService getInstance(){
        return INSTANCE;
    }


    public User create(User entity){
        return userDao.save(entity);
    }

    public User update(User entity){
        userDao.update(entity);
        return entity;
    }

    public boolean delete(Integer id){
        return userDao.delete(id);
    }

    public Optional<User> getById(Integer id){
        return userDao.findByID(id);
    }

    public List<User> getAll(){
        return userDao.findAll();
    }
}
