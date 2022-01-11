package command.data.list;

import java.io.Serializable;

public class InfoMessageCommandData implements Serializable {
    private final String message;

    public InfoMessageCommandData(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
