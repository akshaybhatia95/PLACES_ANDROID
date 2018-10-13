package com.example.d.places;

import java.util.Comparator;

/**
 * Created by D on 4/23/2018.
 */

public class Author {
    private String name;
    private String author_url;
    private String profilePhoto;
    private String rating;
    private String text;
    private String time;

    public Author(String name, String author_url, String profilePhoto, String rating, String text, String time) {
        this.name = name;
        this.author_url = author_url;
        this.profilePhoto = profilePhoto;
        this.rating = rating;
        this.text = text;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor_url() {
        return author_url;
    }

    public void setAuthor_url(String author_url) {
        this.author_url = author_url;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

class SortByLowestRating implements Comparator<Author> {


    @Override
    public int compare(Author o1, Author o2) {
        return Integer.parseInt(o1.getRating()) - Integer.parseInt(o2.getRating());
    }
}

class SortByHighestRating implements Comparator<Author> {

    @Override
    public int compare(Author o1, Author o2) {
        return Integer.parseInt(o2.getRating()) - Integer.parseInt(o1.getRating());
    }
}


class SortByLeastRecent implements Comparator<Author> {

    @Override
    public int compare(Author o1, Author o2) {
        return Integer.parseInt(o2.getTime()) - Integer.parseInt(o1.getTime());
    }
}

class SortByMostRecent implements Comparator<Author> {

    @Override
    public int compare(Author o1, Author o2) {
        return Integer.parseInt(o1.getTime()) - Integer.parseInt(o2.getTime());
    }
}


class SortByDefault implements Comparator<Author> {

    @Override
    public int compare(Author o1, Author o2) {
        return 0;
    }
}

