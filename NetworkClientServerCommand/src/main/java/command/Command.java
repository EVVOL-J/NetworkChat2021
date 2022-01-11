package command;


import command.data.Chat;
import command.data.TypeOfCommand;
import command.data.list.*;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class Command implements Serializable {
    private final TypeOfCommand type;
    private final Object data;

    public Command(TypeOfCommand type, Object data) {
        this.type = type;
        this.data = data;
    }

    public static Command authCommand(String login, String password){
        return new Command(TypeOfCommand.AUTH, new AuthCommandData(login,password));
    }

    public static Command authOKCommand(String username){
        return new Command(TypeOfCommand.AUTH_OK,new AuthOkCommandData(username));
    }

    public static Command errorMassageCommand(String typeErr, String message){
        return new Command(TypeOfCommand.ERROR_MESSAGE,new ErrorMassageCommandData(typeErr,message));
    }

    public static Command gropeMessageCommand(String titleChat, List<String> username){
        return new Command(TypeOfCommand.GROPE_MESSAGE,new GropeMessageCommandData(titleChat,username));
    }

    public static Command infoMessageCommand(String message){
        return new Command(TypeOfCommand.INFO_MESSAGE, new InfoMessageCommandData(message));
    }

    public static Command privateMassageCommand(String username, String massage){
        return new Command(TypeOfCommand.PRIVATE_MESSAGE, new PrivateMassageCommandData(username,massage));
    }

    public static Command chatsOfUserCommand(List<Chat> userChats){
        return new Command(TypeOfCommand.USER_CHATS, new ChatsOfUserCommandData(userChats));
    }
    public static Command userNamesCommand(Set<String> userNames){
        return new Command(TypeOfCommand.USER_SET_MESSAGE, new UserSetCommandData(userNames));
    }

    public TypeOfCommand getType() {
        return type;
    }

    public Object getData() {
        return data;
    }
}
