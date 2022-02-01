package dataBase;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DataBaseController {
    private Connection connection;
    private Statement statement;
    private PreparedStatement ps;
    private ResultSet rs;
    private final String URL = "jdbc:mysql://localhost:3306/chatdb";
    private final String USERNAME = "root";
    private final String PASSWORD = "L7wrKOXp";
    private final Logger logger= LogManager.getLogger(DataBaseController.class);

    public void connection() throws SQLException {
        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        logger.info("Successful connection to the database");
        statement = connection.createStatement();
    }

    public Integer getUserNameByLoginAndPassword(String login, String password) throws SQLException {
        Integer id = null;
        ps = connection.prepareStatement("SELECT users.id FROM users " +
                "LEFT JOIN `password` ON users.id_password=`password`.id " +
                "WHERE login=? AND `password`=?");
        ps.setString(1, login);
        ps.setString(2, password);
        rs = ps.executeQuery();
        if (rs.next()) id = rs.getInt(1);
        return id;
    }


    public Map<Integer, String> getUserIDAndNames() throws SQLException {
        Map<Integer, String> map = new HashMap<>();
        rs = statement.executeQuery("SELECT id, `name` FROM `users`");
        while (rs.next()) {
            map.put(rs.getInt(1), rs.getString(2));
        }
        return map;

    }

    public Map<Integer, String> getUserChatIdAndNameByUserID(Integer id) throws SQLException {
        Map<Integer, String> map = new HashMap<>();
        ps = connection.prepareStatement("SELECT chat_name.id, chat_name FROM user_id_chat_id " +
                "left join chat_name ON chat_name.id=user_id_chat_id.chat_id " +
                "WHERE user_id=?");
        ps.setInt(1, id);
        rs = ps.executeQuery();
        while (rs.next()) {
            map.put(rs.getInt(1), rs.getString(2));
        }
        return map;
    }

    public Set<Integer> getUsersIDinChatByChatId(Integer chat_id) throws SQLException {
        Set<Integer> set = new HashSet<>();
        ps = connection.prepareStatement("SELECT users.id FROM user_id_chat_id " +
                "left join users ON users.id=user_id_chat_id.user_id " +
                "WHERE chat_id=?");
        ps.setInt(1, chat_id);
        rs = ps.executeQuery();
        while (rs.next()) {
            set.add(rs.getInt(1));
        }
        return set;
    }

    public String getUserNameById(Integer id) throws SQLException {
        String name = null;
        ps = connection.prepareStatement("SELECT users.`name` FROM users WHERE id=?");
        ps.setInt(1, id);
        rs = ps.executeQuery();
        if (rs.next())
            name = rs.getString(1);
        return name;
    }

    public boolean createNewChat(String chatName, Set<Integer> userId) throws SQLException {
        Integer id = null;
        connection.setAutoCommit(false);
        try {
            statement.execute("INSERT INTO chat_name(chat_name) VALUE ('" + chatName + "')");
            rs = statement.executeQuery("SELECT MAX(id) FROM chat_name WHERE chat_name='" + chatName + "'");
            if (rs.next()) id = rs.getInt(1);
            if (id != null) {
                ps = connection.prepareStatement("INSERT INTO user_id_chat_id VALUES (?,?)");
                for (Integer i : userId) {
                    ps.setInt(1, i);
                    ps.setInt(2, id);
                    ps.addBatch();
                }
                ps.executeBatch();
                connection.commit();
            }
            return true;
        } catch (SQLException e) {
            connection.rollback();
            return false;
        }

    }

    public boolean checkUserName(String userName) throws SQLException {
        rs = statement.executeQuery("SELECT `name` FROM users WHERE `name`='" + userName + "'");
        return rs.next();
    }

    public boolean checkUserLogin(String login) throws SQLException {
        rs = statement.executeQuery("SELECT `login` FROM users WHERE `login`='" + login + "'");
        return rs.next();
    }

    public boolean createNewAccount(String name, String login, String password) throws SQLException {
        connection.setAutoCommit(false);
        Integer passId ;
        Integer chatMeID;
        Integer userID;
        try {
            statement.execute("INSERT INTO `password` (`password`) VALUES ('" + password + "')");

            rs = statement.executeQuery("SELECT MAX(id) FROM `password` WHERE `password`='" + password + "'");
            if (rs.next()) passId = rs.getInt(1);
            else return false;

            statement.execute("INSERT INTO users (`name`,`login`,id_password) VALUES ('"+name+"','"+login+"', "+passId+")");
            statement.execute("INSERT INTO chat_name (`chat_name`) VALUES ('My')");

            rs = statement.executeQuery("SELECT MAX(id) FROM `users` WHERE `name`='" + name + "'");
            if (rs.next()) userID = rs.getInt(1);
            else return false;

            rs = statement.executeQuery("SELECT MAX(id) FROM `chat_name` WHERE `chat_name`='My'");
            if (rs.next()) chatMeID = rs.getInt(1);
            else return false;

            statement.execute("INSERT INTO user_id_chat_id VALUES ('"+userID+"','"+chatMeID+"')");

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            return false;
        }
        return true;
    }


    public void disconnect() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
                logger.info("database close");
            }
        } catch (SQLException e) {
            logger.error("Error to close database");

        }
    }


}
