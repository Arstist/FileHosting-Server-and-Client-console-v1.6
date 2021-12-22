package Server;

import Server.Files.Class.FileLooker;

public class Command {
    
    public String checkCommand(String msg) {
        if (msg.equals("/help")) {
            return ("Максимальный вес файла 30MB" + "\n" +
                    "list of available commands:" + "\n" +
                    "/help - показывает список команд" + "\n" +
                    "/ls - показывает список файлов на сервере" + "\n" +
                    "/dd <num> - скачивает файл с севера, где num номер файла, писать без <>" + "\n" +
                    "/ud <Absolute Path> загружает файл на сервер, path Должен быть в таком формате < D:\\User\\Desktop\\fileName.txt >");
        }
        if(msg.equals("/ls")) {
            return FileLooker.getLs();
        }

        return "unknown command";
    }

    public boolean checker(String msg) {
        if(msg.equals("/help")) return true;
        if(msg.equals("/ls")) return true;
        if(msg.equals("/dd")) return true;

        return false;
    }
}
