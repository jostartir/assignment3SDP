package notification;

import notification.sender.MessageSender;

import java.util.Collection;
import java.util.List;

public class ScheduledBatchNotification extends Notification{
    private List<String> recipients;

    public ScheduledBatchNotification(MessageSender sender, Collection<String> recipients){
        super(sender);
        this.recipients = List.copyOf(recipients);
    }

    @Override
    public void send(String recipient, String message){

        boolean success = sender.sendMessage(recipient,"Batch notification",message);

        if (!success){
            System.out.println("Delivery failed for:" + recipient);
        }



    }

    public void sendBatch(String message){
        int successful = 0;
        int failed = 0;

        for (int i = 0; i < recipients.size(); i++){
            boolean success = sender.sendMessage(recipients.get(i),"Batch notification", message);
            if (success){
                successful++;
            }else{
                failed++;
            }


        }

        System.out.println("Total: " + recipients.size());
        System.out.println("Successful: " + successful);
        System.out.println("Failed: " + failed);
    }
}
