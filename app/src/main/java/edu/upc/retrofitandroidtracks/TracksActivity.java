package edu.upc.retrofitandroidtracks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TracksActivity extends AppCompatActivity {

    static final String BASE_URL = "http://localhost:8080/dsaApp/";
    TracksAPI tracksAPI;

    public EditText titleText;
    public EditText singerText;

    public Track myTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracks);
        titleText = findViewById(R.id.titleEditText);
        singerText = findViewById(R.id.singerEditText);
        createTracksAPI();

        Intent intent = getIntent();
        String title = intent.getStringExtra("Track title");
        String singer = intent.getStringExtra("Track singer");
        String id = intent.getStringExtra("Track id");
        myTrack = new Track(title, singer);
        myTrack.setId(id);

        titleText.setText(myTrack.getTitle());
        singerText.setText(myTrack.getSinger());

    }

    public void onClick(View v){
        Button bt = (Button)v;
        String operacio = (String)bt.getText();

        String title = titleText.getText().toString();
        String singer = singerText.getText().toString();

        if(operacio.equals("UPDATE")){
            myTrack.setTitle(title);
            myTrack.setSinger(singer);
            Call<ResponseBody> updateCall = tracksAPI.updateTrack(myTrack);
            updateCall.enqueue(updateCallback);
        }
        else if(operacio.equals("DELETE")){
            Call<ResponseBody> deleteCall = tracksAPI.deleteTrack(myTrack.getId());
            deleteCall.enqueue(deleteCallback);
        }
        else if(operacio.equals("POST")){
            Track myTrack2 = new Track(title,singer);
            Call<Track> postCall = tracksAPI.addTrack(myTrack2);
            postCall.enqueue(postCallback);
        }
    }

    public void createTracksAPI(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        tracksAPI = retrofit.create(TracksAPI.class);
    }

    Callback<Track> postCallback = new Callback<Track>() {
        @Override
        public void onResponse(Call<Track> call2, Response<Track> response) {
            Track track = response.body();
            /*
            System.out.println("Track afegida:");
            System.out.println(track.getId());
            System.out.println(track.getTitle());
            System.out.println(track.getSinger());

             */

        }

        @Override
        public void onFailure(Call<Track> call2, Throwable throwable) {
            throwable.printStackTrace();
        }
    };

    Callback<ResponseBody> deleteCallback = new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> deleteCall, Response<ResponseBody> response) {
            //System.out.println("Track borrada");
        }

        @Override
        public void onFailure(Call<ResponseBody> deleteCall, Throwable throwable) {
            throwable.printStackTrace();
        }
    };

    Callback<ResponseBody> updateCallback = new Callback<ResponseBody>(){
        @Override
        public void onResponse(Call<ResponseBody> updateCall, Response<ResponseBody> response) {
            //System.out.println("Track updated");
        }

        @Override
        public void onFailure(Call<ResponseBody> updateCall, Throwable throwable) {
            throwable.printStackTrace();
        }
    };
}