package yevgeniy.melnichuk.example.wicket;

import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

public class ChatSession extends WebSession {
    private static final long serialVersionUID = 1L;

    private String username;

    public ChatSession(Request request) {
        super(request);
    }

    public static ChatSession get() {
        return (ChatSession) WebSession.get();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
