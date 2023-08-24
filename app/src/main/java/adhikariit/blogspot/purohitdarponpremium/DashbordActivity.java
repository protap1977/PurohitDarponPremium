package adhikariit.blogspot.purohitdarponpremium;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.common.reflect.TypeToken;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DashbordActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    myadapter adapter;
    private FirebaseAuth mAuth;

    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashbord);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("সূচীপত্রঃ-");

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {

        } else {
            Intent intent = new Intent(DashbordActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        }

        recyclerView = findViewById(R.id.recView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseFirestore.getInstance();

        FirebaseRecyclerOptions<model> options = new FirebaseRecyclerOptions.Builder<model>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("mypdf"), model.class)
                .build();
        adapter = new myadapter(options);
        recyclerView.setAdapter(adapter);

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
                .setPersistenceEnabled(true) //enable offline persistence.
                .build();

        db.setFirestoreSettings(settings);

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("mypdf");
        ref.keepSynced(true);


    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.searchId);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search heare");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                prosesssearch(newText);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void prosesssearch(String newText) {

        FirebaseRecyclerOptions<model> options = new FirebaseRecyclerOptions.Builder<model>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("mypdf").orderByChild("filename").startAt(newText).endAt(newText + "\uf8ff"), model.class)
                .build();
        adapter = new myadapter(options);
        adapter.startListening();

        recyclerView.setAdapter(adapter);
    }


    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.shareId:
                Toast.makeText(this, "Sharing...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String subject = "Purohit Darpon Premium";
                String body = "Purohit Darpon Premium app টি সমায়োপযোগী একটি অধুনিক পূজা পদ্ধতি সহায়িকা মোবাইল এপ্লিকেশন। ডিজিটাল এই সমায়ে" +
                        " আপনি যখন সবকিছু আপনার মোবাইল বা ট্যাবে সবকিছু ঠিক ঠাক মতো করেত চান বইয়ের পাতা উল্টাতে এবং বই টানাটানিতে আপনি ক্লান্ত তখনেই আমরা নিয়ে এলাম আপনার জন্য" +
                        " Purohit Darpon Premium এ্যাপ খানা। এই এ্যাপ টিতে পন্ডিত সুরেন্দ্র মোহন ভট্টাচায্র্য সংকলিত পূরোহীত দর্পণ পুস্তক খানির পিডিএফ থেকে মোবাইল ই-বুক তৈরী করেছি" +
                        " যা আপনার মোবাইলের কিছু এমিবর জায়গায় গোটা পুস্তক খানি অনায়াসে ব্যাবহার করেত পারবেন।" +
                        " Purohit Darpon Premium এ্যাপ খানি প্রিমিয়াম হওয়ার জন্য এখানে কোন প্রকার  এ্যড দেওয়া হয় নাই" +
                        "     Purohit Darpon Premium আপনার জন্য এবং বিশেষ করে পূরোহীত মহাশয়ের জন্য খুবই প্রয়োজন তাই আজই ডাউনলোড করে রেখেদিতে পারেন-\n" +
                        "ডাউনলোড লিং-\n adhikariit.blogspot.purohitdarponpremium";

                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                intent.putExtra(Intent.EXTRA_TEXT, body);
                startActivity(Intent.createChooser(intent, "share with"));
                finish();
                break;

            case R.id.contactMeId:
                Toast.makeText(this, "Contact me", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DashbordActivity.this, ContactMe.class));
                finish();
                break;

            case R.id.ratemeId: {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=" + "adhikariit.blogspot.purohitdarponpremium")));

                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id" + getOpPackageName())));
                    finish();
                }
            }

            break;

            case R.id.privecyId:
                Toast.makeText(this, "Privacy Policy", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DashbordActivity.this, PrivacyPolicy.class));
                finish();
                break;


            case R.id.logoutId:
                Toast.makeText(this, "Log Out", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(DashbordActivity.this, MainActivity.class));
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder;
        alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setIcon(R.drawable.book);
        alertDialogBuilder.setTitle(R.string.app_name);
        alertDialogBuilder.setMessage(R.string.MTitle);
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton("হ্যাঁ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();

            }
        });
        alertDialogBuilder.setNegativeButton("না", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}