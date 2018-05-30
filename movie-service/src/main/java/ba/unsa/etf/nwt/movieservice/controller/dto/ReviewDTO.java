package ba.unsa.etf.nwt.movieservice.controller.dto;

public class ReviewDTO {
    public Long userId;
    public String username;
    public String comment;
    public Long movieId;

    public ReviewDTO() {}

    public ReviewDTO(Long userId, String username, String comment, Long movieId) {
        this.userId = userId;
        this.username = username;
        this.comment = comment;
        this.movieId = movieId;
    }
}
