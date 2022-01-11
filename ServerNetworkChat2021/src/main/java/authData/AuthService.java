package authData;

import java.util.Set;

;

public interface AuthService {
    void start();
    String getUserNameByLoginAndPassword(String login, String password);
    Set<String> getUserNames();
    void stop();

}
