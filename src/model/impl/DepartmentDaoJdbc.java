package model.impl;

import java.sql.Connection;
import java.util.List;

import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJdbc implements DepartmentDao {

    private Connection conn;

    public DepartmentDaoJdbc(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void save(Department department) {

        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public void update(Department department) {

        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void deleteById(Integer idInteger) {

        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public Department findById(Integer id) {

        throw new UnsupportedOperationException("Unimplemented method 'findById'");

    }

    @Override
    public List<Department> findAll() {

        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

}
