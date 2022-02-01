package command.data.list;

import java.io.Serializable;

public class AuthOkCommandData implements Serializable {
    private final String username;
    private final Integer userID;

    public AuthOkCommandData(Integer userID,String username) {
        this.username = username;
        this.userID=userID;
    }

    public Integer getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }
}

