package model.dao;

import db.DB;
import model.impl.DepartmentDaoJdbc;
import model.impl.SellerDaoJdbc;

/* Fabrica de Dao expondo o tipo da interface, porem retornando uma implementação.
 * Gerando uma implementaçao de dependencia sem explicitar a implementação.
  */
public class DaoFactory {

    public static SellerDao createSellerDao() {
        return new SellerDaoJdbc(DB.getConnection());
    }

    public static DepartmentDao createDepartmentDao() {
        return new DepartmentDaoJdbc(DB.getConnection());
    }

}
