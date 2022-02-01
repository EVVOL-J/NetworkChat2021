package command.data.list;

import java.io.Serializable;
import java.util.Map;

public class UserSetCommandData implements Serializable {
    Map<Integer,String> userNames;

    public UserSetCommandData(Map<Integer,String> userNames) {
        this.userNames = userNames;
    }

    public Map<Integer,String> getUserNames() {

        return userNames;
    }
}
