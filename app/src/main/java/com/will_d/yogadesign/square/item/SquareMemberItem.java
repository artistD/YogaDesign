package com.will_d.yogadesign.square.item;

public class SquareMemberItem {
    private String imgUrl;
    private String memberName;


    public SquareMemberItem(String imgUrl, String memberName) {
        this.imgUrl = imgUrl;
        this.memberName = memberName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getMemberName() {
        return memberName;
    }
}
