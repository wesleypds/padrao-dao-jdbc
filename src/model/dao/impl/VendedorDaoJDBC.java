package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.VendedorDAO;
import model.entities.Departamento;
import model.entities.Vendedor;

public class VendedorDaoJDBC implements VendedorDAO {

    private Connection conn = null;

    public VendedorDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Vendedor vendedor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }

    @Override
    public void update(Vendedor vendedor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void deleteById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public Vendedor findById(Integer id) {

        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            statement = conn.prepareStatement(
                "SELECT vendedor.*, departamento.nome as depNome " +
                "FROM vendedor INNER JOIN departamento " +
                "ON vendedor.id_departamento = departamento.id " +
                "WHERE vendedor.id = ?"
            );
            statement.setInt(1, id);
            result = statement.executeQuery();
            System.err.println(result);
            if (result.next()) {
                Integer idVendedor = result.getInt("id");
                String nome = result.getString("nome");
                String email = result.getString("email");
                Date dataAniversario = result.getDate("data_aniversario");
                Double salarioBase = result.getDouble("salario_base");
                Integer idDepartamento = result.getInt("id_departamento");
                String nomeDepartamento = result.getString("depNome");

                Departamento departamento = new Departamento(idDepartamento, nomeDepartamento);
                Vendedor vendedor = new Vendedor(idVendedor, nome, email, dataAniversario, salarioBase, departamento);

                return vendedor;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException("Error: " + e.getMessage());
        } finally {
            DB.closeStatement(statement);
            DB.closeResultSet(result);
        }

    }

    @Override
    public List<Vendedor> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }
    
}
