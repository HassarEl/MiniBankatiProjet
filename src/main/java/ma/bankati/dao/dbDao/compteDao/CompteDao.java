package ma.bankati.dao.dbDao.compteDao;

import ma.bankati.dao.creditDao.ICreditDao;
import ma.bankati.dao.dataDao.fileDb.IComptDao;
import ma.bankati.model.compte.Compte;

import java.util.List;

public class CompteDao implements IComptDao {





    @Override
    public Compte findById(Long identity) {
        return null;
    }

    @Override
    public List<Compte> findAll() {
        return List.of();
    }

    @Override
    public Compte save(Compte newElement) {
        return null;
    }

    @Override
    public void delete(Compte element) {

    }

    @Override
    public void deleteById(Long identity) {

    }

    @Override
    public void update(Compte newValuesElement) {

    }
}
