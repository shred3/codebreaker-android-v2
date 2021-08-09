package edu.cnm.deepdive.codebreaker.viewmodel;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import edu.cnm.deepdive.codebreaker.model.entity.User;
import edu.cnm.deepdive.codebreaker.service.GoogleSignInService;
import edu.cnm.deepdive.codebreaker.service.UserRepository;
import io.reactivex.disposables.CompositeDisposable;

public class UserViewModel extends AndroidViewModel implements LifecycleObserver {

  private final UserRepository repository;
  private final GoogleSignInService service;
  private final MutableLiveData<User> user;
  private final MutableLiveData<Throwable> throwable;
  private final CompositeDisposable pending;

  public UserViewModel(@NonNull Application application) {
    super(application);
    repository = new UserRepository(application);
    service = GoogleSignInService.getInstance();
    user = new MutableLiveData<>();
    throwable = new MutableLiveData<>();
    pending = new CompositeDisposable();
    loadUser();
  }

  public LiveData<User> getUser() {
    return user;
  }

  public LiveData<Throwable> getThrowable() {
    return throwable;
  }

  public void save(User user) {
    pending.add(
        repository
            .save(user)
            .subscribe(
                () -> {},
                this::postThrowable
            )
    );
  }

  private void loadUser() {
    pending.add(
        service
            .refreshUser()
            .subscribe(
                user::postValue,
                this::postThrowable
            )
    );
  }

  @OnLifecycleEvent(Event.ON_STOP)
  private void clearPending() {
    pending.clear();
  }

  private void postThrowable(Throwable throwable) {
    Log.e(getClass().getSimpleName(), throwable.getMessage(), throwable);
    this.throwable.postValue(throwable);
  }

}
