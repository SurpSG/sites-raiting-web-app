package com.leader.beans;

/**
 * Created by ruki on 09.06.2014.
 */
public class Site {

    private static final int SHORT_DESCRITION_LENGTH = 200;

    private int id;
    private String name;
    private double rating;
    private String description;
    private String pictureURL;
    private String url;
    private String shortDescription;

    public Site() {
    }

    public String getName() {
        return name;
    }

    public double getRating() {
        return rating;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public String getShortDescription() {
        return shortDescription;
    }

//    public static String createShortDescription(String description) {
//        String shortDescription = "";
//        if (description.length() < SHORT_DESCRITION_LENGTH) {
//            shortDescription = description;
//        } else {
//            shortDescription = description.substring(0, SHORT_DESCRITION_LENGTH);
//        }
//        return shortDescription;
//    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        if(url.startsWith("http")){
            this.url = url;
        }else{
            this.url = "http:\\\\"+url;
        }
    }
}
