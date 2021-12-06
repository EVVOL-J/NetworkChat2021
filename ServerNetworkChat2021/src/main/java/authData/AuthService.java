package authData;

public interface AuthService {
    void start();
    String getUserNameByLoginAndPassword(String login, String password);
    void stop();

}
