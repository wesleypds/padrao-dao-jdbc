package model.dao;

import java.util.List;

import model.entities.Departamento;

public interface DepartamentoDAO {

    void insert(Departamento departamento);

    void update(Departamento departamento);

    void deleteById(Integer id);

    Departamento findById(Integer id);

    List<Departamento> findAll();

}
