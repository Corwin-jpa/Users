package kz.corwin.users.entity;

import java.io.Serializable;

public class SubscriptionId implements Serializable {
    private int publisherId;
    private int subscriberId;

    public SubscriptionId() {}

    public SubscriptionId(int publisherId, int subscriberId) {
        this.publisherId = publisherId;
        this.subscriberId = subscriberId;
    }
}
