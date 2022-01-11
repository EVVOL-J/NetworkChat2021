package command.data.list;

import java.io.Serializable;
import java.util.Set;

public class UserSetCommandData implements Serializable {
    Set<String> userNames;

    public UserSetCommandData(Set<String> userNames) {
        this.userNames = userNames;
    }

    public Set<String> getUserNames() {
        return userNames;
    }
}
