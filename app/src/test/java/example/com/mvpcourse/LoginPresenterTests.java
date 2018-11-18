package example.com.mvpcourse;

import org.junit.Before;
import org.junit.Test;

import example.com.mvpcourse.login.LoginActivityMVP;
import example.com.mvpcourse.login.LoginPresenter;
import example.com.mvpcourse.login.User;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class LoginPresenterTests {
    LoginActivityMVP.Model mockLoginModel;
    LoginActivityMVP.View mockLoginView;
    LoginPresenter  presenter;
    User user;
    
    @Before
    public void setup(){
        mockLoginModel = mock(LoginActivityMVP.Model.class);
        user = new User("Jamie","Fox");
//        when(mockLoginModel.getUser()).thenReturn(user);
        mockLoginView = mock(LoginActivityMVP.View.class);
        presenter= new LoginPresenter(mockLoginModel);
        presenter.setView(mockLoginView);
    }
    
    @Test
    public void noInterationWithView(){
        presenter.getCurrentUser();
        verifyZeroInteractions(mockLoginView);
    }
    @Test
    public void loadTheUserFromTheRespositoryWhenValidUserIsPresent(){
        when(mockLoginModel.getUser()).thenReturn(user);
        presenter.getCurrentUser();
        verify(mockLoginModel,times(1)).getUser();
        verify(mockLoginView,times(1)).setFirstName("Jamie");
        verify(mockLoginView,times(1)).setLastName("Fox");
        verify(mockLoginView,never()).showUserNotAvailable();
    }
    @Test
    public void shouldShowErrorMessageWhenUserIsNull(){
        when(mockLoginView.getFirstName()).thenReturn(null);
        presenter.getCurrentUser();
        verify(mockLoginModel,times(1)).getUser();
        verify(mockLoginView,never()).setFirstName("Jamie");
        verify(mockLoginView,never()).setLastName("Fox");
        verify(mockLoginView,times(1)).showUserNotAvailable();
    }
    
    
}
