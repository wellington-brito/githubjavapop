package com.example.wellington_brito.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Repository implements Serializable, Parcelable {

    private int id;
    private String repositoryName;
    private String repositoryDescricpiton;
    private String countForks;
    private String countStars;
    private String username;
    private String lastName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public String getRepositoryDescricpiton() {
        return repositoryDescricpiton;
    }

    public void setRepositoryDescricpiton(String repositoryDescricpiton) {
        this.repositoryDescricpiton = repositoryDescricpiton;
    }

    public String getCountForks() {
        return countForks;
    }

    public void setCountForks(String countForks) {
        this.countForks = countForks;
    }

    public String getCountStars() {
        return countStars;
    }

    public void setCountStars(String countStars) {
        this.countStars = countStars;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Repository() {}
    protected Repository(Parcel in) {
        id = in.readInt();
        repositoryName = in.readString();
        repositoryDescricpiton = in.readString();
        countForks = in.readString();
        countStars = in.readString();
        username = in.readString();
        lastName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(repositoryName);
        dest.writeString(repositoryDescricpiton);
        dest.writeString(countForks);
        dest.writeString(countStars);
        dest.writeString(username);
        dest.writeString(lastName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Repository> CREATOR = new Creator<Repository>() {
        @Override
        public Repository createFromParcel(Parcel in) {
            return new Repository(in);
        }

        @Override
        public Repository[] newArray(int size) {
            return new Repository[size];
        }
    };
}
