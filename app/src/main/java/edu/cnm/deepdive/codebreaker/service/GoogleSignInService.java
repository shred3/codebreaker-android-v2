package edu.cnm.deepdive.codebreaker.service;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.util.Log;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import edu.cnm.deepdive.codebreaker.BuildConfig;
import edu.cnm.deepdive.codebreaker.model.entity.User;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class GoogleSignInService {

  private static final String BEARER_TOKEN_FORMAT = "Bearer %s";

  private static Application context;

  private final GoogleSignInClient client;
  private final UserRepository userRepository;

  private GoogleSignInAccount account;

  private GoogleSignInService() {
    GoogleSignInOptions options = new GoogleSignInOptions.Builder()
        .requestEmail()
        .requestId()
        .requestProfile()
        .requestIdToken(BuildConfig.CLIENT_ID)
        .build();
    client = GoogleSignIn.getClient(context, options);
    userRepository = new UserRepository(context);
  }

  public static void setContext(Application context) {
    GoogleSignInService.context = context;
  }

  public static GoogleSignInService getInstance() {
    return InstanceHolder.INSTANCE;
  }

  public GoogleSignInAccount getAccount() {
    return account;
  }

  private void setAccount(GoogleSignInAccount account) {
    this.account = account;
    // TODO Remove following line after we start sending authenticated requests to service.
    Log.d(getClass().getName(), (account != null) ? account.getIdToken() : "(none)");
  }

  public Single<GoogleSignInAccount> refresh() {
    return Single.create((emitter) ->
        client.silentSignIn()
            .addOnSuccessListener(this::setAccount)
            .addOnSuccessListener(emitter::onSuccess)
            .addOnFailureListener(emitter::onError)
    );
  }

  public Single<String> refreshBearerToken() {
    return refresh()
        .map((account) -> String.format(BEARER_TOKEN_FORMAT, account.getIdToken()));
  }

  public Single<User> refreshUser() {
    return refresh()
        .observeOn(Schedulers.io())
        .flatMap((account) -> userRepository.getOrCreate(account.getId(), account.getDisplayName()));

  }

  public void startSignIn(Activity activity, int requestCode) {
    setAccount(null);
    Intent intent = client.getSignInIntent();
    activity.startActivityForResult(intent, requestCode);
  }

  public Task<GoogleSignInAccount> completeSignIn(Intent data) {
    Task<GoogleSignInAccount> task = null;
    try {
      task = GoogleSignIn.getSignedInAccountFromIntent(data);
      setAccount(task.getResult(ApiException.class));
    } catch (ApiException e) {
      // Exception will be passed automatically to onFailureListener.
    }
    return task;
  }

  public Task<Void> signOut() {
    return client.signOut()
        .addOnCompleteListener((ignored) -> setAccount(null));
  }

  private static class InstanceHolder {

    private static final GoogleSignInService INSTANCE = new GoogleSignInService();

  }

}
