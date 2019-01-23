package coffee_management.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import coffee_management.dto.Product;
import coffee_management.jdbc.MyBatisSqlSessionFactory;

public class ProductMapperImpl implements ProductMapper {
	private static final String namespace = "coffee_management.dao.ProductMapper";
	
	private static final ProductMapper instance = new ProductMapperImpl();

	public static ProductMapper getInstance() {
		return instance;
	}
	
	public ProductMapperImpl() {}
	

	@Override
	public List<Product> selectProductByAll() throws SQLException {
		try(SqlSession sqlSession = MyBatisSqlSessionFactory.openSession()){
			return sqlSession.selectList(namespace + ".selectProductByAll");
		}
	}

	@Override
	public Product selectProductByCode(Product product) throws SQLException {
		try(SqlSession sqlSession = MyBatisSqlSessionFactory.openSession()){
			return sqlSession.selectOne(namespace + ".selectProductByCode", product);
		}
	}
	
	private Product getProduct(ResultSet rs) throws SQLException {
		String code = rs.getString("code");
		String name = rs.getString("name");
		
		return new Product(code, name);
	}

}
