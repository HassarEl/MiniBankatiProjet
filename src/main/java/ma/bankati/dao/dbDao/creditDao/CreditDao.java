package ma.bankati.dao.dbDao.creditDao;

import ma.bankati.dao.creditDao.ICreditDao;
import ma.bankati.model.Crediit.Credit;

import java.util.List;

public class CreditDao implements ICreditDao {

    @Override
    public Credit findById(Long identity) {
        return null;
    }

    @Override
    public List<Credit> findAll() {
        return List.of();
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
