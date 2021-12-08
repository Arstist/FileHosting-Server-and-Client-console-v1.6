package Server.Files.Class;

import java.io.File;
import java.util.*;

public class FileLooker {
    static File folder = new File("D:\\JavaProject\\Echo-Server-Netty\\src\\main\\java\\Server\\Files"); // Дериктория в которой хранятся файлы(сервер)
    static File[] listOfFiles = folder.listFiles();
    public final static Map<String, Integer> filesID_and_Path = new HashMap<>();
    public final static Map<String, Integer> filesNames_and_id = new HashMap<>();


    public FileLooker() {
        checkerFiles();
    }


    public static void checkerFiles() {
        int tmp = 1;
        for (File file : listOfFiles) {
            if (file.isFile()) {
                filesID_and_Path.put(file.getPath(), tmp);
                filesNames_and_id.put(file.getName(), tmp);
                tmp++;
            }
        }
    }

    public static String converterForName(int id) {
        String s = getKeysByValue(filesNames_and_id,id).toString();
        return removeChar(s);
    }



    public static String converter(int id) {
        String s = getKeysByValue(filesID_and_Path,id).toString();
        return removeChar(s);
    }


    public static <T, E> Set<T> getKeysByValue(Map<T, E> map, E value) {
        Set<T> keys = new HashSet<T>();
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                keys.add(entry.getKey());
            }
        }
        return  keys;
    }

    public static String removeChar(String s) {
        return s.replace("[", "").replace("]", "");
    }

    public static String getLs() {
        String newS;
        StringBuilder ls = new StringBuilder();
        for (int i = 1; i <= filesNames_and_id.size(); i++) {
            ls.append(String.format("[%s] %s", i, getKeysByValue(filesNames_and_id, i)) + '\n');
        }
        newS = ls.toString();
        return newS;
    }

}


