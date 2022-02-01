package command;


import command.data.TypeOfCommand;
import command.data.list.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Command<T> implements Serializable {
    private final TypeOfCommand type;
    private final T data;

    public Command(TypeOfCommand type, T data) {
        this.type = type;
        this.data = data;
    }

    public static Command<AuthCommandData> authCommand(String login, String password){
        return new Command<>(TypeOfCommand.AUTH, new AuthCommandData(login,password));
    }

    public static Command<AuthOkCommandData> authOKCommand(Integer userID, String username){
        return new Command<>(TypeOfCommand.AUTH_OK,new AuthOkCommandData(userID, username));
    }

    public static Command<ErrorMassageCommandData> errorMassageCommand(String typeErr, String message){
        return new Command<>(TypeOfCommand.ERROR_MESSAGE,new ErrorMassageCommandData(typeErr,message));
    }

    public static Command<GropeMessageCommandData> gropeMessageCommand(String titleChat, List<String> username){
        return new Command<>(TypeOfCommand.GROPE_MESSAGE,new GropeMessageCommandData(titleChat,username));
    }

    public static Command<InfoMessageCommandData> infoMessageCommand(String message){
        return new Command<>(TypeOfCommand.INFO_MESSAGE, new InfoMessageCommandData(message));
    }

    public static Command<MassageCommandData> massageCommand(Integer chatID, String massage){
        return new Command<>(TypeOfCommand.CHAT_MESSAGE, new MassageCommandData(chatID,massage));
    }
    public static Command<PrivateMassageCommandData> privateMassageCommand(String userName, String massage){
        return new Command<>(TypeOfCommand.PRIVATE_MESSAGE, new  PrivateMassageCommandData(userName,massage));
    }

    public static Command<ChatsOfUserCommandData> chatsOfUserCommand(Map<Integer, String> chats){
        return new Command<>(TypeOfCommand.USER_CHATS, new ChatsOfUserCommandData(chats));
    }
    public static Command<UserSetCommandData> userNamesCommand(Map<Integer,String> userNames){
        return new Command<>(TypeOfCommand.USER_NAMES, new UserSetCommandData(userNames));
    }
    public static Command<CreateNewChatCommandData> createNewChatCommand(String titleChat, Set<Integer> users){
        return new Command<>(TypeOfCommand.CREATE_NEW_CHAT,new CreateNewChatCommandData(titleChat,users));
    }
    public static Command<CreateNewUserCommandData> createNewUserCommand(String name, String login, String password){
        return new Command<>(TypeOfCommand.CREATE_NEW_USER,new CreateNewUserCommandData(name,login,password));
    }


    public TypeOfCommand getType() {
        return type;
    }

    public Object getData() {
        return data;
    }
}
