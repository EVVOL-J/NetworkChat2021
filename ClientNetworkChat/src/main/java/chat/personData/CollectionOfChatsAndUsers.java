package chat.personData;

import chat.history.History;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;


public class CollectionOfChatsAndUsers {
    private Map<Integer, String> chatsList;
    private Map<Integer, String> userNames;


    private String thisUserName;


    public CollectionOfChatsAndUsers() {
        this.chatsList = new HashMap<>();
        this.userNames = new HashMap<>();


    }

    public void insertChats(Map<Integer, String> chats) {
        chatsList.clear();
        chatsList.putAll(chats);
    }

    public void setUserNames(Map<Integer, String> userNames) {
        this.userNames.clear();
        this.userNames.putAll(userNames);
    }


    public ObservableList<String> getUserNamesList() {
        ObservableList<String> userList = FXCollections.observableArrayList();
        for (Map.Entry<Integer, String> user : userNames.entrySet()) {
            if (!user.getValue().equals(thisUserName))
                userList.add(user.getValue());

        }
        return userList;
    }

    public ObservableList<String> getChatList() {
        ObservableList<String> list = FXCollections.observableArrayList();
        for (Map.Entry<Integer, String> chat : chatsList.entrySet()) {
            list.add(chat.getValue());
            System.out.println(chat.getValue());
        }
        return list;
    }

    public String getChatNameById(Integer id){
        return chatsList.get(id);
    }


    public Set<Integer> getUsersIdSet(ObservableList<String> items) {
        Set<Integer> userId = new HashSet<>();
        for (Map.Entry<Integer, String> user : userNames.entrySet()) {
            for (String item : items) {
                if (item.equals(user.getValue())) {
                    userId.add(user.getKey());
                }
            }
        }
        System.out.println("List id" + userId.size());
        return userId;
    }

    public void setThisUserName(String thisUserName) {
        this.thisUserName = thisUserName;
    }

    public Integer getChatIDByTitle(String title){
        for(Map.Entry<Integer,String> chat : chatsList.entrySet()){
            if(title.equals(chat.getValue())) return chat.getKey();
        }
        return null;
    }



    public String getThisUserName() {
        return thisUserName;
    }
}

