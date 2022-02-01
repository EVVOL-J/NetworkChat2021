package command.data.list;


import java.io.Serializable;

import java.util.Map;


public class ChatsOfUserCommandData implements Serializable {
    private final Map<Integer, String> mapChats;

    public ChatsOfUserCommandData(Map<Integer, String> mapChats) {
        this.mapChats = mapChats;
    }

    public Map<Integer, String> getMapChats() {
        return mapChats;
    }
}
