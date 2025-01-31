package app;

import model.dao.DAOFactory;
import model.dao.DepartamentoDAO;
import model.entities.Departamento;

public class DepartamentoApp {
    public static void main(String[] args) {
        
        DepartamentoDAO departamentoDAO = DAOFactory.createDepartamentoDAO();

        System.out.println();
        System.err.println("########## TESTE 1: Método insert de Departamento ##########");
        System.out.println();

        departamentoDAO.insert(new Departamento(null, "Ferramentas"));

        System.out.println();
        System.err.println("########## TESTE 2: Método update de Departamento ##########");
        System.out.println();

        departamentoDAO.update(new Departamento(2, "Eletrônicos"));

        System.out.println();
        System.err.println("########## TESTE 3: Método delete de Departamento ##########");
        System.out.println();

        departamentoDAO.deleteById(6);

        System.out.println();
        System.err.println("########## TESTE 4: Método findById de Departamento ##########");
        System.out.println();

        System.err.println(departamentoDAO.findById(1));

        System.out.println();

        System.err.println("########## TESTE 5: Método findAll de Departamento ##########");
        System.out.println();

        departamentoDAO.findAll().forEach(System.out::println);

        System.out.println();

    }
}
