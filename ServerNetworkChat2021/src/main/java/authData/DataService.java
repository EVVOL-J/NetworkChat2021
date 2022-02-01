package authData;

import java.util.Map;
import java.util.Set;


public interface DataService {
    void start();

    Integer getUserIDByLoginAndPassword(String login, String password);

    Map<Integer, String> getUserIDAndNames();
    Map<Integer, String> getUserChatIdAndNameByUserID(Integer id);
    Set<Integer> getUsersIDinChatByChatId(Integer chat_id);
    String getUserNameById(Integer id);
    boolean createNewChat(String chatName, Set<Integer> userId);
    boolean checkUserName(String name);
    boolean checkUserLogin(String login);
    boolean createNewUser(String name, String login, String password);

    void stop();



}
