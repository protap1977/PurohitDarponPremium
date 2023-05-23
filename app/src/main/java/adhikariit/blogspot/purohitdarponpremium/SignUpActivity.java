package adhikariit.blogspot.purohitdarponpremium;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private TextView showDate;


    private Button signUpButton;
    private Button button;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        nameEditText = findViewById(R.id.name_edittext);
        emailEditText = findViewById(R.id.email_edittext);
        passwordEditText = findViewById(R.id.password_edittext);
        confirmPasswordEditText = findViewById(R.id.confirm_password_edittext);
        showDate = findViewById(R.id.birthday_label);
        button = findViewById(R.id.calen_der_Btn);

        signUpButton = findViewById(R.id.sign_up_button);


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpButton.setEnabled(false);
                String name = nameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();
                String dateofBirth = showDate.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    nameEditText.setError("Name is required");
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    emailEditText.setError("Email is required");
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailEditText.setError("Please enter a valid email address");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    passwordEditText.setError("Password is required");
                    return;
                }

                if (password.length() < 6) {
                    passwordEditText.setError("Password must be at least 6 characters");
                    return;
                }

                if (TextUtils.isEmpty(confirmPassword)) {
                    confirmPasswordEditText.setError("Confirm password is required");
                    return;
                }

                if (!confirmPassword.equals(password)) {
                    confirmPasswordEditText.setError("Passwords do not match");
                    return;
                }

                if (TextUtils.isEmpty(dateofBirth)) {
                    showDate.setError("Please select your date of birth");
                    return;
                }
                // Get a reference to the Firebase Realtime Database
                DatabaseReference database = FirebaseDatabase.getInstance().getReference();

                // Generate a new key for the user
                String userId = database.child("users").push().getKey();

                // Create a new user object
                User user = new User(name, email, password, dateofBirth);

                // Add the user to the database
                database.child("users").child(userId).setValue(user);
                if (password.equals(confirmPassword)) {
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        // Do something with the user
                                        startActivity(new Intent(SignUpActivity.this, DashbordActivity.class));
                                        finish();
                                        Toast.makeText(SignUpActivity.this, "SignUp Successful!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(SignUpActivity.this, "SignUp Failed!", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(SignUpActivity.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();

            }
        });

    }

    private void openDialog() {
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                showDate.setText(String.valueOf(year) + "." + String.valueOf(month + 1) + "." + String.valueOf(dayOfMonth));
            }
        }, 2023, 12, 11);
        dialog.show();
    }
}