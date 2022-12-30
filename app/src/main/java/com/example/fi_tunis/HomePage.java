package com.example.fi_tunis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {


    RecyclerView states,monuments,acc,restaurants,random,best;
    FloatingActionButton FAB;
    ScrollView scrollView;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        FAB_Visibility();
        getStates();
        getMonuments();
        getAccommodations();
        getRestaurants();
        getRandom();

        BestPicks();
    }

    private void FAB_Visibility(){
        FAB=findViewById(R.id.floatingbutton);
        FAB.setVisibility(View.GONE);

        scrollView = findViewById(R.id.HomePageScroll);
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if ((scrollView.getChildAt(0).getBottom() < (scrollView.getHeight() + scrollView.getScrollY()))) {
                    FAB.setVisibility(View.VISIBLE);
                }else{
                    FAB.setVisibility(View.GONE);
                }
            }
        });
    }

    private void BestPicks(){
        Integer[] images={R.drawable.bestfood,R.drawable.best_autumn,R.drawable.bestinspring,R.drawable.bestinsummer,R.drawable.bestinwinter};

        best = findViewById(R.id.recycler);
        best.setHasFixedSize(true);
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        best.setLayoutManager(horizontalLayoutManagaer);
        best.setAdapter(new BestPicksRecyclerAdapter(images,HomePage.this, SearchPage.class));
        best.setItemAnimator(new DefaultItemAnimator());//set item animator to DefaultAnimator
    }

    private void getStates() {
        db.collection("states").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                if (!documentSnapshots.isEmpty()) {
                    List<State> list = documentSnapshots.toObjects(State.class);
                    String[] names = new String[list.size()];
                    String[] images = new String[list.size()];
                    for (int i = 0; i < list.size(); i++) {
                        names[i] = list.get(i).name;
                        images[i]= list.get(i).images.get(0);
                    }

                    states = findViewById(R.id.states);
                    states.setHasFixedSize(true);
                    LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                    states.setLayoutManager(horizontalLayoutManagaer);
                    states.setAdapter(new MyRecyclerAdapter(names, names, images, HomePage.this, ItemPage.class, "states"));
                    states.setItemAnimator(new DefaultItemAnimator());//set item animator to DefaultAnimator

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                ShowToast("Error! " + e.getMessage());
            }
        });

    }

    private void getMonuments(){
        db.collection("monuments").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                if (!documentSnapshots.isEmpty()) {
                    List<Monument> list = documentSnapshots.toObjects(Monument.class);
                    String[] ids = new String[list.size()];
                    String[] names = new String[list.size()];
                    String[] images = new String[list.size()];
                    for (int i = 0; i < list.size(); i++) {
                        ids[i] = list.get(i).id;
                        names[i] = list.get(i).name;
                        images[i]= list.get(i).images.get(0);
                    }

                    monuments = findViewById(R.id.monuments);
                    monuments.setHasFixedSize(true);
                    LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                    monuments.setLayoutManager(horizontalLayoutManagaer);
                    monuments.setAdapter(new MyRecyclerAdapter(ids, names, images, HomePage.this, ItemPage.class,"monuments"));
                    monuments.setItemAnimator(new DefaultItemAnimator());//set item animator to DefaultAnimator

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                ShowToast("Error! " + e.getMessage());
            }
        });
    }

    private void getAccommodations(){
        db.collection("accommodations").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                if (!documentSnapshots.isEmpty()) {
                    List<Accommodation> list = documentSnapshots.toObjects(Accommodation.class);
                    String[] ids = new String[list.size()];
                    String[] names = new String[list.size()];
                    String[] images = new String[list.size()];
                    for (int i = 0; i < list.size(); i++) {
                        ids[i] = list.get(i).id;
                        names[i] = list.get(i).name;
                        images[i]= list.get(i).images.get(0);
                    }

                    acc = findViewById(R.id.accommodations);
                    acc.setHasFixedSize(true);
                    LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                    acc.setLayoutManager(horizontalLayoutManagaer);
                    acc.setAdapter(new MyRecyclerAdapter(ids, names, images, HomePage.this, ItemPage.class,"accommodations"));
                    acc.setItemAnimator(new DefaultItemAnimator());//set item animator to DefaultAnimator
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                ShowToast("Error! " + e.getMessage());
            }
        });
    }

    private void getRestaurants(){
        db.collection("restaurants").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                if (!documentSnapshots.isEmpty()) {
                    List<Restaurant> list = documentSnapshots.toObjects(Restaurant.class);
                    String[] ids = new String[list.size()];
                    String[] names = new String[list.size()];
                    String[] images = new String[list.size()];

                    for (int i = 0; i < list.size(); i++) {
                        ids[i] = list.get(i).id;
                        names[i] = list.get(i).name;
                        images[i]= list.get(i).images.get(0);
                    }

                    restaurants = findViewById(R.id.restaurants);
                    restaurants.setHasFixedSize(true);
                    LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                    restaurants.setLayoutManager(horizontalLayoutManagaer);
                    restaurants.setAdapter(new MyRecyclerAdapter(ids, names, images, HomePage.this, ItemPage.class, "restaurants"));
                    restaurants.setItemAnimator(new DefaultItemAnimator());//set item animator to DefaultAnimator
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                ShowToast("Error! " + e.getMessage());
            }
        });
    }

    private void getRandom(){
        db.collection("random").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                if (!documentSnapshots.isEmpty()) {
                    List<RandomPics> list = documentSnapshots.toObjects(RandomPics.class);
                    String[] images = new String[list.size()];

                    for (int i = 0; i < list.size(); i++) {
                        images[i] = list.get(i).image;
                    }

                    random = findViewById(R.id.randompics);
                    random.setHasFixedSize(true);
                    random.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                    random.setAdapter(new MyGridAdapter(HomePage.this,images));
                    random.setItemAnimator(new DefaultItemAnimator());//set item animator to DefaultAnimator

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                ShowToast("Error! " + e.getMessage());
            }
        });
    }

    private void ShowToast(String msg){
        Toast.makeText(HomePage.this, msg, Toast.LENGTH_SHORT).show();
    }

    public void SearchPage(View view) {
        Intent i = new Intent(getApplicationContext(), SearchPage.class);
        startActivity(i);
    }

    public void ScrollUp(View view) {
        scrollView.smoothScrollTo(0,0);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        this.recreate();
    }
}