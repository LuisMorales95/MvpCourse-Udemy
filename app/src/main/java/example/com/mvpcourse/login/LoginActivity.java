package example.com.mvpcourse.login;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLOutput;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.com.mvpcourse.R;
import example.com.mvpcourse.root.App;
import example.com.mvpcourse.twitchapi.TwitchAPI;
import example.com.mvpcourse.twitchapi.apimodel.Top;
import example.com.mvpcourse.twitchapi.apimodel.Twitch;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class LoginActivity extends AppCompatActivity implements LoginActivityMVP.View{
    
    @Inject
    LoginActivityMVP.Presenter presenter;
    @Inject
    TwitchAPI twitchAPI;
    
    @BindView(R.id.loginActivity_firstName_editText)
    EditText firstName;
    @BindView(R.id.loginActivity_lastName_editText)
    EditText lastName;
    @BindView(R.id.loginActivity_login_button)
    Button login;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        
        ((App) getApplication()).getComponent().inject(this);
        
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.loginButtonClicked();
            }
        });
        
        Call<Twitch> call = twitchAPI.getTopGames();
        
        call.enqueue(new Callback<Twitch>() {
            @Override
            public void onResponse(Call<Twitch> call, Response<Twitch> response) {
                List<Top> gameList = response.body().getTop();
                
                for (Top top : gameList){
                    System.out.println(top.getGame().getName());
                }
            }
    
            @Override
            public void onFailure(Call<Twitch> call, Throwable t) {
                t.printStackTrace();
            }
        });
        
        twitchAPI.getTopGamesObservable()
                .flatMap(new Func1<Twitch, Observable<Top>>() {
                    @Override
                    public Observable<Top> call(Twitch twitch) {
                        return Observable.from(twitch.getTop());
                    }
                })
                .flatMap(new Func1<Top, Observable<String>>() {
                    @Override
                    public Observable<String> call(Top top) {
                        return Observable.just(top.getGame().getName());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(LoginActivity.this, "Completed", Toast.LENGTH_SHORT).show();
                    }
    
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
    
                    @Override
                    public void onNext(String s) {
                        System.out.println("RxJava OutPut: " + s);
                    }
                });
        
    }
    
    
    
    
    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.getCurrentUser();
    }
    
    @Override
    public String getFirstName() {
        return firstName.getText().toString();
    }
    
    @Override
    public String getLastName() {
        return lastName.getText().toString();
    }
    
    @Override
    public void showUserNotAvailable() {
        Toast.makeText(this, "User is not Available", Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void showInputError() {
        Toast.makeText(this, "First or Last Name can't be empty", Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void showUserSavedMessage() {
        Toast.makeText(this, "User Saved", Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void setFirstName(String fName) {
        firstName.setText(fName);
    }
    
    @Override
    public void setLastName(String lName) {
        lastName.setText(lName);
    }
}
