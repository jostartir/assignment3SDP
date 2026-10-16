import notification.*;
import notification.sender.*;

import java.util.List;


public class Main {
    public static void main(String[] args) {
        System.out.println("Mandatory Demo");
        System.out.println("----------------------");

        MessageSender telegram = new TelegramMessageSender(true);
        Notification urgent = new UrgentNotification(telegram);

        urgent.send("user999","Server is down");

        System.out.println("--------------------");

        MessageSender email = new EmailMessageSender(true,"From aliusheka");
        Notification encrypted = new EncryptedNotification(email);

        encrypted.send("userka","hello broski!");

        System.out.println("--------------------");

        MessageSender sms = new SMSMessageSender(true);
        ScheduledBatchNotification scheduled = new ScheduledBatchNotification(sms, List.of("jostar","doe","cartlo"));

        scheduled.sendBatch("hello my brothers!");

        System.out.println("----------------");

        urgent.send("user999","hello>??");

        urgent.setSender(email);

        urgent.send("user875","hello!!!");

        System.out.println("-------------------");

        MessageSender unavailable = new TelegramMessageSender(false);
        UrgentNotification urgentNotification = new UrgentNotification(unavailable,sms);

        urgentNotification.send("user989","Server is down!");

        System.out.println("--------------------");

        MessageSender push = new PushNotificationSender(true);
        Notification marketing = new MarketingNotification(push);

        marketing.send("user678","We have no promo action!!!");

    }
}