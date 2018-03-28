package ba.etf.unsa.nwt.cinemaservice.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "news")
public class News extends BaseModel {
    private String title;
    private String contentText;
    private String imageUrl;
    private Date datePublished;
    private CinemaShowing cinemaShowing;

    protected News() {}

    public News(String title, String contentText, String imageUrl, Date datePublished, CinemaShowing cinemaShowing) {
        this.title = title;
        this.contentText = contentText;
        this.imageUrl = imageUrl;
        this.datePublished = datePublished;
        this.cinemaShowing = cinemaShowing;
    }

    @Size(max = 255, message = "Table news: Column title cannot be longer than 255 characters")
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotBlank(message = "Table news: Column content_text cannot be blank nor null")
    @Size(max = 5000, message = "Table news: Column content_text cannot be longer than 5000 characters")
    @Column(name = "content_text")
    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    @Size(max = 255, message = "Table news: Column image_url cannot be longer than 255 characters")
    @Column(name = "image_url")
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @NotNull(message = "Table news: Column date_published cannot be null")
    @Column(name = "date_published")
    public Date getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(Date datePublished) {
        this.datePublished = datePublished;
    }

    @ManyToOne
    @JoinColumn(name = "cinema_showing_id")
    public CinemaShowing getCinemaShowing() {
        return cinemaShowing;
    }

    public void setCinemaShowing(CinemaShowing cinemaShowing) {
        this.cinemaShowing = cinemaShowing;
    }

}
