package com.will_d.yogadesign.square.item;

import java.util.ArrayList;

public class SquareMemberItem {
    private String id;

    private String imgUrl;

    private String memberName;
    private String stateMsg;
    public int favoriteNum;
    private ArrayList<String> favoriteCheckedUserList;


    public SquareMemberItem(String id, String imgUrl, String memberName, String stateMsg, int favoriteNum, ArrayList<String> favoriteCheckedUserList) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.memberName = memberName;
        this.stateMsg = stateMsg;
        this.favoriteNum = favoriteNum;
        this.favoriteCheckedUserList = favoriteCheckedUserList;
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

    public int getFavoriteNum() {
        return favoriteNum;
    }

    public ArrayList<String> getFavoriteCheckedUserList() {
        return favoriteCheckedUserList;
    }

}
