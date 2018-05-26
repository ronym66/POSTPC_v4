package com.example.rony.v4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;


public class MainActivity extends AppCompatActivity {

    private static Retrofit.Builder mRetrofitBuilder;
    private static OkHttpClient.Builder mHttpClient;
    private static Retrofit mRetrofit;

    private ArrayList<ImageModel> mImageList;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageList = new ArrayList<>();
        initializeRecyclerView();
        Button imagesOnButton = findViewById(R.id.button);
        imagesOnButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mRetrofitBuilder = new Retrofit.Builder().baseUrl("https://api.imgur.com/").addConverterFactory(GsonConverterFactory.create());
                mHttpClient = new OkHttpClient.Builder();
                mRetrofit = mRetrofitBuilder.client(mHttpClient.build()).build();
                ImgurService imgurService = mRetrofit.create(ImgurService.class);
                Call<ImageResponse> call = imgurService.getAlbumImages("gXXW4X2");
                call.enqueue(new Callback<ImageResponse>(){

                    @Override
                    public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                        ImageResponse data = response.body();
                        if(data != null) {
                            for (ImageResponse.Image image: data.data.images) {
                                mImageList.add(new ImageModel(image.id, image.link));
                            }
                            mAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<ImageResponse> call, Throwable t) {

                    }
                });

            }
        });

    }

    public interface ImgurService {
        @Headers("Authorization: Client-ID " + "00935037616cf92")
        @GET("/3/album/{albumHash}")
        Call<ImageResponse> getAlbumImages(@Path("albumHash") String albumHash);
    }

    private void initializeRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.myRecycler);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new NewAdapter(mImageList);
        mRecyclerView.setAdapter(mAdapter);
    }

    public class ImageModel {
        private String mId;
        private String mImageURL;

        public ImageModel(String id, String url) {
            this.mId = id;
            this.mImageURL = url;
        }

        public String getImageURL() {
            return this.mImageURL;
        }

        public String getImageId() {
            return this.mId;
        }
    }
}
