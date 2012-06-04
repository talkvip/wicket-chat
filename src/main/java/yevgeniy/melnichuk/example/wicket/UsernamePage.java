package yevgeniy.melnichuk.example.wicket;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;

public class UsernamePage extends WebPage {
    private static final long serialVersionUID = 1L;

    @Override
    protected void onConfigure() {
        super.onConfigure();

        final ChatSession session = ChatSession.get();
        String username = session.getUsername();
        if (username != null) {
            throw new RestartResponseAtInterceptPageException(ChatPage.class);
        }
    }

    public UsernamePage() {
        final Model<String> usernameModel = new Model<String>("");
        TextField<String> textField = new TextField<String>("username", usernameModel);
        Button button = new Button("submit") {
            private static final long serialVersionUID = 1L;

            @Override
            public void onSubmit() {
                super.onSubmit();

                String username = usernameModel.getObject();
                ChatSession.get().setUsername(username);
            }
        };

        Form<String> form = new Form<String>("form");
        form.add(textField);
        form.add(button);

        add(form);
    }
}
