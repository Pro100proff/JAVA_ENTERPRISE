package ru.itis.dao;

import ru.itis.models.Human;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 11.09.2017
 * HumansDaoJdbcImpl
 *
 * @author Sidikov Marsel (First Software Engineering Platform)
 * @version v1.0
 */
public class HumansDaoJdbcImpl implements HumansDao {

    private Connection connection;

    //language=SQL
    private final static String SQL_INSERT_OWNER =
    "INSERT INTO owner(name, age, color) VALUES (?,?,?)";

    //language=SQL
    private final static String SQL_SELECT_OWNER_BY_ID =
            "SELECT * FROM owner WHERE owner.id = ?";

    public HumansDaoJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Human> findAll() {
        return null;
    }

    @Override
    public void save(Human model) {
        try {
            PreparedStatement statement =
                    connection.prepareStatement(SQL_INSERT_OWNER,
                            new String[] {"id"});
            statement.setString(1, model.getName());
            statement.setInt(2, model.getAge());
            statement.setString(3, model.getColor());
            statement.executeUpdate();

            // получаем указатель на результирующие строки
            // результирующие строки - сгенерированный id
            ResultSet resultSet = statement.getGeneratedKeys();
            // одновременно сдвигаем итератор и проверяем есть там че или нет
            if (resultSet.next()) {
                long id = resultSet.getLong(1);
                model.setId(id);
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Human find(Long id) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_OWNER_BY_ID);
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Human.Builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .color(resultSet.getString("color"))
                        .age(resultSet.getInt("age"))
                        .build();
            } else throw new IllegalArgumentException("User not found");
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void update(Human model) {

    }
}
