package android.example.com.picassoretro;

import android.example.com.picassoretro.model.PuppyService;
import android.example.com.picassoretro.model.RandoPuppy;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//
//public class MainActivity extends AppCompatActivity {
//    private PuppyService puppyService;
//    private static final String TAG = "JSON?";
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://dog.ceo/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        // creating the service so we can use it to make requests:
//        puppyService = retrofit.create(PuppyService.class);
//
//        Call<RandoPuppy> puppy = puppyService.getPuppy();
//        puppy.enqueue(new Callback<RandoPuppy>() {
//            @Override
//            public void onResponse(Call<RandoPuppy> call, Response<RandoPuppy> response) {
//                Log.d(TAG, "onResponse: " + response.body().getMessage());
//            }
//
//            @Override
//            public void onFailure(Call<RandoPuppy> call, Throwable t) {
//                Log.d(TAG, "onResponse: " + t.toString());
//            }
//        });
//    }
//
//    //does this go here?
//    //wat is the diff w it going in a singleton
////    As you can see in Call, we are saying that whatever JSON object we get back, we are treating it like our data model "RandoPuppy" and so we'll be able to use the getter methods in RandoPuppy.java to get the URL string for photo link from the JSON key "message". But that's only if we get a response. If we get a response (onResponse()), we should be able to log our response body, and its contents. If we fail, (onFailure()), we should be able to log our error, and fail gracefully.
//


    public class MainActivity extends AppCompatActivity {

        private static final String TAG = "JSON?";
        private ImageView imageView;
        private Button newPuppy;
        private PuppyService puppyService;
        private String puppyUrl;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            imageView = (ImageView) findViewById(R.id.puppy_imageview);
            newPuppy = (Button) findViewById(R.id.new_puppy_button);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://dog.ceo/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            puppyService = retrofit.create(PuppyService.class);

            newPuppy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Call<RandoPuppy> puppy = puppyService.getPuppy();
                    puppy.enqueue(new Callback<RandoPuppy>() {
                        @Override
                        public void onResponse(Call<RandoPuppy> call, Response<RandoPuppy> response) {
                            Log.d(TAG, "onResponse: " + response.body().getMessage());
                            puppyUrl = response.body().getMessage();
                            Picasso.get()
                                    .load(response.body().getMessage())
                                    .into(imageView);
                        }

                        @Override
                        public void onFailure(Call<RandoPuppy> call, Throwable t) {
                            Log.d(TAG, "onResponse: " + t.toString());
                        }
                    });
                }
            });

            if(savedInstanceState != null) {
                String savedPuppy = savedInstanceState.getString("puppyUrl");
                puppyUrl = savedPuppy;
                Picasso.with(getApplicationContext())
                        .load(savedPuppy)
                        .into(imageView);
            } else {
                newPuppy.callOnClick();
            }
        }

        @Override
        protected void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putString("puppyUrl", puppyUrl);
        }
    }
}


