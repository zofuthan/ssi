package me.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import me.entry.Person;

public class IPersonDaoTest {

	private SqlSessionFactory sessionFactory;
	private SqlSession session;

	@BeforeSuite
	public void initConnection() throws IOException {
		InputStream stream = new FileInputStream(new File("src/main/resources/mybatis-config.xml"));
		sessionFactory = new SqlSessionFactoryBuilder().build(stream);
	}

	@BeforeClass
	public void createTable() throws SQLException{
		session = sessionFactory.openSession();
		Connection conn = session.getConnection();
		Statement st = conn.createStatement();
		st.execute("CREATE TABLE PERSON (ID INTEGER, NAME VARCHAR(100), AGE INTEGER)");
		session.close();
	}
	
	@AfterClass
	public void dropTable() throws SQLException{
		session = sessionFactory.openSession();
		Connection conn = session.getConnection();
		Statement st = conn.createStatement();
		st.execute("DROP TABLE PERSON");
		session.close();
	}
	
	@BeforeMethod
	public void openSession() {
		session = sessionFactory.openSession();
	}

	@AfterMethod
	public void colseSession() {
		try {
			session.commit();
		} catch (Exception e) {
			session.rollback();
		} finally {
			session.close();
		}
	}

	@Test(dependsOnMethods = { "testSavePerson" })
	public void testGetPerson() {
		Person person = session.selectOne("enrty.PersonMapper.selectPerson", "1");
		Assert.assertNotNull(person);
	}

	@Test
	public void testSavePerson() {
		Person person = new Person();
		person.setId(1);
		person.setName("张三");
		person.setAge(20);
		session.insert("enrty.PersonMapper.insertPerson", person);
	}

	@Test(dependsOnMethods = { "testGetPerson" })
	public void testDeletePerson() {
		session.delete("enrty.PersonMapper.deletePerson", 1);
	}
}
