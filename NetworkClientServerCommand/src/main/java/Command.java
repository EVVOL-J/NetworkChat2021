import commandData.TypeOfCommand;
import commandData.list.*;

import java.io.Serializable;
import java.util.List;

public class Command implements Serializable {
    private TypeOfCommand type;
    private Object data;

    public Command(TypeOfCommand type, Object data) {
        this.type = type;
        this.data = data;
    }

    public Command authCommand(String login, String password){
        return new Command(TypeOfCommand.AUTH, new AuthCommandData(login,password));
    }

    public Command authOKCommand(String username){
        return new Command(TypeOfCommand.AUTH_OK,new AuthOkCommandData(username));
    }

    public Command errorMassageCommand(String typeErr, String message){
        return new Command(TypeOfCommand.ERROR_MESSAGE,new ErrorMassageCommandData(typeErr,message));
    }

    public Command gropeMessageCommand(String titleChat, List<String> username){
        return new Command(TypeOfCommand.GROPE_MESSAGE,new GropeMessageCommandData(titleChat,username));
    }

    public Command infoMessageCommand(String message){
        return new Command(TypeOfCommand.INFO_MESSAGE, new InfoMessageCommandData(message));
    }

    public Command privateMassageCommand(String username, String massage){
        return new Command(TypeOfCommand.PRIVATE_MESSAGE, new PrivateMassageCommandData(username,massage));
    }

}
