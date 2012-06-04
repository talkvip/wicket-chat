package yevgeniy.melnichuk.example.wicket;

import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;

public class ChatApplication extends WebApplication {

    @Override
    protected void init() {
        super.init();

        mountPage("/username", UsernamePage.class);
        mountPage("/chat", ChatPage.class);
    }

    @Override
    public Session newSession(Request request, Response response) {
        return new ChatSession(request);
    }

    @Override
    public Class<? extends Page> getHomePage() {
        return ChatPage.class;
    }
}
