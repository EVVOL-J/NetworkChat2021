package authData;

import java.util.Objects;

public class User {
    private String userName;
    private String password;
    private String login;

    public User(String userName, String password, String login) {
        this.userName = userName;
        this.password = password;
        this.login = login;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userName, user.userName) && Objects.equals(password, user.password) && Objects.equals(login, user.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, password, login);
    }
}
