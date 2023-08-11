package model.dao;

import java.util.List;

import model.entities.Department;

public interface DepartmentDao {

    public void saveInsert(Department department);

    public void update(Department department);

    public void deleteById(Integer id);

    public Department findById(Integer id);

    List<Department> findAll();

}
