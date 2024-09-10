package homeWork;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class Program {

    public static void main(String[] args) {

        try (SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Person.class).buildSessionFactory()){

            //Создание сессии
            Session session = sessionFactory.getCurrentSession();

            //Начало транзакции
            session.beginTransaction();

            //Создание нового обьекта
            Person personNew = new Person("Богдан", "Савушкин", 14);
            session.save(personNew);

            //Чтение обьектов из базы данных
            Person retrievedPerson = session.get(Person.class, personNew.getId());
            System.out.println("Получили person: " + retrievedPerson);
            System.out.println("Попробуем изменить новому person возраст");


            //Обновление обьектов
            retrievedPerson.updateAge();
            session.update(retrievedPerson);


            System.out.println("Изменили новому Person возраст");
            System.out.println("Выведем еще раз Person");

            System.out.println("Получили person: " + retrievedPerson);


            //Закоммитили изменения
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

