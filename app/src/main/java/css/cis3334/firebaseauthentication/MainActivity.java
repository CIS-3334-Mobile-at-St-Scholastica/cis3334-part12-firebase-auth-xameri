package css.cis3334.firebaseauthentication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {

    private TextView textViewStatus;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private Button buttonGoogleLogin;
    private Button buttonCreateLogin;
    private Button buttonSignOut;

    private FirebaseAuth mAuth;

    /**
     * onCreate() method is a overrided method which is in super class.
     * it instantaites the values of text fields, buttons and creates objects.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assigning the instance of firebase to the mAuth
        mAuth = FirebaseAuth.getInstance();

        // Assigning the text fields and bottuns to the variables declared above
        textViewStatus = (TextView) findViewById(R.id.textViewStatus);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonGoogleLogin = (Button) findViewById(R.id.buttonGoogleLogin);
        buttonCreateLogin = (Button) findViewById(R.id.buttonCreateLogin);
        buttonSignOut = (Button) findViewById(R.id.buttonSignOut);

        // Creating an object to which is listening button login event
        // it will initiate if there is click event to the button login
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // it is just normal login
                signIn(editTextEmail.getText().toString(), editTextPassword.getText().toString());
            }
        });

        // Creating an object to which is listening button create login event
        // it will initiate if there is click event to the button create login
        buttonCreateLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // it is just a creating account
                createAccount(editTextEmail.getText().toString(), editTextPassword.getText().toString());
            }
        });

        // Creating an object to which is listening button google login event
        // it will initiate if there is click event to the button google login
        buttonGoogleLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // it is just google login
                // Google will log you in
                googleSignIn();
            }
        });

        // Creating an object to which is listening button sign out event
        // it will initiate if there is click event to the button sign out
        buttonSignOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // CIS3334
                // it is going to sign you out
                signOut();
            }
        });


    }

    /**
     * createAccount() method is creating accounts of a user that put its email and password or used google sign.
     * if it is successful the sign in, it saves the user info into the firebase. if fails, it will display a message.
     */

    private void createAccount(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Successful sign in
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // Sign in failed
                            textViewStatus.setText("Authentication failed");
                           // updateUI(null);
                        }

                        // ...
                    }
                });
    }

    /**
     * signin() method is signing in the user if it provides correct email and password.
     * otherwise it will display a message of failed signin.
     */

    private void signIn(String email, String password){

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                           // Create user with email success
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                           // Creating user with email failed
                            textViewStatus.setText("Creating user failed");
                            //updateUI(null);
                        }

                        // ...
                    }
                });

    }

    /**
     * signout() method signs out the user as it shows its name.
     */

    private void signOut () {

        mAuth.signOut();
    }

    private void googleSignIn() {

    }

   //=======================

    /**
     * onStart() is another overrided method which checks whether the user signed in already or not.
     */

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
        if (currentUser != null) {
            // User is already signed in
            textViewStatus.setText("Signed In");
        } else {
            // No user is signed in
            textViewStatus.setText("User already signed Out");
        }
    }



}
