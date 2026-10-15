package notification;

import notification.sender.MessageSender;

public class UrgentNotification extends Notification{
    private final MessageSender fallbackSender;

    public UrgentNotification(MessageSender sender){
        this(sender, null);
    }
    public UrgentNotification(MessageSender sender, MessageSender fallbackSender){
        super(sender);
        this.sender = sender;
        this.fallbackSender = fallbackSender;
    }

    @Override
    public void send(String recipient, String message){
        MessageSender activeSender = sender;

        if (!activeSender.isAvailable()){
            System.out.println(activeSender.getChannelName() + " is not available");

            if (fallbackSender == null || !fallbackSender.isAvailable()){
                System.out.println("No available fallback sender");
                return;
            }

            activeSender = fallbackSender;

            System.out.println("Using fallback: " + activeSender );
        }

        boolean success = activeSender.sendMessage(recipient,"Urgent Notification","[URGENT]" + message);

        if (!success){
            System.out.println("Urgent notification delivery is failed");
        }




    }
}
