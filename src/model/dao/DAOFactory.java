package model.dao;

import model.dao.impl.DepartamentoDaoJDBC;
import model.dao.impl.VendedorDaoJDBC;

public class DAOFactory {

    public static VendedorDAO createVendedorDAO() {
        return new VendedorDaoJDBC();
    }

    public static DepartamentoDAO createDepartamentoDAO() {
        return new DepartamentoDaoJDBC();
    }
}
