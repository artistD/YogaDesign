package com.will_d.yogadesign.square.item;


public class SquareMemberItem {
    private String id;
    private String imgUrl;
    private String memberName;
    private String stateMsg;


    public SquareMemberItem(String id, String imgUrl, String memberName, String stateMsg) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.memberName = memberName;
        this.stateMsg = stateMsg;
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

    public String getStateMsg() {
        return stateMsg;
    }
}
