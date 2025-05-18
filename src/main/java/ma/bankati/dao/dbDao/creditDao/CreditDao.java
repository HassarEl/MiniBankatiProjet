package ma.bankati.dao.dbDao.creditDao;

import ma.bankati.config.SessionFactory;
import ma.bankati.dao.creditDao.ICreditDao;
import ma.bankati.model.Crediit.Credit;
import ma.bankati.model.Crediit.Status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CreditDao implements ICreditDao {

    private Connection session;

    public CreditDao() {
        this.session = SessionFactory.getInstance().openSession();
    }

    // mapToCredit Methode
    private Credit mapToCredit(ResultSet rs) throws SQLException {
        var id = rs.getLong("id_credit");
        var mt_credit = rs.getDouble("mt_credit");
        var nbrMois = rs.getLong("nbr_mois");
        var motif = rs.getString("motif");
        var status = Status.valueOf( rs.getString("status"));
        var created = rs.getTimestamp("created");

        Credit credit = new Credit(id, mt_credit, nbrMois, motif, status);

        return credit;
    }

    @Override
    public Credit findById(Long identity) {
        return findAll().stream()
                .filter(credit -> credit.getId()
                        .equals(identity))
                .findFirst().orElse(null);
    }


    // FindAll Methode
    @Override
    public List<Credit> findAll() {
        List<Credit> credits = new ArrayList<Credit>();
        try {
            var sql = "SELECT * FROM credit";
            PreparedStatement ps = session.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                credits.add(mapToCredit(rs));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return credits;
    }

    @Override
    public Credit save(Credit newElement) {
        try{
            var sql = "INSERT INTO credit (mt_credit, nbr_mois, motif, status) VALUES (?,?,?,?)";
            PreparedStatement ps = session.prepareStatement(sql);
            ps.setDouble(1, newElement.mtCredit);
            ps.setLong(2, newElement.nbrMois);
            ps.setString(3, newElement.motif);
            ps.setString(4, newElement.getStatus().toString());
            var status = ps.executeUpdate();

            if(status == 0){
                System.err.println("Credit insert failed!!");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return newElement;
    }

    @Override
    public void delete(Credit element) {
        try{
            var sql = "DELETE FROM credit WHERE id_credit = ?";
            PreparedStatement ps = session.prepareStatement(sql);
            ps.setLong(1, element.getId());
            var status = ps.executeUpdate();
             if(status == 0){
                 System.err.println("Credit delete failed!!");
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
    public void update(Credit newValuesElement) {
        try{
            var sql = "update credit set mt_credit = ? , nbr_mois = ? , motif = ?, status = ? where id_credit = ?";
            PreparedStatement ps = session.prepareStatement(sql);
            ps.setDouble(1, newValuesElement.mtCredit);
            ps.setLong(2, newValuesElement.nbrMois);
            ps.setString(3, newValuesElement.motif);
            ps.setString(4, newValuesElement.getStatus().toString());
            ps.setLong(5, newValuesElement.getId());
            var status = ps.executeUpdate();
            if(status == 0){
                System.err.println("Credit update failed!!");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
