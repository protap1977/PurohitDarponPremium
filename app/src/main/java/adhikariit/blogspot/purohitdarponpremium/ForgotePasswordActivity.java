package adhikariit.blogspot.purohitdarponpremium;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotePasswordActivity extends AppCompatActivity {

    private EditText mEmailEditText;
    private Button mResetPasswordButton;
    private TextView mBackToLoginTextView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgote_password);

        mEmailEditText = findViewById(R.id.forgot_password_email);
        mResetPasswordButton = findViewById(R.id.forgot_password_button);
        mBackToLoginTextView = findViewById(R.id.back_to_login);

        mAuth = FirebaseAuth.getInstance();

        mResetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reset password logic goes here
                resetPassword();
            }
        });

        mBackToLoginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go back to login activity logic goes here
                startActivity(new Intent(ForgotePasswordActivity.this,MainActivity.class));
                finish();
            }
        });
    }
    private void resetPassword() {
        String email = mEmailEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter your email!", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Check email to reset your password!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Fail to send reset password email!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}