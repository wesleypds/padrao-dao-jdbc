package model.dao;

import db.DB;
import model.dao.impl.DepartamentoDaoJDBC;
import model.dao.impl.VendedorDaoJDBC;

public class DAOFactory {

    public static VendedorDAO createVendedorDAO() {
        return new VendedorDaoJDBC(DB.getConnection());
    }

    public static DepartamentoDAO createDepartamentoDAO() {
        return new DepartamentoDaoJDBC(DB.getConnection());
    }
}
