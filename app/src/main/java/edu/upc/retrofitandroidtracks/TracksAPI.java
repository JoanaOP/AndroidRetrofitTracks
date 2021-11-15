package edu.upc.retrofitandroidtracks;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.DELETE;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.Call;
import retrofit2.http.*;

public interface TracksAPI {
    @GET("tracks")
    Call<List<Track>> loadTracks();

    @GET("tracks/{id}")
    Call<Track> getTrack(@Path("id") String id);

    @DELETE("tracks/{id}")
    Call<ResponseBody> deleteTrack(@Path("id") String id);

    @PUT("tracks")
    Call<ResponseBody> updateTrack(@Body Track track);

    @POST("tracks")
    Call<Track> addTrack(@Body Track track);
}
