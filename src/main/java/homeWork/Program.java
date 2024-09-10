package homeWork;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class Program {

    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";
    private static final String PASSWORD = "password";


    public static void main(String[] args) {


        try (SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Person.class).buildSessionFactory()) {

            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);



            //Создание базы данных
            createDataBase(connection);

            useDatabase(connection);

            createTable(connection);

            //Создание сессии
            Session session = sessionFactory.getCurrentSession();


            //Создание транзакции
            session.beginTransaction();

            //Создание 5 обьектов Person
            Person person1 = new Person("Иван", "Бабенко", 30);
            Person person2 = new Person("Юлия", "Фомина", 19);
            Person person3 = new Person("Мария", "Терскова", 23);
            Person person4 = new Person("Сергей", "Кучера", 33);
            Person person5 = new Person("Артем", "Гостев", 25);
            Person[] array = new Person[]{person1, person2, person3, person4, person5};
            for (int i = 0; i < array.length; i++) {
                insertData(connection, array[i]);
            }

//            //Закоммитили изменения
//            session.getTransaction().commit();
//
//            System.out.println("Все 5 Person созданы и добавлены");
//            System.out.println("Попробуем найти всех Person в нашей БД");
//
//            //Чтение обьектов из базы данных
//            Person retrievedPerson1 = session.get(Person.class, person1.getId());
//            System.out.println("Получили person: " + retrievedPerson1);
//
//            Person retrievedPerson2 = session.get(Person.class, person2.getId());
//            System.out.println("Получили person: " + retrievedPerson2);
//
//            Person retrievedPerson3 = session.get(Person.class, person3.getId());
//            System.out.println("Получили person: " + retrievedPerson3);
//
//            Person retrievedPerson4 = session.get(Person.class, person4.getId());
//            System.out.println("Получили person: " + retrievedPerson4);
//
//            Person retrievedPerson5 = session.get(Person.class, person5.getId());
//            System.out.println("Получили person: " + retrievedPerson5);
//
//            System.out.println("Теперь попробуем изменить им возраст (сделаем -1 от текущего)");
//
//            //Обновление обьектов
//            retrievedPerson1.updateAge();
//            session.update(retrievedPerson1);
//            retrievedPerson2.updateAge();
//            session.update(retrievedPerson2);
//            retrievedPerson3.updateAge();
//            session.update(retrievedPerson3);
//            retrievedPerson4.updateAge();
//            session.update(retrievedPerson4);
//            retrievedPerson5.updateAge();
//            session.update(retrievedPerson5);
//
//            System.out.println("Изменили всем Person возраст");
//            System.out.println("Выведем еще раз всех Person");
//
//            System.out.println("Получили person: " + retrievedPerson1);
//            System.out.println("Получили person: " + retrievedPerson2);
//            System.out.println("Получили person: " + retrievedPerson3);
//            System.out.println("Получили person: " + retrievedPerson4);
//            System.out.println("Получили person: " + retrievedPerson5);
//
            //Закоммитили изменения
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private static void createDataBase(Connection connection) throws SQLException {
        String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS personsDB;";
        try (PreparedStatement statement = connection.prepareStatement(createDatabaseSQL)) {
            statement.execute();
        }
    }

    private static void createTable(Connection connection) throws SQLException{
        String createTableSQL = "CREATE TABLE IF NOT EXISTS persons (id INT AUTO_INCREMENT PRIMARY KEY, firstName VARCHAR(255), lastName VARCHAR(255), age INT);";
        try (PreparedStatement statement = connection.prepareStatement(createTableSQL)){
            statement.execute();
        }
    }

    private static void useDatabase (Connection connection) throws SQLException {
        String useDatabaseSQL = "USE personsDB";
        try (PreparedStatement statement = connection.prepareStatement(useDatabaseSQL)) {
            statement.execute();
        }
    }

    private static void insertData(Connection connection, Person person) throws SQLException {
        String insertDataSQL = "INSERT INTO persons (firstName, lastName, age) VALUES (?, ?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(insertDataSQL)) {
            statement.setString(1, person.getFirstName());
            statement.setString(2, person.getLastName());
            statement.setInt(3, person.getAge());
            statement.executeUpdate();
        }
    }

}

