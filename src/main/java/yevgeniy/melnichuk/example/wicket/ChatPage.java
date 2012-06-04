package yevgeniy.melnichuk.example.wicket;

import java.util.LinkedList;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.time.Duration;

public class ChatPage extends WebPage {
    private static final long serialVersionUID = 1L;

    private static final int MAX_MESSAGES = 50;
    static private final LinkedList<ChatMessage> messages = new LinkedList<ChatMessage>();

    private MarkupContainer messagesContainer;

    public ChatPage() {
        add(buildUsernameLabel(), buildMessagesContainer(), buildForm());
    }

    @Override
    protected void onConfigure() {
        super.onConfigure();

        String username = ChatSession.get().getUsername();
        if (username == null) {
            throw new RestartResponseAtInterceptPageException(UsernamePage.class);
        }
    }

    private Component buildForm() {
        final TextField<String> textField = new TextField<String>("message", new Model<String>());
        textField.setOutputMarkupId(true);

        Component submit = new AjaxSubmitLink("submit") {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                String username = ChatSession.get().getUsername();
                String text = textField.getModelObject();

                ChatMessage chatMessage = new ChatMessage(username, text);

                synchronized (messages) {
                    if (messages.size() >= MAX_MESSAGES) {
                        messages.removeLast();
                    }

                    messages.addFirst(chatMessage);
                }

                textField.setModelObject("");
                target.add(messagesContainer, textField);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                throw new UnsupportedOperationException("nor errors allowed :)");
            }
        };

        return new Form<String>("form").add(textField, submit);
    }

    private Component buildUsernameLabel() {
        String username = ChatSession.get().getUsername();
        Model<String> model = new Model<String>(username);
        Label usernameLabel = new Label("username", model);
        return usernameLabel;
    }

    private Component buildMessagesContainer() {
        messagesContainer = new WebMarkupContainer("messages");

        final ListView<ChatMessage> listView = new ListView<ChatMessage>("message", messages) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void populateItem(ListItem<ChatMessage> item) {
                this.modelChanging();

                ChatMessage message = item.getModelObject();

                Label from = new Label("from", new PropertyModel<String>(message, "from"));
                item.add(from);

                Label text = new Label("text", new PropertyModel<String>(message, "text"));
                item.add(text);
            }
        };

        messagesContainer.setOutputMarkupId(true);
        messagesContainer.add(listView);

        AjaxSelfUpdatingTimerBehavior ajaxBehavior = new AjaxSelfUpdatingTimerBehavior(Duration.seconds(5));
        messagesContainer.add(ajaxBehavior);

        return messagesContainer;
    }
}
