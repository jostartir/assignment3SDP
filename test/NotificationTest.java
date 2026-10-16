import notification.*;
import notification.sender.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static java.awt.SystemColor.text;
import static org.junit.jupiter.api.Assertions.*;

public class NotificationTest {

    @Test
    @DisplayName("successful delivery")
    void successfulDelivery(){
        MessageSender telegram = new TelegramMessageSender(true);
        boolean result = telegram.sendMessage("user212","Mylobs","hello this is weird word means nothing");
        assertTrue(result);
    }

    @Test
    @DisplayName("unavailable provider")
    void unavailableProvider(){
        MessageSender telegram = new TelegramMessageSender(false);
        boolean result = telegram.sendMessage("user212","Fail","Hey bro! I HATE YOU! but this is false:)) ");
        assertFalse(result);
    }

    @Test
    @DisplayName("fallback")
    void fallBack(){
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        PrintStream originalOut = System.out;

        try{
            System.setOut(new PrintStream(output));

            MessageSender teleg = new TelegramMessageSender(false);
            MessageSender smse = new SMSMessageSender(true);

            UrgentNotification urgentNotification = new UrgentNotification(teleg,smse);

            urgentNotification.send("+77001234567","hello!!!");

        }finally {
            System.setOut(originalOut);
        }

        assertTrue(output.toString().contains("SMS -> +77001234567"));
    }

    @Test
    @DisplayName("SMS length restriction")
    void smsLength(){
        MessageSender sms = new SMSMessageSender(true);
        String message = "A".repeat(160);

        boolean result = sms.sendMessage("user99","",message);
        assertTrue(result);
    }

    @Test
    @DisplayName("Sms should reject 161 simbols")
    void smsShouldReject161Characters(){
        MessageSender sms = new SMSMessageSender(true);
        String message = "A".repeat(161);

        boolean result = sms.sendMessage("user89","",message);
        assertFalse(result);
    }

    @Test
    @DisplayName("encrypted message")
    void encryptedMessage(){
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        try{
            System.setOut(new PrintStream(output));
            MessageSender gateAway = new EmailMessageSender(true,"from alisherka");
            EncryptedNotification encrypted = new EncryptedNotification(gateAway);
            encrypted.send("neighbour","Hello");
        }finally {
            System.setOut(originalOut);
        }
        assertTrue(output.toString().contains("SGVsbG8="));
        assertFalse(output.toString().contains("Hello"));


    }

    @Test
    @DisplayName("batch notification")
    void batchNotification(){
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        try{
            System.setOut(new PrintStream(output));
            MessageSender smse = new SMSMessageSender(true);
            ScheduledBatchNotification scheduled = new ScheduledBatchNotification(smse, List.of("+77001111111", "+77002222222", "+77003333333"));
            scheduled.sendBatch("hello!!!");
        }finally {
            System.setOut(originalOut);
        }

        String text = output.toString();

        assertTrue(text.contains("SMS -> +77001111111"));
        assertTrue(text.contains("SMS -> +77002222222"));
        assertTrue(text.contains("SMS -> +77003333333"));

        assertTrue(text.contains("Total: 3"));
        assertTrue(text.contains("Successful: 3"));
        assertTrue(text.contains("Failed: 0"));

    }

    @Test
    @DisplayName("runtime sender switching")
    void shouldSwitchSenderAtRuntime() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        try {
            System.setOut(new PrintStream(output));

            MessageSender telegram = new TelegramMessageSender(true);
            MessageSender email = new EmailMessageSender(true, "Gateway");

            Notification notification = new UrgentNotification(telegram);

            notification.send("telegram-user", "First message");

            String beforeSwitch = output.toString();

            assertTrue(beforeSwitch.contains("Telegram -> telegram-user"));
            assertTrue(beforeSwitch.contains("[URGENT]First message"));

            output.reset();

            notification.setSender(email);
            notification.send("user@example.com", "Second message");

            String afterSwitch = output.toString();

            assertTrue(afterSwitch.contains("Email ->"));
            assertTrue(afterSwitch.contains("user@example.com"));
            assertTrue(afterSwitch.contains("[URGENT]Second message"));
            assertFalse(afterSwitch.contains("Telegram ->"));

        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @DisplayName("Push sender successfully delivers a message")
    void pushShouldSendSuccessfully() {
        MessageSender push = new PushNotificationSender(true);

        boolean result = push.sendMessage(
                "device-123",
                "Test",
                "Hello!"
        );

        assertTrue(result);
    }

    @Test
    @DisplayName("Marketing notification adds promo text and uses Email")
    void marketingShouldSendThroughEmail() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        try {
            System.setOut(new PrintStream(output));

            MessageSender email = new EmailMessageSender(true, "Gateway");
            Notification marketing = new MarketingNotification(email);

            marketing.send("user@example.com", "Get 20% off!");
        } finally {
            System.setOut(originalOut);
        }

        String text = output.toString();

        assertTrue(text.contains("user@example.com"));
        assertTrue(text.contains("Special offer"));
        assertTrue(text.contains("[PROMO]Get 20% off!"));
    }

    @Test
    @DisplayName("Unavailable Push sender fails to deliver")
    void pushShouldFailWhenUnavailable() {
        MessageSender push = new PushNotificationSender(false);

        boolean result = push.sendMessage(
                "device-123",
                "Test",
                "Hello!"
        );

        assertFalse(result);
    }

}
