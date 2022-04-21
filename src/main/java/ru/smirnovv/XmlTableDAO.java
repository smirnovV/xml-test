package ru.smirnovv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Set;

/**
 * Слой работы с базой данных
 */
public class XmlTableDAO {

    /**
     * SQL-скрипт для создания таблицы
     */
    private static final String createTableSql =
            "CREATE TABLE IF NOT EXISTS xml_test(title varchar(100) NOT NULL UNIQUE)";

    /**
     * SQL-скрипт для вставки данных
     */
    private static final String insertDataSql =
            "insert into xml_test(title) select unnest(?) ON CONFLICT (title) DO NOTHING";

    /**
     * SQL-скрипт получения количества строк в таблице
     */
    private static final String countRowsSql =
            "select count(*) as count from xml_test";

    /**
     * Экземпляр класса
     */
    private static XmlTableDAO xmlTableDAO;

    /**
     * url базы данных
     */
    private final String url;

    /**
     * Пользователь базы данных
     */
    private final String user;

    /**
     * Пароль базы данных
     */
    private final String password;

    /**
     * Конструктор экземпляра класса
     *
     * @param url      url базы данных
     * @param user     пользователь базы данных
     * @param password пароль базы данных
     */
    private XmlTableDAO(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    /**
     * Вернуть экземпляр класса
     *
     * @param url      url базы данных
     * @param user     пользователь базы данных
     * @param password пароль базы данных
     * @return экземпляр класса
     */
    public static XmlTableDAO getInstance(String url, String user, String password) {
        if (Objects.isNull(xmlTableDAO)) {
            xmlTableDAO = new XmlTableDAO(url, user, password);
        }

        return xmlTableDAO;
    }

    /**
     * Инициализировать базу данных
     *
     * @throws ApplicationException возникшая ошибка
     */
    public void initDataBase() throws ApplicationException {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {
            statement.execute(createTableSql);
        } catch (SQLException exception) {
            throw new ApplicationException("Ошибка создания таблицы", exception);
        }
    }

    /**
     * Добавить данные в таблицу
     *
     * @param strings вставляемые данные
     * @throws ApplicationException возникшая ошибка
     */
    public void insertData(Set<String> strings) throws ApplicationException {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(insertDataSql)) {
            statement.setArray(1, connection.createArrayOf("VARCHAR", strings.toArray()));
            statement.execute();
        } catch (SQLException exception) {
            throw new ApplicationException("Ошибка вставки данных в таблицу", exception);
        }
    }

    /**
     * Вернуть количество строк в таблице
     *
     * @return количество строк в таблице
     * @throws ApplicationException возникшая ошибка
     */
    public int retrieveRowCount() throws ApplicationException {
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(countRowsSql)) {
            resultSet.next();
            return resultSet.getInt("count");
        } catch (SQLException exception) {
            throw new ApplicationException("Ошибка получения размера таблицы", exception);
        }
    }

}
