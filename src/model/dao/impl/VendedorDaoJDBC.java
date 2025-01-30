package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        PreparedStatement statement = null;

        // try {
        //     statement = conn.prepareStatement(
        //         "INSERT INTO vendedor " +
        //         "(nome,email,data_aniversario,salario_base,)"
        //     );
        // } catch (Exception e) {
        //     // TODO: handle exception
        // }

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
            if (result.next()) {

                Departamento departamento = instanciaDepartamento(result);
                Vendedor vendedor = instanciaVendedor(result, departamento);

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

    public List<Vendedor> findByDepartamento(Integer id) {
        
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            statement = conn.prepareStatement(
                "SELECT vendedor.*, departamento.nome as depNome " +
                "FROM vendedor INNER JOIN departamento " +
                "ON vendedor.id_departamento = departamento.id " +
                "WHERE id_departamento = ? " +
                "ORDER BY nome"
            );
            statement.setInt(1, id);
            result = statement.executeQuery();
            List<Vendedor> list = new ArrayList<>();
            Map<Integer, Departamento> mapDep = new HashMap<>();
            while (result.next()) {

                Departamento dep = mapDep.get(result.getInt("id_departamento"));

                if (dep == null) {
                    dep = instanciaDepartamento(result);
                    mapDep.put(dep.getId(), dep);
                }
                
                Vendedor vendedor = instanciaVendedor(result, dep);
                list.add(vendedor);

            }
            return list;
        } catch (SQLException e) {
            throw new DbException("Error: " + e.getMessage());
        } finally {
            DB.closeStatement(statement);
            DB.closeResultSet(result);
        }

    }
    
    private Departamento instanciaDepartamento(ResultSet result) throws SQLException {
        Departamento departamento = new Departamento();
        departamento.setId(result.getInt("id_departamento"));
        departamento.setNome(result.getString("depNome"));
        return departamento;
    }

    private Vendedor instanciaVendedor(ResultSet result, Departamento departamento) throws SQLException {
        Vendedor vendedor = new Vendedor();
        vendedor.setId(result.getInt("id"));
        vendedor.setNome(result.getString("nome"));
        vendedor.setEmail(result.getString("email"));
        vendedor.setDataAniversario(result.getDate("data_aniversario"));
        vendedor.setSalarioBase(result.getDouble("salario_base"));
        vendedor.setDepartamento(departamento);
        return vendedor;
    }
}
