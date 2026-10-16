package notification.sender;

public class PushNotificationSender implements MessageSender{

    private boolean available;

    public PushNotificationSender(boolean available){
        this.available = available;

    }

    @Override
    public boolean sendMessage(String recipient, String title, String body){
        if (!isAvailable()){
            System.out.println("Push is unavailable");
            return false;
        }
        System.out.println("Push -> " + recipient);
        System.out.println("title: " + title);
        System.out.println("body: " + body);

        return true;
    }


    @Override
    public boolean isAvailable(){
        return available;
    };

    @Override
    public String getChannelName(){
        return "Push";
    };

    public void setAvailable(boolean available){
        this.available = available;
    }
}
