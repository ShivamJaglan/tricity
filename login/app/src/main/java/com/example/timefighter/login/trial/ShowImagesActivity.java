package com.example.timefighter.login.trial;

//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//
//import com.example.timefighter.login.R;
//
//public class ShowImagesActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_show_images);
//    }
//}


        import android.app.ProgressDialog;
//        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
//        import android.support.v7.widget.LinearLayoutManager;
//        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.SearchView;
        import android.widget.Toast;

        import androidx.appcompat.app.AppCompatActivity;
        import androidx.appcompat.widget.Toolbar;
        import androidx.fragment.app.Fragment;
        import androidx.recyclerview.widget.GridLayoutManager;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import com.example.timefighter.login.R;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

        import java.util.ArrayList;
        import java.util.List;
        import java.util.zip.Inflater;
        import androidx.fragment.app.FragmentActivity;

public class ShowImagesActivity extends Fragment implements SearchView.OnQueryTextListener{
    //recyclerview object
    private RecyclerView recyclerView;

    //adapter object
//    private RecyclerView.Adapter adapter;

    //database reference
    private DatabaseReference mDatabase;


    //progress dialog
    private ProgressDialog progressDialog;

    //list to hold all the uploaded images
    private List<Upload> uploads;
    private MyAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_show_images, container, false);}

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_show_images);
    @Override
        public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View v=getView();
//        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbarmenu);
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbarnavigation);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);




        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerVieww);
        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this)); ye nhi
//        recyclerView.setLayoutManager(new GridLayoutManager(ShowImagesActivity.this, 2));
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));



//        progressDialog = new ProgressDialog(this);
        progressDialog = new ProgressDialog(getActivity());

        uploads = new ArrayList<>();

        //displaying progress dialog while fetching images
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);

        //adding an event listener to fetch values
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //dismissing the progress dialog
                progressDialog.dismiss();

                //iterating through all the values in database
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    uploads.add(upload);
                }
                //creating adapter
                adapter = new MyAdapter(getActivity().getApplicationContext(), uploads);

                //adding adapter to recyclerview
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(getActivity().getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
//        MenuInflater menuInflater=getMenuInflater(); upar void ki jgh boolean
        menuInflater.inflate(R.menu.menuincludingsearch,menu);
        MenuItem.OnActionExpandListener onActionExpandListener= new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                return true;
            }
        };
        menu.findItem(R.id.app_bar_search).setOnActionExpandListener(onActionExpandListener);
        SearchView searchView=(SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setQueryHint("Search.....");
        searchView.setOnQueryTextListener(this);

//        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        String userInput= s.toLowerCase();
        List<Upload> newList= new ArrayList<>();
        for(Upload itemmm: uploads)
        {
            if(itemmm.getName().toLowerCase().contains(userInput))
            {
                newList.add(itemmm);
            }
        }
        adapter.updateList(newList);


        return false;
    }
}