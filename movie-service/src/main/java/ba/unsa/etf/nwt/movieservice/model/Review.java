package ba.unsa.etf.nwt.movieservice.model;

import javax.persistence.*;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String username;
    private String comment;

    public Review() {
    }

    public Review(Long userId, String username, String comment) {
        this.userId = userId;
        this.username = username;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }


    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", userId=" + userId +
                ", username=" + username +
                ", comment='" + comment + '\'' +
                '}';
    }
}
