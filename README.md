# Assignment 3 — Multi-Channel Notification Gateway

## Independent Extension
We added PushNotificationSender by implementing MessageSender, without changing the existing notification classes.

We also added MarketingNotification by extending Notification, without changing the existing senders.

This shows the main benefit of the Bridge pattern: notification types and delivery providers can be developed separately. Any notification type can work with any provider that implements MessageSender.