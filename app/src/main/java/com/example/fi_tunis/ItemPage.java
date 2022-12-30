package com.example.fi_tunis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class ItemPage extends AppCompatActivity {

    TextView Name,Description,Appbar;
    RecyclerView Images;

    String map_url,collection,id;


    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_page);

        Name=findViewById(R.id.name);
        Description=findViewById(R.id.description);
        Appbar=findViewById(R.id.appbar);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
             collection = extras.getString("type");
             id = extras.getString("id");
        }

        getData();
    }


    private void getData(){
        DocumentReference docRef = db.collection(collection).document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Name.setText(document.get("name").toString());
                        Appbar.setText(document.get("name").toString());
                        Description.setText(document.get("description").toString());
                        map_url=document.get("map").toString();
                        List<String> list = (List<String>) document.get("images");

                        String[] images= new String[list.size()];
                        for(int i=0;i<list.size();i++)
                            images[i]=list.get(i);

                        Images=findViewById(R.id.photos);
                        Images.setHasFixedSize(true);
                        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                        Images.setLayoutManager(horizontalLayoutManagaer);
                        Images.setAdapter(new ItemPageRecyclerAdapter(images, ItemPage.this));
                        Images.setItemAnimator(new DefaultItemAnimator());//set item animator to DefaultAnimator
                    } else {
                        ShowToast("No Such Document");
                    }
                } else {
                    ShowToast(task.getException().getMessage());
                }
            }
        });


    }

    public void GoToMap(View view){
        String uri = String.format(Locale.ENGLISH, "geo:"+map_url);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }

    public void SearchPage(View view) {
        Intent i = new Intent(getApplicationContext(), SearchPage.class);
        startActivity(i);
    }

    private void ShowToast(String msg){
        Toast.makeText(ItemPage.this, msg, Toast.LENGTH_SHORT).show();
    }

    public void goback(View view){
        ItemPage.this.finish();
    }
}