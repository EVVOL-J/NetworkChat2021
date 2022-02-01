package authData;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class BaseDataService implements DataService {
    private final Map<Integer, User> userSet;

    public BaseDataService() {
        this.userSet = new HashMap<>();
        userSet.put(1, new User("Женя", "pass1", "login1"));
        userSet.put(2, new User("Никита", "pass2", "login2"));
        userSet.put(3, new User("Володя", "pass3", "login3"));
    }

    @Override
    public void start() {
        System.out.println("Initialization list of user");
    }

    @Override
    public Integer getUserIDByLoginAndPassword(String login, String password) {
        if (login != null && password != null) {
            for (Map.Entry<Integer, User> user : userSet.entrySet()) {
                if (user.getValue().getPassword().equals(password) && user.getValue().getLogin().equals(login))
                    return user.getKey();
            }
        }
        return null;
    }

    @Override
    public Map<Integer, String> getUserIDAndNames() {
        Map<Integer, String> userIdNames = new HashMap<>();
        for(Map.Entry<Integer,User> user:userSet.entrySet()){
            userIdNames.put(user.getKey(),user.getValue().getUserName());
        }
        return userIdNames;
    }

    @Override
    public Map<Integer, String> getUserChatIdAndNameByUserID(Integer id) {
        return null;
    }

    @Override
    public Set<Integer> getUsersIDinChatByChatId(Integer chat_id) {
        return null;
    }

    @Override
    public String getUserNameById(Integer id) {
        return null;
    }

    @Override
    public boolean createNewChat(String chatName, Set<Integer> userId) {
     return false;
    }

    @Override
    public boolean checkUserName(String name) {
        return false;
    }

    @Override
    public boolean checkUserLogin(String login) {
        return false;
    }

    @Override
    public boolean createNewUser(String name, String login, String password) {
        return false;
    }


    @Override
    public void stop() {
        System.out.println("Close list of users");
    }
}
