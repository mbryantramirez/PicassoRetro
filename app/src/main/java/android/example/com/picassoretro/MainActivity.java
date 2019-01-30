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

/**
 * Remember to format your code command option l makes it cleaner and easier to read
 */

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
            /**
             * add a null check to response.body otherwise your app has the possibility of crashing
             */
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

    if (savedInstanceState != null) {
      String savedPuppy = savedInstanceState.getString("puppyUrl");
      puppyUrl = savedPuppy;
      Picasso.get()
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



