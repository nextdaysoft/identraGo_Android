package com.project.identranaccess.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.project.identranaccess.Activity.HomePageActivity;
import com.project.identranaccess.Adapter.VisitorDisplayDataAdapter;
import com.project.identranaccess.FragmentCommunication;
import com.project.identranaccess.R;
import com.project.identranaccess.database.Utility;
import com.project.identranaccess.model.FavData;
import com.project.identranaccess.model.VisitorData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoricalListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class HistoricalListFragment extends Fragment {

    FloatingActionButton generate_qr;
    ImageView backIv;
    Boolean clicked = true;
    private List<VisitorData> visitdataArraylist;
    private ArrayList<FavData> favData;
    private VisitorDisplayDataAdapter visitorDisplayDataAdapter;
    RecyclerView RV_display_visitorData;
    Context mContext;
    TextView favList;
    FragmentCommunication fragmentCommunication;
    EditText search;
    FirebaseFirestore db;
    VisitorData visitorData;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    DialogFragment loader;

    public HistoricalListFragment() {
        // Required empty public constructor
    }

    public static HistoricalListFragment newInstance(String param1, String param2) {
        HistoricalListFragment fragment = new HistoricalListFragment();
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
        View view = inflater.inflate(R.layout.fragment_historical_list, container, false);
        init(view);




        favList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                FavListFragment llf = new FavListFragment();
                ft.replace(R.id.fragment_container, llf);
                ft.commit();
            }
        });

        generate_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                VisitorFragment llf = new VisitorFragment();
                ft.replace(R.id.fragment_container, llf);
                ft.commit();
            }
        });

        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, HomePageActivity.class);
                startActivity(i);
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                   visitorDisplayDataAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return view;
    }


    public void init(View view) {
        mContext = getActivity();
        generate_qr = view.findViewById(R.id.generate_qr);
        backIv = view.findViewById(R.id.backIv);
        favList = view.findViewById(R.id.favList);
        search = view.findViewById(R.id.search);
        RV_display_visitorData = view.findViewById(R.id.RV_display_visitorData);
        db = FirebaseFirestore.getInstance();
        visitdataArraylist = new ArrayList<>();


        db.collection("createNewUser")
                .whereEqualTo("id", Utility.getUserId(requireActivity(), "userId"))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            visitdataArraylist = task.getResult().toObjects(VisitorData.class);

                            if(visitdataArraylist!=null) {
                                Collections.reverse(visitdataArraylist);
                                visitorDisplayDataAdapter = new VisitorDisplayDataAdapter(visitdataArraylist, mContext);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
                                RV_display_visitorData.setLayoutManager(linearLayoutManager);
                                RV_display_visitorData.setAdapter(visitorDisplayDataAdapter);
                             //   Log.e("sdjgbsjgsbg", visitdataArraylist.get(0).getName() + "");
                            }

                        }
                    }
                });
      /*  docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });*/
    }

    @Override
    public void onResume() {
        super.onResume();

    }

}