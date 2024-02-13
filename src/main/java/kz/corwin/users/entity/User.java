package kz.corwin.users.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE user_id=?")
@Where(clause = "deleted=false")
@Getter
@Setter
public class User {
    public User(Integer id, String username, String surname, String country, LocalDate dateOfBirdth, String relationshipStatus, Boolean deleted) {
        this.id = id;
        this.username = username;
        this.surname = surname;
        this.country = country;
        this.dateOfBirdth = dateOfBirdth;
        this.relationshipStatus = relationshipStatus;
        this.deleted = deleted;
    }

    public User(){

    }

    public User(Integer id, String username, String surname) {
        this.id = id;
        this.username = username;
        this.surname = surname;
    }

    public User(String username, String surname) {
        this.username = username;
        this.surname = surname;
    }

    @Id
    @Column(name = "user_id",nullable=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="username",nullable=false)
    private String username;

    @Column(name="surname",nullable=false)
    private String surname;

    @Column(name="country",nullable=false)
    private String country;

    @Column(name="date_of_birdth",nullable=false)
    private LocalDate dateOfBirdth;

    @Column(name="relationship_status")
    private String relationshipStatus;

    @Column(name="deleted")
    private Boolean deleted = Boolean.FALSE;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subscriber")
//    private List<User> subscribers;

//    @OneToOne (optional=false, mappedBy="publisher")
//    private User user;


}
