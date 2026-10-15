package notification.sender;

public class SMSMessageSender implements MessageSender{
    private boolean available;

    public SMSMessageSender(boolean available){
        this.available = available;

    }

    @Override
    public boolean sendMessage(String recipient, String title, String body){
        if (!isAvailable()){
            System.out.println("SMS is unavailable");
            return false;
        }
        if (body.length() > 160){
            System.out.println("Sms exceeds 160 characters");
            return false;
        }
        System.out.println("SMS -> " + recipient);
        System.out.println(body);

        return true;
    }


    @Override
    public boolean isAvailable(){
        return available;
    };

    @Override
    public String getChannelName(){
        return "SMS";
    };

    public void setAvailable(boolean available){
        this.available = available;
    }
}
