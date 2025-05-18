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
        return null;
    }

    @Override
    public void delete(Credit element) {

    }

    @Override
    public void deleteById(Long identity) {

    }

    @Override
    public void update(Credit newValuesElement) {

    }
}
