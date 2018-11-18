package example.com.mvpcourse.twitchapi;



import example.com.mvpcourse.twitchapi.apimodel.Twitch;
import retrofit2.Call;
import retrofit2.http.GET;
import rx.Observable;

public interface TwitchAPI {
    
    @GET("games/top")
    Call<Twitch> getTopGames();
    
    @GET("games/top")
    Observable<Twitch> getTopGamesObservable();
}
