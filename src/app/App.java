package app;

import model.dao.DAOFactory;
import model.dao.VendedorDAO;
import model.entities.Vendedor;

public class App {
    public static void main(String[] args) {

        VendedorDAO vendedorDAO = DAOFactory.createVendedorDAO();
        Vendedor vendedor = vendedorDAO.findById(3);

        System.err.println(vendedor);

    }
}
