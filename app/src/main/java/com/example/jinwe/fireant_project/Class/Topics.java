package com.example.jinwe.fireant_project.Class;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jinwe on 7/5/2018.
 */

public class Topics implements Parcelable{

    int id;
    String topic;
    String created_at;
    Integer up_vote;
    Integer down_vote;

    public Topics(){}

    public Topics(int id, String topic, String created_at){
        this.id = id;
        this.topic = topic;
        this.created_at = created_at;
    }

    public Topics(String topic, String created_at, int down_vote, int up_vote){
        this.topic = topic;
        this.created_at = created_at;
        this.down_vote = down_vote;
        this.up_vote = up_vote;
    }

    protected Topics(Parcel in) {
        id = in.readInt();
        topic = in.readString();
        created_at = in.readString();
        down_vote = in.readInt();
        up_vote = in.readInt();
    }

    public static final Creator<Topics> CREATOR = new Creator<Topics>() {
        @Override
        public Topics createFromParcel(Parcel in) {
            return new Topics(in);
        }

        @Override
        public Topics[] newArray(int size) {
            return new Topics[0];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Integer getUp_vote() {
        return up_vote;
    }

    public void setUp_vote(Integer up_vote) {
        this.up_vote = up_vote;
    }

    public Integer getDown_vote() {
        return down_vote;
    }

    public void setDown_vote(Integer down_vote) {
        this.down_vote = down_vote;
    }


    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(topic);
        dest.writeString(created_at);
        dest.writeInt(down_vote);
        dest.writeInt(up_vote);

    }
}
