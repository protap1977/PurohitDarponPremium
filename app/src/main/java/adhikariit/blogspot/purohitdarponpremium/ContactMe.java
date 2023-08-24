package adhikariit.blogspot.purohitdarponpremium;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ContactMe extends AppCompatActivity implements View.OnClickListener {
    private Button sendButton, clearBtn;
    private EditText nameEditeTex, messageEditeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_me);

        sendButton = findViewById(R.id.sendBtnId);
        clearBtn = findViewById(R.id.clearBtnId);
        nameEditeTex= findViewById(R.id.nameEditeText);
        messageEditeText= findViewById(R.id.messageEditeTextId);

        sendButton.setOnClickListener(this);
        clearBtn.setOnClickListener(this);
        nameEditeTex.setOnClickListener(this);
        messageEditeText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        try {
            String name = nameEditeTex.getText().toString();
            String message = messageEditeText.getText().toString();

            if (v.getId() == R.id.sendBtnId) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/email");

                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"adhikari77it@gmail.com", "protap1977a@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "About Purohit Darpon Premium ");
                intent.putExtra(Intent.EXTRA_TEXT, "Name : " + name + "\n Message: " + message);
                startActivity(Intent.createChooser(intent, "Feedback with"));
            }

            if (v.getId() == R.id.clearBtnId) ;
            {
                nameEditeTex.setText("");
                messageEditeText.setText("");

            }


        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Exception : " + e, Toast.LENGTH_SHORT).show();

        }

    }
}