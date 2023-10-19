package com.project.identranaccess.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.identranaccess.Activity.HomePageActivity;
import com.project.identranaccess.Adapter.VisitorDisplayDataAdapter;
import com.project.identranaccess.FragmentCommunication;
import com.project.identranaccess.R;
import com.project.identranaccess.database.MyLocalDatabase;
import com.project.identranaccess.model.FavData;
import com.project.identranaccess.model.VisitorData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoricalListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoricalListFragment extends Fragment {

    FloatingActionButton generate_qr;
    ImageView backIv;
    Boolean clicked = true;
    private ArrayList<VisitorData> visitdataArraylist;
    private ArrayList<FavData> favData;
    private MyLocalDatabase dbHandler;
    private VisitorDisplayDataAdapter visitorDisplayDataAdapter;
    RecyclerView RV_display_visitorData;
    Context mContext;
    TextView favList;
    FragmentCommunication fragmentCommunication;
    EditText search;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

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

        Collections.reverse(visitdataArraylist);
        visitorDisplayDataAdapter = new VisitorDisplayDataAdapter(visitdataArraylist, mContext,favData);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        RV_display_visitorData.setLayoutManager(linearLayoutManager);
        RV_display_visitorData.setAdapter(visitorDisplayDataAdapter);




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
            //    getActivity().finish();
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
        dbHandler = new MyLocalDatabase(mContext);
        //if (dbHandler.visitorData() != null) {
        visitdataArraylist = dbHandler.visitorData();
         favData = dbHandler.fav_listData();
        //  }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

}