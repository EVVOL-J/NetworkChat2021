package authData;


import java.util.HashSet;
import java.util.Set;

public class BaseAuthService implements AuthService {
    private final Set<User> userSet;

    public BaseAuthService(){
        this.userSet=new HashSet<>();
        userSet.add(new User("Name1","pass1","login1"));
        userSet.add(new User("Name2","pass2","login2"));
        userSet.add(new User("Name3","pass3","login3"));
    }

    @Override
    public void start() {
        System.out.println("Initialization list of user");
    }

    @Override
    public String getUserNameByLoginAndPassword(String login, String password) {
        if(login!=null&&password!=null)
            for (User user:userSet){
                if(login.equals(user.getLogin())&&password.equals(user.getPassword()))
                    return user.getUserName();
            }
        return null;
    }

    @Override
    public Set<String> getUserNames() {
        Set<String> userNames=new HashSet<>();
        for (User user:userSet){
            userNames.add(user.getUserName());
        }
        return userNames;
    }

    @Override
    public void stop() {
        System.out.println("Close list of users");
    }
}
