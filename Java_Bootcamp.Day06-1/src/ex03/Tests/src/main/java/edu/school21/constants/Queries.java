package edu.school21.constants;

public interface Queries {

    String FIND_ALL = "select * from products";

    String FIND_BY_ID = "select * from products where identifier = ?";

    String UPDATE = "update products set name = ?, price = ? where identifier = ?";

    String SAVE = "insert into products(name, price) values(?, ?)";

    String DELETE = "delete from products where identifier = ?";

}
