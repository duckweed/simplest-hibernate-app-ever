package org.adscale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Copyright AdScale GmbH, Germany, 2007
 */
public class SavePerson_Test {

    Session session;

    private SessionFactory sessionFactory;


    @Before
    public void setUp() throws Exception {
        sessionFactory = createSessionFactory("thing", Person.class);
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
    }


    @Test
    public void savePerson() throws Exception {
        String expectedName = "andrew";

        Person person = createPerson(expectedName);
        session.save(person);

        getsOnePersonWithName(expectedName);

        session.getTransaction().rollback();
//        session.close();
    }


    private void getsOnePersonWithName(String expectedName) {
        List list = session.createCriteria(Person.class).list();
//        assertEquals("should find 1 person", 1, list.size());
//        Person savedPerson = (Person) list.get(0);
//        assertEquals("should get name back correctly", expectedName, savedPerson.getName());
    }


    @Test
    public void aPersonHasAnAddress() throws Exception {
        fail();
    }


    private Person createPerson(String expectedName) {
        Person person = new Person();
        person.setName(expectedName);
        return person;
    }


    public static SessionFactory createSessionFactory(String dbName, Class... clazzes) {
        Configuration cfg = new Configuration();
        cfg.setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
        cfg.setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver");
        cfg.setProperty("hibernate.connection.url", "jdbc:hsqldb:mem:" + dbName);
        //        cfg.setProperty("hibernate.connection.url", "jdbc:hsqldb:mem:" + dbName + ";sql.nulls_first=false;sql.convert_trunc=false");
        cfg.setProperty("hibernate.connection.username", "sa");
        cfg.setProperty("hibernate.connection.password", "");
        //        cfg.setProperty("hibernate.connection.pool_size", "1");
        //        cfg.setProperty("hibernate.connection.autocommit", "false");
        //        cfg.setProperty("hibernate.cache.provider_class", "org.hibernate.cache.NoCacheProvider");
        cfg.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        //                cfg.setProperty("hibernate.show_sql", "true");
        cfg.setProperty("hibernate.transaction.factory_class", "org.hibernate.transaction.JDBCTransactionFactory");
        cfg.setProperty("hibernate.current_session_context_class", "thread");
        //        cfg.setProperty("hibernate.cache.use_second_level_cache", "false");

        for (Class clazze : clazzes) {
            cfg.addAnnotatedClass(clazze);
        }

        return cfg.buildSessionFactory();
    }
}
