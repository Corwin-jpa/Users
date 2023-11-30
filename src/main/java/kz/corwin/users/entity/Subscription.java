package kz.corwin.users.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "subscriptions")
@IdClass(SubscriptionId.class)
@Getter
@Setter
public class Subscription {

    public Subscription(int publisherId, int subscriberId) {
        this.publisherId = publisherId;
        this.subscriberId = subscriberId;
    }

    public Subscription() {}

    @Id
    @Column(name = "publisher_id",nullable=false)
    private int publisherId;

    @Id
    @Column(name="subscriber_id",nullable=false)
    private int subscriberId;

//    @OneToOne (optional=false, cascade=CascadeType.ALL)
//    @JoinColumn (name="publisher_id")
//    private User publisher;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn (name = "subscriber_id", nullable = false)
//    private User subscriber;
}
