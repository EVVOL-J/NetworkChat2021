package command.data;

import java.io.Serializable;
import java.util.Objects;

public class Chat implements Serializable {
    private String userName;
    private boolean onlineStatus;


    public Chat(String userName, boolean onlineStatus) {
        this.userName = userName;
        this.onlineStatus=onlineStatus;

    }

    public String getUserName() {
        return userName;
    }

    public boolean isOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(boolean onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chat user = (Chat) o;
        return Objects.equals(userName, user.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName);
    }

    @Override
    public String toString() {
        if(onlineStatus) return userName +" Online";
        else return userName;
    }
}
