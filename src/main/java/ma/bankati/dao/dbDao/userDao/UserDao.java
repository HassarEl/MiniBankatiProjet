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

    public UserDao() {
        this.session = SessionFactory.getInstance().openSession();
    }


    private User mapToUser(ResultSet resultSet) throws SQLException {
        var id = resultSet.getLong("Id");
        var firstName = resultSet.getString("FirstName");
        var lastName = resultSet.getString("LastName");
        var username = resultSet.getString("username");
        var password = resultSet.getString("password");

        String roleStr = resultSet.getString("role");
        ERole role;
        try {
            role = ERole.valueOf(roleStr.toUpperCase());
        } catch (Exception e) {
            role = ERole.CLIENT; // Default role if parsing fails
        }

        LocalDate creationDate = null;
        try {
            Date date = resultSet.getDate("created_at");
            if (date != null) {
                creationDate = ((java.sql.Date) date).toLocalDate();
            }
        } catch (SQLException e) {
            creationDate = LocalDate.now();
        }

        return new User(id, firstName, lastName, username, password, role, creationDate);
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
        try {
            var sql = "INSERT INTO users(FirstName, LastName, username, password, role, created_at) values(?,?,?,?,?,?)";
            PreparedStatement ps = session.prepareStatement(sql);
            ps.setString(1, newElement.getFirstName());
            ps.setString(2, newElement.getLastName());
            ps.setString(3, newElement.getUsername());
            ps.setString(4, newElement.getPassword());
            ps.setString(5, newElement.getRole().toString());
            ps.setDate(6, java.sql.Date.valueOf(newElement.getCreationDate()));

            var status = ps.executeUpdate();
            if(status == 0) {
                System.err.println("no User line added : insert failed!!");
            }else{
                var rs = ps.getGeneratedKeys();
                if(rs.next()) {
                    var id = rs.getLong(1);
                    newElement.setId(id);
                    System.out.println("user " + id + " added successfully");
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return newElement;
    }

    @Override
    public void delete(User element) {
        try{
            var sql = "DELETE FROM users WHERE Id = ?";
            PreparedStatement ps = session.prepareStatement(sql);
            ps.setLong(1, element.getId());
            var status = ps.executeUpdate();
            if(status == 0) {
                System.err.println("no User line added : delete failed!!");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Long identity) {
        delete(findById(identity));
    }

    @Override
    public void update(User newValuesElement) {
        try{
            var sql = "UPDATE users set firstName = ?, lastName = ?, username = ?, password = ?, role = ? WHERE Id = ?";
            PreparedStatement ps = session.prepareStatement(sql);
            ps.setString(1, newValuesElement.getFirstName());
            ps.setString(2, newValuesElement.getLastName());
            ps.setString(3, newValuesElement.getUsername());
            ps.setString(4, newValuesElement.getPassword());
            ps.setString(5, newValuesElement.getRole().toString());
            // ps.setDat(6, java.sql.Date.valueOf(newValuesElement.getCreationDate()));
            ps.setLong(6, newValuesElement.getId());
            var status = ps.executeUpdate();
            if(status == 0) {
                System.err.println("no User line added : update failed!!");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return findAll()
                .stream()
                .filter(user -> login.equals(user.getUsername()) && password.equals(user.getPassword()))
                .findFirst();
    }

}
