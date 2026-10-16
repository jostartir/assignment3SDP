import notification.*;
import notification.sender.*;

import java.util.List;


public class Main {
    public static void main(String[] args) {
        MessageSender push = new PushNotificationSender(true);

        Notification market = new MarketingNotification(push);

        market.send("Hello","HELLOO MY FRIENDOOOO");

    }
}