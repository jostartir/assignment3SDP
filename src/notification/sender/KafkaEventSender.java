package notification.sender;

public class KafkaEventSender implements MessageSender{
    private boolean available;
    private final String topic;

    public KafkaEventSender(boolean available, String topic){
        this.available = available;
        this.topic = topic;
    }

    @Override
    public boolean sendMessage(String recipient, String title, String body){
        if (!isAvailable()){
            System.out.println("Kafka is unavailable");
            return false;
        }
        System.out.println("Kafka event published");
        System.out.println("Topic:" + topic);
        System.out.println("Key: " + recipient);
        System.out.println("Title: " + title);
        System.out.println("Body: " + body);

        return true;

    }

    @Override
    public boolean isAvailable(){
        return available;
    };

    @Override
    public String getChannelName(){
        return "Kafka";
    };

    public void setAvailable(boolean available){
        this.available = available;
    }
}
