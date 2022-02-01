package authData;

import dataBase.DataBaseController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

public class DataBaseDataService implements DataService {
    private final DataBaseController dbc;
    private final Logger logger= LogManager.getLogger(DataBaseDataService.class);

    public DataBaseDataService(){
        dbc=new DataBaseController();
    }

    @Override
    public void start() {
        try {
            dbc.connection();
            logger.info("База данных успешно подключена");
        } catch (SQLException e) {
            logError("Ошибка открытия БД");
        }
    }

    @Override
    public Integer getUserIDByLoginAndPassword(String login, String password) {
        try {
            return dbc.getUserNameByLoginAndPassword(login,password);
        } catch (SQLException e) {
            logError("аутентификация");
            return null;
        }
    }



    @Override
    public Map<Integer, String> getUserIDAndNames() {
        try {
            return dbc.getUserIDAndNames();
        } catch (SQLException e) {
            logError("список юзеров");
            return null;
        }
    }

    @Override
    public Map<Integer, String> getUserChatIdAndNameByUserID(Integer id) {
        try {
            return dbc.getUserChatIdAndNameByUserID(id);
        } catch (SQLException e) {
            logError("список чатов");
            return null;
        }
    }

    @Override
    public Set<Integer> getUsersIDinChatByChatId(Integer chat_id) {
        try {
            return dbc.getUsersIDinChatByChatId(chat_id);
        } catch (SQLException e) {
            logError("список юзеров в чате");
            return null;
        }
    }

    @Override
    public String getUserNameById(Integer id) {
        try {
            return dbc.getUserNameById(id);
        } catch (SQLException e) {
            logError("имя пользователя");
            return null;
        }
    }

    @Override
    public boolean createNewChat(String chatName, Set<Integer> userId) {
        try {
            return dbc.createNewChat(chatName, userId);
        } catch (SQLException e) {
            logError("создание нового чата");
            return false;
        }
    }

    @Override
    public boolean checkUserName(String name) {
        try {
            return dbc.checkUserName(name);
        } catch (SQLException e) {
            logError("проверка Имени пользователя");
            return true;
        }
    }

    @Override
    public boolean checkUserLogin(String login) {
        try {
            return dbc.checkUserLogin(login);
        } catch (SQLException e) {
            logError("проверка Логина пользователя");
            return true;
        }
    }

    @Override
    public boolean createNewUser(String name, String login, String password) {
        try {
            return dbc.createNewAccount(name,login,password);
        } catch (SQLException e) {
            logError("создание нового пользователя");
            return false;
        }
    }

    @Override
    public void stop() {
        dbc.disconnect();
    }

    private void logError(String detail) {
        logger.error("Ошибка взаимодействия с БД: "+detail);
       // System.out.println("Ошибка взаимодействия с БД: "+detail);
    }


}
