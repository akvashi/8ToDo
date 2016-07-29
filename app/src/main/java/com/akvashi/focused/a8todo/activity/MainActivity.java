package com.akvashi.focused.a8todo.activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.akvashi.focused.a8todo.R;
import com.akvashi.focused.a8todo.a8ToDoApplication;
import com.akvashi.focused.a8todo.fragment.ToDoFragment;
import com.akvashi.focused.a8todo.injector.component.ApplicationComponent;
import com.akvashi.focused.a8todo.injector.component.DaggerMainComponent;
import com.akvashi.focused.a8todo.injector.component.MainComponent;
import com.akvashi.focused.a8todo.injector.module.ActivityModule;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    private static final int REQUEST_INVITE = 1;
    public static final String ANONYMOUS = "anonymous";
    private static final String MESSAGE_SENT_EVENT = "message_sent";
    private String mUsername;
    private String mPhotoUrl;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;


    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseAnalytics mFirebaseAnalytics;

    private MainComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ApplicationComponent appComponent = ((a8ToDoApplication) getApplication()).getApplicationComponent();
        component = DaggerMainComponent.builder()
                .applicationComponent(((a8ToDoApplication) getApplication()).getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build();

        component.inject(this);


        ToDoFragment fragment = new ToDoFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.fragment, fragment).commit();

        // Set default username is anonymous.
        mUsername = ANONYMOUS;

        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();

        //Initialize Analytics
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


        mAuthListener = firebaseAuth -> {
            mFirebaseUser = mFirebaseAuth.getCurrentUser();
            if (mFirebaseUser == null) {
                // User is signed out
                startActivity(new Intent(MainActivity.this, SignInActivity.class));
                finish();
                Log.d(TAG, "onAuthStateChanged:signed_in:" + mFirebaseUser.getUid());
                return;
            } else {
                // User is signed in
                mUsername = mFirebaseUser.getDisplayName();
                if (mFirebaseUser.getPhotoUrl() != null) {
                    mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
                }
                Log.d(TAG, "onAuthStateChanged:signed_out");
            }
            // ...
        };


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }

    public MainComponent getComponent() {
        return component;
    }


    @Override
    public void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.invite_menu:
                sendInvitation();
                return true;
            case R.id.sign_out_menu:
                mFirebaseAuth.signOut();
                mUsername = ANONYMOUS;
                startActivity(new Intent(this, SignInActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void sendInvitation() {
        Intent intent = new AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
                .setMessage(getString(R.string.invitation_message))
                .setCallToActionText(getString(R.string.invitation_cta))
                .build();
        startActivityForResult(intent, REQUEST_INVITE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode);

        if (requestCode == REQUEST_INVITE) {
            if (resultCode == RESULT_OK) {
                Bundle payload = new Bundle();
                payload.putString(FirebaseAnalytics.Param.VALUE, "sent");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SHARE,
                        payload);
                // Check how many invitations were sent and log.
                String[] ids = AppInviteInvitation.getInvitationIds(resultCode,
                        data);
                Log.d(TAG, "Invitations sent: " + ids.length);
            } else {
                Bundle payload = new Bundle();
                payload.putString(FirebaseAnalytics.Param.VALUE, "not sent");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SHARE,
                        payload);
                // Sending failed or it was canceled, show failure message to
                // the user
                Log.d(TAG, "Failed to send invitation.");
            }
        }
    }

}
