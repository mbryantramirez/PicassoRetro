package android.example.com.picassoretro.model;

import retrofit2.Call;
import retrofit2.http.GET;




public interface PuppyService {
    @GET("api/breeds/image/random")
    Call<RandoPuppy> getPuppy();
}

//calls the getPupp( method which makes http requto JSON