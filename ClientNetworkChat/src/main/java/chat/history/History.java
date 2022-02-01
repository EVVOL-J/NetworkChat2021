package chat.history;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class History {
    private final String PATH_DIRECTORY="C:\\Users\\MI\\IdeaProjects\\NetworkChat2021\\history\\clients\\";
    private final String name;
    private final int CHECK_SYMBOL;
    private final int READ_NUMBER_LAST_MESSAGES = 100;
    private final String START_MASSAGE="Date";
    private final int APPROXIMATE_AVERAGE_CHARS_IN_STRING=100;


    public History(String name) {
        this.name = name;
        checkFilePath(PATH_DIRECTORY + name);
        CHECK_SYMBOL = READ_NUMBER_LAST_MESSAGES * APPROXIMATE_AVERAGE_CHARS_IN_STRING;
    }


    public boolean writeMessageToChat(String chatName, String message) {
        File file = new File(PATH_DIRECTORY + name + "\\" + chatName + ".txt");
        if (!chekFileExistAndCreate(file)) return false;
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), StandardCharsets.UTF_8));
            bw.write(message);
            bw.close();
        } catch (IOException e) {
            System.out.println("Ошибка записи в файл");
        }

        return true;
    }

    private boolean chekFileExistAndCreate(File file) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Ошибка записи истории");
                return false;
            }
        }
        return file.exists();
    }

    public ArrayList<String> readLastOneHundredMessages(String chatName) {
        ArrayList<String> messages = new ArrayList<>();
        if (chatName==null) return messages;
        File file = new File(PATH_DIRECTORY + name + "\\" + chatName + ".txt");
        if (!chekFileExistAndCreate(file)) return messages;
        try {
            RandomAccessFile reader = new RandomAccessFile(file, "r");
            messages = returnLastMessages(reader);
        } catch (IOException e) {
            System.out.println("Ошибка чтения данных из файла");

        } finally {
            return messages;
        }

    }


    private ArrayList<String> returnLastMessages(RandomAccessFile reader) throws IOException {
        ArrayList<String> messages = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        String line;
        Long length = reader.length();
        int i = 1;
        do {
            messages.clear();
            if (length < i * CHECK_SYMBOL) {
                reader.seek(0);
            } else reader.seek(length - CHECK_SYMBOL * i);

            while ((line = reader.readLine()) != null) {
                line=new String(line.getBytes("ISO-8859-1"), "UTF-8");//перевод строки в UTF-8
                if (line.startsWith(START_MASSAGE)) {
                    if (sb.length() != 0) {
                        if (messages.size() == READ_NUMBER_LAST_MESSAGES - 1) {
                            messages.remove(0);
                        }
                        if (sb.toString().startsWith(START_MASSAGE)) {
                            messages.add(sb.toString());
                        }
                    }
                    sb.delete(0, sb.length());
                }
                sb.append(line + "\n");
            }
            messages.add(sb.toString());
            i++;

        } while (i * CHECK_SYMBOL < length && messages.size() != READ_NUMBER_LAST_MESSAGES);
        return messages;
    }

    private void checkFilePath(String chatPath) {
        File file = new File(chatPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
}
