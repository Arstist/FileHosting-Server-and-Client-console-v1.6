package Server.Lite;

import Client.Lite.Message;

public class FileMessage extends Message {
    private byte[] content;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
