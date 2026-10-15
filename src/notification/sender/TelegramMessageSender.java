package notification.sender;

public class TelegramMessageSender implements MessageSender{

    private boolean available;

    public TelegramMessageSender(boolean available){
        this.available = available;

    }

    @Override
    public boolean sendMessage(String recipient, String title, String body){
        if (!isAvailable()){
            System.out.println("Telegram is unavailable");
            return false;
        }
        System.out.println("Telegram -> " + recipient);
        System.out.println("<b>" + title + "</b>");
        System.out.println(body);

        return true;
    }


    @Override
    public boolean isAvailable(){
        return available;
    };

    @Override
    public String getChannelName(){
        return "Telegram";
    };

    public void setAvailable(boolean available){
        this.available = available;
    }
}
