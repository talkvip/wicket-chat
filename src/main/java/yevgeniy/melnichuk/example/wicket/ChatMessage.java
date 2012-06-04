package yevgeniy.melnichuk.example.wicket;

import java.io.Serializable;

public class ChatMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    private String from;
    private String text;

    public ChatMessage(String from, String text) {
        super();
        this.from = from;
        this.text = text;
    }

    public String getFrom() {
        return from;
    }

    public String getText() {
        return text;
    }

}
