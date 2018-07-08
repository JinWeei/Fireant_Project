package com.example.jinwe.fireant_project.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jinwe.fireant_project.Adapter.TopicsAdapter;
import com.example.jinwe.fireant_project.Class.Topics;
import com.example.jinwe.fireant_project.DatabaseHandler;
import com.example.jinwe.fireant_project.OnItemClick;
import com.example.jinwe.fireant_project.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity{

    private ArrayList<Topics> topicses = new ArrayList<>();
    private TopicsAdapter topicsAdapter;
    private DatabaseHandler mDBHandler;

    ListView listView;
    EditText editText;
    Button submit;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDBHandler = new DatabaseHandler(this);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("send_back"));

        listView = (ListView) findViewById(R.id.listPro);
        editText = (EditText) findViewById(R.id.insertTopic);
        submit = (Button) findViewById(R.id.Submit);

        getData();

        topicsAdapter = new TopicsAdapter(MainActivity.this, topicses, MainActivity.this);
        listView.setAdapter(topicsAdapter);
        topicsAdapter.notifyDataSetChanged();

        final DatabaseHandler db = new DatabaseHandler(this);


        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        date = DateFormat.format("dd-MM-yyyy HH:mm:ss", cal).toString();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        String topic = editText.getText().toString();
                        if(topic.equals("")){
                            Toast.makeText(getApplicationContext(), "Invalid input.", Toast.LENGTH_SHORT).show();
                        }else {
                            db.addTopicsData(new Topics(topic, date, 0, 0));
                            finish();
                            startActivity(getIntent());
                        }
                    }
                });
            }
        });


    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String data = intent.getStringExtra("Message");
            if(data.equals("yes")){
                finish();
                startActivity(getIntent());
            }
        }
    };

    protected void getData(){
        List<Topics> topics = mDBHandler.getHighestValue();
        if(topics.isEmpty()){

        }else{
            for(int i = 0; i<topics.size(); i++){
                Topics topics1 = topics.get(i);
                String title = topics1.getTopic();
                String date = topics1.getCreated_at();
                Integer down = topics1.getDown_vote();
                Integer up = topics1.getUp_vote();
                Topics topics2 = new Topics(title, date, down, up);
                topicses.add(topics2);

            }
        }
    }
}
