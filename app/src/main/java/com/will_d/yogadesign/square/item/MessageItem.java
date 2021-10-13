package com.will_d.yogadesign.square.item;

public class MessageItem {

    private String id;
    private String name;
    private String message;
    private String time;
    private String profileUrl;


    public MessageItem(String id,String name, String message, String time, String profileUrl) {
        this.id= id;
        this.name = name;
        this.message = message;
        this.time = time;
        this.profileUrl = profileUrl;
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }

    public String getProfileUrl() {
        return profileUrl;
    }
}
