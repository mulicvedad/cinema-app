package ba.etf.unsa.nwt.cinemaservice.controllers.dto;

public class CinemaSeatDTO {

    public Long roomId;
    public Integer rowNum;
    public Integer colNum;
    public String mark;

    public CinemaSeatDTO(Long roomId, Integer rowNum, Integer colNum, String mark) {
        this.roomId = roomId;
        this.rowNum = rowNum;
        this.colNum = colNum;
        this.mark = mark;
    }

}
