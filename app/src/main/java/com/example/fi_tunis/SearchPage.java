package com.example.fi_tunis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class SearchPage extends AppCompatActivity {

    EditText edittext;
    RecyclerView RV;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    int pos=-1;
    String table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

        init();


    }

    private void init(){
        edittext=findViewById(R.id.searchET);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            pos = extras.getInt("pos");
            switch (pos){
                case 0:
                    table="BestFood";
                    break;
                case 1:
                    table="BestInAutumn";
                    break;
                case 2:
                    table="BestInSpring";
                    break;
                case 3:
                    table="BestInSummer";
                    break;
                case 4:
                    table="BestInWinter";
                    break;
            }
        }else{
            edittext.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
            imm.showSoftInput(edittext, InputMethodManager.SHOW_IMPLICIT);
        }

        if (pos>-1){
            db.collection(table).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot documentSnapshots) {
                    if (!documentSnapshots.isEmpty()) {
                        List<RandomPics> list = documentSnapshots.toObjects(RandomPics.class);
                        String[] images = new String[list.size()];

                        for (int i = 0; i < list.size(); i++) {
                            images[i] = list.get(i).image;
                        }

                        RV = findViewById(R.id.RV);
                        RV.setHasFixedSize(true);
                        RV.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                        RV.setAdapter(new MyGridAdapter(SearchPage.this,images));
                        RV.setItemAnimator(new DefaultItemAnimator());//set item animator to DefaultAnimator

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    ShowToast("Error! " + e.getMessage());
                }
            });
        }
    }

    public void Search(View v){
            if(edittext.getText().toString().isEmpty()){
                ShowToast("Please Type Something To Search");
            }else{
                RV.removeAllViews();
                db.collection("accommodations").whereArrayContains("tags","WIFI").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (!documentSnapshots.isEmpty()) {
                            List<Accommodation> list = documentSnapshots.toObjects(Accommodation.class);
                            String[] images = new String[list.size()];
                            String[] names = new String[list.size()];

                            for (int i = 0; i < list.size(); i++) {
                                images[i] = list.get(i).images.get(0);
                                names[i] = list.get(i).name;
                            }

                            RV = findViewById(R.id.RV);
                            RV.setHasFixedSize(true);
                            LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                            RV.setLayoutManager(horizontalLayoutManagaer);
                            RV.setAdapter(new DiscoverAdapter( SearchPage.this,names, images));
                            RV.setItemAnimator(new DefaultItemAnimator());//set item animator to DefaultAnimator

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        ShowToast("Error! " + e.getMessage());
                    }
                });
            }

    }

    private void ShowToast(String msg){
        Toast.makeText(SearchPage.this, msg, Toast.LENGTH_SHORT).show();
    }

    public  void GoBack(View v){
        SearchPage.this.finish();
    }
}