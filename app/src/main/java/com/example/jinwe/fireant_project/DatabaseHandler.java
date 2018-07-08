package com.example.jinwe.fireant_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jinwe.fireant_project.Class.Topics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jinwe on 7/5/2018.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;

    private static final String DATABASE_NAME   = "FireAnt.db";
    public static final String TABLE_TOPICS     = "topic";
    public static final String TOPIC_ID         = "id";
    public static final String TOPIC_TITLE      = "title";
    public static final String TOPIC_CREATED_AT = "created_at";
    public static final String TOPIC_UP_VOTE = "up_vote";
    public static final String TOPIC_DOWN_VOTE = "down_vote";

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_TOPICS =
                "CREATE TABLE IF NOT EXISTS " + TABLE_TOPICS + "("
                        + TOPIC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + TOPIC_TITLE + " TEXT, "
                        + TOPIC_CREATED_AT + " TEXT, "
                        + TOPIC_DOWN_VOTE + " INTEGER, "
                        + TOPIC_UP_VOTE + " INTEGER "
                        + " ); ";
        db.execSQL(CREATE_TABLE_TOPICS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TOPICS);
        onCreate(db);
    }

    public void addTopicsData(Topics topics){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put(TOPIC_TITLE, topics.getTopic());
        value.put(TOPIC_CREATED_AT, topics.getCreated_at());
        value.put(TOPIC_DOWN_VOTE, topics.getDown_vote());
        value.put(TOPIC_UP_VOTE, topics.getUp_vote());

        db.insert(TABLE_TOPICS, null, value);
        db.close();
    }

    public List<Topics> getAllTopicsData(){
        List<Topics> topicses = new ArrayList<>();
        String select = " SELECT * FROM " + TABLE_TOPICS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(select, null);

        if(cursor.moveToFirst()) {
            do {
                Topics topics = new Topics();
                topics.setId(Integer.parseInt(cursor.getString(0)));
                topics.setTopic(cursor.getString(1));
                topics.setCreated_at(cursor.getString(2));
                topics.setDown_vote(cursor.getInt(3));
                topics.setUp_vote(cursor.getInt(4));
                topicses.add(topics);
            }while(cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return topicses;
    }

    public int updateUpVote(Topics topics){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TOPIC_UP_VOTE, topics.getUp_vote());

        return  db.update(TABLE_TOPICS, values, TOPIC_ID + " =?", new String[]{String.valueOf(topics.getId())});
    }

    public int updateDownVote(Topics topics){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TOPIC_DOWN_VOTE, topics.getDown_vote());

        return  db.update(TABLE_TOPICS, values, TOPIC_ID + " =?", new String[]{String.valueOf(topics.getId())});
    }

    public List<Topics> getHighestValue(){
        List<Topics> topics = new ArrayList<>();
        String select = " SELECT * FROM " + TABLE_TOPICS + " ORDER BY " + TOPIC_UP_VOTE + " DESC" ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(select, null);

        if(cursor.moveToFirst()){
            do{
                Topics topics1 = new Topics();
                topics1.setId(Integer.parseInt(cursor.getString(0)));
                topics1.setTopic(cursor.getString(1));
                topics1.setCreated_at(cursor.getString(2));
                topics1.setDown_vote(cursor.getInt(3));
                topics1.setUp_vote(cursor.getInt(4));
                topics.add(topics1);
            }while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return topics;
    }

    public int updateTopicsData(Topics topics){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TOPIC_TITLE, topics.getTopic());
        values.put(TOPIC_CREATED_AT, topics.getCreated_at());
        values.put(TOPIC_DOWN_VOTE, topics.getDown_vote());
        values.put(TOPIC_UP_VOTE, topics.getUp_vote());

        return db.update(TABLE_TOPICS, values, TOPIC_ID + " =?", new String[]{String.valueOf(topics.getId())} );
    }
}
