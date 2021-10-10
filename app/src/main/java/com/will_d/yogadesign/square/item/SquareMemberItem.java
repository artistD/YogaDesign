package com.will_d.yogadesign.square.item;

public class SquareMemberItem {
    private String id;
    private String imgUrl;
    private String memberName;


    public SquareMemberItem(String id, String imgUrl, String memberName) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.memberName = memberName;
    }

    public String getId() {
        return id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getMemberName() {
        return memberName;
    }
}
