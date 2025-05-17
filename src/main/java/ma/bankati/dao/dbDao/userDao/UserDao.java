package ma.bankati.dao.dbDao.userDao;

import ma.bankati.config.SessionFactory;
import ma.bankati.dao.userDao.IUserDao;
import ma.bankati.model.users.User;

import java.nio.file.Path;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class UserDao implements IUserDao {

    private Connection session;

    public UserDao(Path path) {
        this.session = SessionFactory.getInstance().openSession();
    }


    @Override
    public User findById(Long identity) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public User save(User newElement) {
        return null;
    }

    @Override
    public void delete(User element) {

    }

    @Override
    public void deleteById(Long identity) {

    }

    @Override
    public void update(User newValuesElement) {

    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return Optional.empty();
    }

}
