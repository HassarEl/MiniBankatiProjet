package ma.bankati.dao.clientDao;

import ma.bankati.model.users.ERole;
import ma.bankati.model.users.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ClientDao implements IClientDao{

    Path path;
    public ClientDao(){
        try{
            this.path = Paths
                    .get(
                            getClass()
                                    .getClassLoader()
                                    .getResource("FileBase/clients.txt")
                                    .toURI()
                    );
        }catch (Exception e){
            System.err.println("FileBase not found");
        }
    }

    private User map(String fileLine){
        String[] fields = fileLine.split("-");
        Long    id          = Long.parseLong(fields[0]);
        String  firstName   = fields[1].equals("null") ? null : fields[1];
        String  lastName    = fields[2].equals("null") ? null : fields[2];
        String  username    = fields[3].equals("null") ? null : fields[3];
        String  password    = fields[4].equals("null") ? null : fields[4];
        ERole   role        = fields[5].equals("null") ? null : (fields[5].equals("ADMIN") ? ERole.ADMIN : ERole.CLIENT);
        LocalDate  creationDate  = fields[6].equals("null") ? null :
                LocalDate.parse(fields[6], DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        return new User(id, firstName, lastName, username, password, role, creationDate);
    }

    private String mapToFileLine(User client){
        Long    id          = client.getId();
        String  firstName   = client.getFirstName() == null || client.getFirstName().trim().length() == 0 ? "null" : client.getFirstName();
        String  lastName    = client.getLastName()  == null || client.getLastName().trim().length()  == 0 ? "null" : client.getLastName();
        String  username    = client.getUsername()  == null || client.getUsername().trim().length()  == 0 ? "null" : client.getUsername();
        String  password    = client.getPassword()  == null || client.getPassword().trim().length()  == 0 ? "null" : client.getPassword();
        ERole   role        = client.getRole()      == null ? null : client.getRole();
        String  creationDate  = client.getCreationDate()  == null  ? "null" : client.getCreationDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")).toString();

        return id+"-"+firstName+"-"+lastName+"-"+username+"-"+password+"-"+role+"-"+creationDate + System.lineSeparator();
    }

    private long newMaxId(){
        return findAll().stream().mapToLong(User::getId).max().orElse(0) + 1;
    }

    @Override
    public User findById(Long identity) {

        return findAll().stream()
                .filter(u -> u.getId().equals(identity))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<User> findAll() {
        try{
            return
                    Files.readAllLines(path)
                            .stream()
                            .skip(1)
                            .map(line -> map(line))
                            .toList();
        }catch (IOException e){
            System.err.println("Error lors de la recuperation des client");
            return new ArrayList<>();
        }
    }

    @Override
    public User save(User newElement) {
        try{
            newElement.setId(newMaxId());
            newElement.setCreationDate(LocalDate.now());
            Files.writeString(path, mapToFileLine(newElement), StandardOpenOption.APPEND);
            return newElement;
        }catch (IOException e){
            System.err.println("Error lors de l'enregistrement à la base de données");
            return null;
        }
    }

    @Override
    public void delete(User element) {
        deleteById(element.getId());
    }

    @Override
    public void deleteById(Long identity) {
        List<User> updatedList = findAll().stream()
                .filter(u -> !u.getId().equals(identity))
                .toList();
        rewriteFile(updatedList);
    }

    @Override
    public void update(User newValuesElement) {
        List<User> updatedList = findAll().stream()
                .map(user -> user.getId().equals(newValuesElement.getId()) ? newValuesElement : user)
                .toList();
        rewriteFile(updatedList);
    }

    public void rewriteFile(List<User> clients){
        try{
            List<String> lines = new ArrayList<>();
            lines.add("ID-FirstName-LastName-UserName-Password-Role-Created_At"); // Header
            for (User client : clients) {
                lines.add(mapToFileLine(client).trim());
            }
            Files.write(path, lines);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
