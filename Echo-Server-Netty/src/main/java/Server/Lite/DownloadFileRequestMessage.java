package Server.Lite;

import Client.Lite.Message;

public class DownloadFileRequestMessage extends Message {
    private int id;
    private int name;

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
