package com.example.medlink_deliverable2.models;

/**
 * @author Katerina
 *
 */

public class Rating {

    private String comment;
    private Float rating;

    public Rating(String comment, Float rating){
        this.comment = comment;
        this.rating = rating;
    }

    public String getComment() {
        return comment; }

    public void setComment(String comment) {
        this.comment = comment; }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Rating(){}
}
