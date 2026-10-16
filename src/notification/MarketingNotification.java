package notification;

import notification.sender.MessageSender;



public class MarketingNotification extends Notification{
    public MarketingNotification(MessageSender sender){
        super(sender);
    }

    @Override
    public void send(String recipient, String message){

        boolean success = sender.sendMessage(recipient,"Special offer","[PROMO]" + message);

        if (!success){
            System.out.println("Marketing notification delivery is failed");
        }




    }
}
