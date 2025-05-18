package ma.bankati.service.CreditService;

import ma.bankati.dao.creditDao.ICreditDao;
import ma.bankati.dao.dbDao.creditDao.CreditDao;
import ma.bankati.model.Crediit.Credit;

public class CreditService implements ICreditService {

    private final ICreditDao creditDao;
    public CreditService( ) {
        this.creditDao = new CreditDao();
    }


    @Override
    public Credit statusUpdate() {
        return null;
    }
}
