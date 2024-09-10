package homeWork;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class ConnectionDB {

    //Создание обьекта
    private static Person person1 = new Person("Иван", "Бабенко", 30);
    private static Person person2 = new Person("Юлия", "Фомина", 19);
    private static Person person3 = new Person("Мария", "Терскова", 23);
    private static Person person4 = new Person("Сергей", "Кучера", 33);
    private static Person person5 = new Person("Артем", "Гостев", 25);



    private static final String URL = "jdbc:mysql://persons.db:3306/";
    private static final String USER = "root";
    private static final String PASSWORD = "password";
    private static Person[] array = new Person[]{person1,person2,person3,person4,person5};

    public static void main(String[] args) {

        //подключение к базе данных
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)){


            //создание базы данных
            createDataBase(connection);

            //использование базы данных
            useDatabase(connection);

            //Создание таблицы
            createTable(connection);
            for (int i = 0; i < array.length; i++) {
                insertData(connection, array[i]);
            }

            //Чтение данных
            Collection<Person> persons = readData(connection);
            for(var person : persons)
                System.out.println(person);

            //удаление данных
//         for(var student : students)
//             deleteData(connection, student.getId());
//         System.out.println("Delete data successfully");


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createDataBase(Connection connection) throws SQLException{
        String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS personsDB;";
        try (PreparedStatement statement = connection.prepareStatement(createDatabaseSQL)) {
            statement.execute();
        }
    }

    private static void useDatabase (Connection connection) throws SQLException {
        String useDatabaseSQL = "USE personsDB";
        try (PreparedStatement statement = connection.prepareStatement(useDatabaseSQL)) {
            statement.execute();
        }
    }

    private static void createTable(Connection connection) throws SQLException{
        String createTableSQL = "CREATE TABLE IF NOT EXISTS persons (id INT AUTO_INCREMENT PRIMARY KEY, firstName VARCHAR(255), lastName VARCHAR(255) ,age INT);";
        try (PreparedStatement statement = connection.prepareStatement(createTableSQL)){
            statement.execute();
        }
    }

    //добавление данных  в таблицу students
    //@param connection - соеденение с бд
    //@param student - Студент
    //@throws SQLException - исключение при выполнении запроса

    private static void insertData(Connection connection, Person person) throws SQLException {
        String insertDataSQL = "INSERT INTO persons (firstName, lastName, age) VALUES (?, ?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(insertDataSQL)) {
            statement.setString(1, person.getFirstName());
            statement.setString(2, person.getLastName());
            statement.setInt(3, person.getAge());
            statement.executeUpdate();
        }
    }

    //чтение данных из таблицы students
    //@param connection Соеденение с БД
    // Коллекция студентов

    private static Collection<Person> readData(Connection connection) throws SQLException {
        ArrayList<Person> personsList = new ArrayList<>();
        String readDataSQL = "SELECT * FROM persons;";
        try (PreparedStatement statement = connection.prepareStatement(readDataSQL)){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                int age = resultSet.getInt("age");
                personsList.add(new Person(id, firstName, lastName, age));
            }
            return personsList;
        }
    }

    //Обновление данных в таблице students по идентификатору
    private static void updateData(Connection connection, Person person) throws SQLException {
        String updateDataSQL = "UPDATE persons SET age=? WHERE id=?;";
        try (PreparedStatement statement = connection.prepareStatement(updateDataSQL)) {
            statement.setInt(1, person.getAge());
            statement.setInt(2, person.getId());
            statement.executeUpdate();
        }
    }

    //удаление записи из таблицы по идентификатору
    private static void deleteData(Connection connection, int id) throws SQLException {
        String deleteDataSQL = "DELETE FROM persons WHERE id=?;";
        try (PreparedStatement statement = connection.prepareStatement(deleteDataSQL)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }
}
