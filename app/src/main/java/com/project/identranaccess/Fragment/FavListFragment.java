package com.project.identranaccess.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.project.identranaccess.Activity.HomePageActivity;
import com.project.identranaccess.Adapter.FavListDisplayDataAdapter;
import com.project.identranaccess.Adapter.VisitorDisplayDataAdapter;
import com.project.identranaccess.R;
import com.project.identranaccess.database.Utility;
import com.project.identranaccess.model.FavData;
import com.project.identranaccess.model.VisitorData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavListFragment extends Fragment {
    RecyclerView recyclerView_fav;
    ImageView backIv;
    FavListDisplayDataAdapter favListDisplayDataAdapter;
    private List<FavData> favdataArraylist;
    //  private MyLocalDatabase dbHandler;
    Context context;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FirebaseFirestore db;

    public FavListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavListFragment newInstance(String param1, String param2) {
        FavListFragment fragment = new FavListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        db = FirebaseFirestore.getInstance();

        View view = inflater.inflate(R.layout.fragment_fav_list, container, false);
        recyclerView_fav = view.findViewById(R.id.recyclerView_fav);
        backIv = view.findViewById(R.id.backIv);
        context = getActivity();

        db.collection("createNewUser")
                .whereEqualTo("id", Utility.getUserId(requireActivity(), "userId"))
                .whereEqualTo("clicked",true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            favdataArraylist = task.getResult().toObjects(FavData.class);

                            if (favdataArraylist != null) {
                                Collections.reverse(favdataArraylist);
                                favListDisplayDataAdapter = new FavListDisplayDataAdapter(favdataArraylist, context);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
                                recyclerView_fav .setLayoutManager(linearLayoutManager);
                                recyclerView_fav.setAdapter(favListDisplayDataAdapter);
                              //  Log.e("sdjgbsjgsbg", visitdataArraylist.get(0).getName() + "");
                            }

                        }
                    }
                });
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, HomePageActivity.class);
                startActivity(i);
            }
        });

     /*   Collections.reverse(favdataArraylist);
        favListDisplayDataAdapter = new FavListDisplayDataAdapter(favdataArraylist, context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        recyclerView_fav.setLayoutManager(linearLayoutManager);
        recyclerView_fav.setAdapter(favListDisplayDataAdapter);*/

        return view;
    }
}