package com.stfalcon.chatkit.sample.features.demo.def;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;
import com.stfalcon.chatkit.sample.R;
import com.stfalcon.chatkit.sample.api.ChatbotWebService;
import com.stfalcon.chatkit.sample.api.model.MessageAPI;
import com.stfalcon.chatkit.sample.api.model.SentMessage;
import com.stfalcon.chatkit.sample.common.data.fixtures.MessagesFixtures;
import com.stfalcon.chatkit.sample.common.data.model.Message;
import com.stfalcon.chatkit.sample.common.data.model.User;
import com.stfalcon.chatkit.sample.features.demo.DemoMessagesActivity;
import com.stfalcon.chatkit.sample.utils.AppUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DefaultMessagesActivity extends DemoMessagesActivity
        implements MessageInput.InputListener,
        MessageInput.AttachmentsListener,
        MessageInput.TypingListener {

    private static String token = null;

    public static void open(Context context, String token) {
        DefaultMessagesActivity.token = token;
        context.startActivity(new Intent(context, DefaultMessagesActivity.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        List<MessageAPI> messages = getMessages(0);
        for (MessageAPI msg : messages) {
            messagesAdapter.addToStart(Message.from(msg), true);
        }


    }


    private MessagesList messagesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_messages);

        this.messagesList = (MessagesList) findViewById(R.id.messagesList);
        initAdapter();

        MessageInput input = (MessageInput) findViewById(R.id.input);
        input.setInputListener(this);
        input.setTypingListener(this);
        input.setAttachmentsListener(this);
    }

    @Override
    public boolean onSubmit(CharSequence input) {
        super.messagesAdapter.addToStart(
                MessagesFixtures.getTextMessage(input.toString()), true);
        sendMessage(input.toString());
        return true;
    }

    private void sendMessage(String message) {
        final Context context = this;
        ChatbotWebService
                .getInstance()
                .getChatbotAPI()
                .sendMessage(token, message)
                .enqueue(new Callback<MessageAPI>() {
                    @Override
                    public void onResponse(Call<MessageAPI> call, Response<MessageAPI> response) {
                        MessageAPI message = response.body();
                        //Toast.makeText(context, message.toString(), Toast.LENGTH_LONG).show();
//                        messagesAdapter.addToStart(Message.from(message), true);

                    }

                    @Override
                    public void onFailure(Call<MessageAPI> call, Throwable t) {

                    }
                });
    }


    private List<MessageAPI> getMessages(int afterId) {
        final List<MessageAPI> result = new ArrayList<>();
        ChatbotWebService
                .getInstance()
                .getChatbotAPI()
                .receiveMessage(token, 0)
                .enqueue(new Callback<List<MessageAPI>>() {
                             @Override
                             public void onResponse(Call<List<MessageAPI>> call, Response<List<MessageAPI>> response) {
                                 List<MessageAPI> messages = response.body();
                                 if (messages != null) {
                                     result.addAll(response.body());
                                 }
                             }

                             @Override
                             public void onFailure(Call<List<MessageAPI>> call, Throwable t) {

                             }
                         }

                );
        return result;
    }

    @Override
    public void onAddAttachments() {
        super.messagesAdapter.addToStart(
                MessagesFixtures.getImageMessage(), true);
    }

    private void initAdapter() {
        super.messagesAdapter = new MessagesListAdapter<>(super.senderId, super.imageLoader);
        super.messagesAdapter.enableSelectionMode(this);
        super.messagesAdapter.setLoadMoreListener(this);
        super.messagesAdapter.registerViewClickListener(R.id.messageUserAvatar,
                new MessagesListAdapter.OnMessageViewClickListener<Message>() {
                    @Override
                    public void onMessageViewClick(View view, Message message) {
                        AppUtils.showToast(DefaultMessagesActivity.this,
                                message.getUser().getName() + " avatar click",
                                false);
                    }
                });
        this.messagesList.setAdapter(super.messagesAdapter);
    }

    @Override
    public void onStartTyping() {
        Log.v("Typing listener", getString(R.string.start_typing_status));
    }

    @Override
    public void onStopTyping() {
        Log.v("Typing listener", getString(R.string.stop_typing_status));
    }
}
