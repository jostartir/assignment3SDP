package notification;

import notification.sender.MessageSender;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class EncryptedNotification extends Notification{
    public EncryptedNotification(MessageSender sender){
        super(sender);
    }

    @Override
    public void send(String recipient, String message){
        String encodedMessage = Base64.getEncoder().encodeToString(message.getBytes(StandardCharsets.UTF_8));

        boolean success = sender.sendMessage(recipient,"Encrypted notification",encodedMessage);

        if (!success){
            System.out.println("Encrypted notification delivery is failed");
        }
    }
}
