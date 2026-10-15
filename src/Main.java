import notification.EncryptedNotification;
import notification.Notification;
import notification.ScheduledBatchNotification;
import notification.UrgentNotification;
import notification.sender.*;

import java.util.List;


public class Main {
    public static void main(String[] args) {
        MessageSender telegram = new TelegramMessageSender(false);
        MessageSender sms = new SMSMessageSender(true);

        Notification notification = new UrgentNotification(telegram, sms);

        notification.send("+77001234567", "Server is down!");

        System.out.println();
        System.out.println("-------------");
        System.out.println();
        MessageSender email = new EmailMessageSender(true, "Notification Gateway");

        Notification encrypted = new EncryptedNotification(email);

        encrypted.send("user@example.com", "Hello");

        System.out.println();
        System.out.println("---------------");
        System.out.println();

        MessageSender smss = new SMSMessageSender(true);

        ScheduledBatchNotification batch = new ScheduledBatchNotification(
                smss,
                List.of("+77001111111", "+77002222222", "+77003333333")
        );

        batch.sendBatch("Reminder: meeting at 15:00");
    }
}