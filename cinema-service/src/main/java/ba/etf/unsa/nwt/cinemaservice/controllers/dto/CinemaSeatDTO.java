package ba.etf.unsa.nwt.cinemaservice.controllers.dto;

public class CinemaSeatDTO {
    private Long roomId;
    private Integer rowNum;
    private Integer colNum;
    private String mark;

    protected CinemaSeatDTO () {}

    public CinemaSeatDTO(Long roomId, Integer rowNum, Integer colNum, String mark) {
        this.roomId = roomId;
        this.rowNum = rowNum;
        this.colNum = colNum;
        this.mark = mark;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    public Integer getColNum() {
        return colNum;
    }

    public void setColNum(Integer colNum) {
        this.colNum = colNum;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

}
