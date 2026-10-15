import notification.EncryptedNotification;
import notification.Notification;
import notification.ScheduledBatchNotification;
import notification.UrgentNotification;
import notification.sender.*;

import java.util.List;


public class Main {
    public static void main(String[] args) {
        MessageSender telegram = new TelegramMessageSender(true);

        MessageSender email = new EmailMessageSender(
                true,
                "Notification Gateway"
        );

        Notification notification = new UrgentNotification(telegram);

        notification.send("user123", "Server is down!");

        notification.setSender(email);

        notification.send("user@example.com", "Server is down!");

    }
}