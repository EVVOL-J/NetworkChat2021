package command.data.list;

import java.io.Serializable;

public class PrivateMassageCommandData implements Serializable {
    private final String name;
    private final String message;

    public PrivateMassageCommandData(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }
}
