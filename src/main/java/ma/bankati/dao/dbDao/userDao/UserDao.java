package ma.bankati.dao.dbDao.userDao;

import ma.bankati.config.SessionFactory;
import ma.bankati.dao.userDao.IUserDao;
import ma.bankati.model.users.ERole;
import ma.bankati.model.users.User;

import javax.management.relation.Role;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class UserDao implements IUserDao {

    private Connection session;

    public UserDao(Path path) {
        this.session = SessionFactory.getInstance().openSession();
    }


    private User mapToUser(ResultSet resultSet) throws SQLException {
        var id = resultSet.getLong("Id");
        var firstName = resultSet.getString("FirstName");
        var lastName  = resultSet.getString("LastName");
        var username = resultSet.getString("username");
        var password = resultSet.getString("password");
        var role = ERole.valueOf(resultSet.getString("role"));
        var dateCreation = resultSet.getDate("dateCreation").toLocalDate();
        return new User(id, firstName, lastName, username, password, role, dateCreation);
    }


    @Override
    public User findById(Long identity) {
        return findAll().stream()
                .filter(u -> u.getId()
                        .equals(identity))
                        .findFirst().orElse(null);
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try{
            var sql = "SELECT * FROM users";
            PreparedStatement ps = session.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                users.add(mapToUser(rs));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return users;
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
