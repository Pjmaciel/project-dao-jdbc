package app;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Application {

    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();
        System.out.println("");
        System.out.println("");
        System.out.println("=== Test 1: Seller findBy Id ===");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);

        System.out.println("\n=== Test 2: Seller findBy DepartmentId ===");
        Department department = new Department(2, null);
        List<Seller> list = sellerDao.finByDepartment(department);
        for (Seller s : list) {
            System.out.println(s);
        }

        System.out.println("\n=== Test 3: Seller findAll ===");

        list = sellerDao.findAll();
        for (Seller seller2 : list) {
            System.out.println(seller2);

        }

        System.out.println("\n=== Test 4  : Seller SaveInsert ===");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Department departament = new Department(3, null);

        Seller s2 = new Seller();
        s2.setName(" Naruto");
        s2.setEmail("narutouzumaki@hotmail.com");
        s2.setBirthDate(new Date());
        s2.setBaseSalary(4000.00);
        s2.setDepartment(department);

        sellerDao.saveInsert(s2);
        System.out.println("Inserted! new Id = " + s2.getId());

    }

}
