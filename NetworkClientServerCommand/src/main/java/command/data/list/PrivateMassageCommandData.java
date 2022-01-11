package command.data.list;

import java.io.Serializable;

public class PrivateMassageCommandData implements Serializable {
    private final String username;
    private final String message;

    public PrivateMassageCommandData(String username, String message) {
        this.username = username;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }
}
