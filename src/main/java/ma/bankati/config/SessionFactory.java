package ma.bankati.config;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class SessionFactory
{

    private static SessionFactory instance = new SessionFactory();
    private Connection session;

    public Connection openSession() {
        return session;
    }

    private SessionFactory() {

        var configFile = SessionFactory.class
                .getClassLoader()
                .getResourceAsStream("configFiles/db.properties");
        if (configFile != null){
            try {

                Properties properties = new Properties();
                properties.load(configFile);
                System.out.println("config file successfully loaded");

                var driver = properties.getProperty("driver");
                var url = properties.getProperty("url");
                var username = properties.getProperty("username");
                var password = properties.getProperty("password");

                Class.forName(driver);
                System.out.println("Driver JDBC successfully loaded");

                session = DriverManager.getConnection(url, username, password);
                System.out.println("MySQL Session successfully established");


            }catch (ClassNotFoundException e){
                System.err.println("Could not load JDBC driver!");
            }
            catch (IOException e){
                System.err.println("Could not find proprties File!");
            }
            catch (SQLException e){
                System.err.println("Connection to database Failed!");
            }
        }else {
            System.err.println("Could not find properties File!");
        }
    }

    public static SessionFactory getInstance() {
        return instance;
    }


    /*
    public static void main(String[] args) {

        SessionFactory.getInstance();
    }
    */
}