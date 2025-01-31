package app;

import java.util.Date;
import java.util.List;

import model.dao.DAOFactory;
import model.dao.VendedorDAO;
import model.entities.Departamento;
import model.entities.Vendedor;

public class App {
    public static void main(String[] args) {

        VendedorDAO vendedorDAO = DAOFactory.createVendedorDAO();

        System.out.println();
        System.err.println("########## TESTE 1: Método findById de Vendedor ##########");
        System.out.println();

        Vendedor vendedor = vendedorDAO.findById(3);
        System.err.println(vendedor);

        System.out.println();
        System.err.println("########## TESTE 2: Método findByDepartamento de Vendedor ##########");
        System.out.println();

        List<Vendedor> vendedorList = vendedorDAO.findByDepartamento(2);
        vendedorList.forEach(System.out::println);

        System.out.println();
        System.err.println("########## TESTE 3: Método findByDepartamento de Vendedor ##########");
        System.out.println();

        vendedorList = vendedorDAO.findAll();
        vendedorList.forEach(System.out::println);

        System.out.println();
        System.err.println("########## TESTE 4: Método insert de Vendedor ##########");
        System.out.println();

        vendedor = new Vendedor(null, "Wesley Pereira", "wlypereiraa@gmail.com", new Date(), 2500.0, new Departamento(1, null));
        vendedorDAO.insert(vendedor);

        System.out.println();
        System.err.println("########## TESTE 5: Método update de Vendedor ##########");
        System.out.println();

        vendedor.setSalarioBase(3500.0);
        vendedorDAO.update(vendedor);

        System.out.println();

    }
}
