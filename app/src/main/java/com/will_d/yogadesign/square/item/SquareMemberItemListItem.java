package com.will_d.yogadesign.square.item;

public class SquareMemberItemListItem {


    private String imgUrl;
    private String nickName;
    private String name;

    private int counter;
    private String timeSum;


    public SquareMemberItemListItem(String imgUrl, String nickName, String name, int counter, String timeSum) {
        this.imgUrl = imgUrl;
        this.nickName = nickName;
        this.name = name;
        this.counter = counter;
        this.timeSum = timeSum;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public String getName() {
        return name;
    }

    public int getCounter() {
        return counter;
    }

    public String getTimeSum() {
        return timeSum;
    }
}
