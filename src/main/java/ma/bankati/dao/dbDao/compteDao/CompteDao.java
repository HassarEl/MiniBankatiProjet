package ma.bankati.dao.dbDao.compteDao;

import ma.bankati.config.SessionFactory;
import ma.bankati.dao.creditDao.ICreditDao;
import ma.bankati.dao.dataDao.fileDb.IComptDao;
import ma.bankati.model.compte.Compte;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompteDao implements IComptDao {

    private Connection session;
    public CompteDao() {
        this.session = SessionFactory.getInstance().openSession();
    }

    private Compte mapToCompte(ResultSet resultSet) throws SQLException {
        var id = resultSet.getLong("id");
        var solde = resultSet.getString("solde");

        Compte compte = new Compte(id, solde);
        return compte;
    }

    @Override
    public List<Compte> findAll() {
        List<Compte> comptes = new ArrayList<Compte>();
        try{
            var sql = "SELECT * FROM compte";
            PreparedStatement ps = session.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                comptes.add(mapToCompte(rs));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return comptes;
    }


    @Override
    public Compte findById(Long identity) {
        return findAll().stream()
                .filter(compte -> compte.getId() == identity)
                .findFirst().orElse(null);
    }


    @Override
    public Compte save(Compte newElement) {
        try{
            var sql = "INSERT INTO compte (solde) VALUES (?)";
            PreparedStatement ps = session.prepareStatement(sql);
            ps.setString(1, newElement.getSolde());
            var status = ps.executeUpdate();
            if(status == 0){
                System.err.println("Compte save failed");

            }else{
                var rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    var id = rs.getLong(1);
                    newElement.setId(id);
                    System.out.println("Compte : " + id + "added seccessfully");
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return newElement;
    }
    
    @Override
    public void update(Compte newValuesElement) {
        try{
            var sql = "UPDATE compte SET solde = ? WHERE id_compte = ?";
            PreparedStatement ps = session.prepareStatement(sql);
            ps.setString(1, newValuesElement.getSolde());
            ps.setLong(2, newValuesElement.getId());
            var status = ps.executeUpdate();
            if(status == 0){
                System.err.println("Compte update failed");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Compte element) {
        try{
            var sql = "DELETE FROM compte WHERE id_compte = ?";
            PreparedStatement ps = session.prepareStatement(sql);
            ps.setLong(1, element.getId());
            var status = ps.executeUpdate();
            if(status == 0){
                System.err.println("Compte delete failed");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Long identity) {
        delete(findById(identity));
    }
}
