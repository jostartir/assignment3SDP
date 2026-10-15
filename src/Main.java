import notification.EncryptedNotification;
import notification.Notification;
import notification.ScheduledBatchNotification;
import notification.UrgentNotification;
import notification.sender.*;

import java.util.List;


public class Main {
    public static void main(String[] args) {
        MessageSender unavailableTelegram = new TelegramMessageSender(false);
        MessageSender fallbackSms = new SMSMessageSender(true);

        Notification urgent = new UrgentNotification(unavailableTelegram,fallbackSms);

        urgent.send("heeloneiugh@gmail.com", "Server is down!");

    }
}