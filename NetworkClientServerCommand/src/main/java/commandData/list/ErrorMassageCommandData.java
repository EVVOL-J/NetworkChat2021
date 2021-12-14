package commandData.list;

import java.io.Serializable;

public class ErrorMassageCommandData implements Serializable {
    private final String typeErr;
    private final String message;

    public ErrorMassageCommandData(String typeErr, String message) {
        this.typeErr = typeErr;
        this.message = message;
    }

    public String getTypeErr() {
        return typeErr;
    }

    public String getMessage() {
        return message;
    }
}
