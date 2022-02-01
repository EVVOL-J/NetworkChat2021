package chat;

import chat.controllers.AuthController;
import chat.controllers.MainController;
import chat.controllers.NewChatController;
import chat.controllers.NewUserController;
import chat.network.Network;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;


public class NetworkChat extends Application {
    private Stage primaryStage;
    private Stage authStage;
    private Stage chatStage;
    private Stage userStage;
    private Network network;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage=primaryStage;

        network=new Network(this);
        if (!network.connect()){
            showNetworkError("Ошибка подключения к серверу", "Ошибка сети");
            return;
        }

        initAndShowAuthWindow();


        //initAndShowMainWindow();



    }

    public void initAndShowMainWindow(String username) throws IOException {
        System.out.println(username);
        authStage.close();
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(NetworkChat.class.getResource("/views/main.fxml"));
        Parent root = loader.load();
        this.primaryStage.setTitle("Chat");
        this.primaryStage.setScene(new Scene(root, 600, 400));
        MainController controller=loader.getController();
        controller.setNetwork(network);
        network.read(controller);
        network.getCollectionOfChats().setThisUserName(username);
        controller.setHistory(username);
        controller.setUserInfo("Hello, "+ username);
        primaryStage.show();
    }

    private void initAndShowAuthWindow() throws IOException {
        FXMLLoader authLoader=new FXMLLoader();
        authLoader.setLocation(NetworkChat.class.getResource("/views/auth.fxml"));
        Parent authParent=authLoader.load();
        authStage=new Stage();
        authStage.setTitle("Auth window");
        Scene authScene=new Scene(authParent);
        authStage.setScene(authScene);
        AuthController authController=authLoader.getController();
        authController.setNetwork(network);
        authStage.show();
    }

    public void initAndShowNewUserWindow() throws IOException {
        FXMLLoader userLoader=new FXMLLoader();
        userLoader.setLocation(NetworkChat.class.getResource("/views/newUser.fxml"));
        Parent userParent=userLoader.load();
        userStage=new Stage();
        userStage.setTitle("Create new user window");
        Scene userScene=new Scene(userParent);
        userStage.setScene(userScene);
        NewUserController newUserController=userLoader.getController();
        newUserController.setNetwork(network);
        userStage.show();
    }

    public void initAndShowNewChatWindow() throws IOException {
        FXMLLoader chatLoader=new FXMLLoader();
        chatLoader.setLocation((NetworkChat.class.getResource("/views/newChat.fxml")));
        Parent chatParent=chatLoader.load();
        chatStage=new Stage();
        chatStage.setTitle("New chat");
        Scene chatScene=new Scene(chatParent);
        chatStage.setScene(chatScene);
        NewChatController chatController=chatLoader.getController();
        chatController.setNetwork(network);
        chatController.setListUsers();
        chatStage.show();
    }

    public void closeNewChatWindow(){
        chatStage.close();
    }
    public void closeNewUserWindow() {userStage.close();}


    public static void main(String[] args) {
        launch(args);
    }

    public static void showNetworkError(String errorDetails, String errorTitle) {
        System.out.println(errorDetails+" "+errorTitle);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Network Error");
        alert.setHeaderText(errorTitle);
        alert.setContentText(errorDetails);
        alert.showAndWait();
    }

    public static void showNetworkInfoMessage(String message) {
        System.out.println(message);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("INFO");
        alert.setContentText(message);
        alert.showAndWait();
    }



}
