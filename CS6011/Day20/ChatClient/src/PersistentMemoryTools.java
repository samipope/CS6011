import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

// read history messages of certain room from local files
// write new messages to local files to retain history messages
public class PersistentMemoryTools {

    private static String getMemoryFileName(String room)
    {
        return "server/memory/" + room + ".txt";
    }

    private static boolean isRoomRecorded(String room)
    {
        File file = new File(getMemoryFileName(room));
        boolean isRecorded = true;
        try {
            new FileInputStream(file);
        } catch (FileNotFoundException e) {
            isRecorded = false;
        }
        return isRecorded;
    }

    private static void createMemoryFileForRoom(String room) throws IOException {
        File file = new File(getMemoryFileName(room));
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write("");
        fileWriter.close();
    }

    private static ArrayList<String> readMemoryFileForRoom(String room) throws FileNotFoundException {
        File file = new File(getMemoryFileName(room));
        FileInputStream fileInputStream = new FileInputStream(file);
        Scanner scanner = new Scanner(fileInputStream);
        ArrayList<String> memory = new ArrayList<>();
        while (scanner.hasNextLine())
        {
            String line = scanner.nextLine();
            memory.add(line);
        }
        return memory;
    }

    public static ArrayList<String> getMessageHistoryOfRoom(String room) throws IOException {
        boolean isRoomRecorded = isRoomRecorded(room);
        if(!isRoomRecorded)
        {
            createMemoryFileForRoom(room);
        }
        return readMemoryFileForRoom(room);
    }

    public static void addMessageToMemoryFile(Map<String, String> json) throws IOException {
        String room = json.get("room");
        String message = WebSocketTools.stringifyJSON(json) + "\n";
        boolean isRoomRecorded = isRoomRecorded(room);
        if(!isRoomRecorded)
        {
            createMemoryFileForRoom(room);
        }
        File file = new File(getMemoryFileName(room));
        FileWriter fileWriter = new FileWriter(file, true);
        fileWriter.write(message);
        fileWriter.close();
    }
}