package ma.bankati.dao;

import ma.bankati.model.users.ERole;
import ma.bankati.model.users.User;

public class AdminDao {
    private final User user;

    private AdminDao(){
        this.user = new User();
        this.user.setId(1L);
        this.user.setFirstName("Admin");
        this.user.setLastName("Admin");
        this.user.setUsername("Admin");
        this.user.setPassword("1234");
        this.user.setRole(ERole.ADMIN);
    }


}
