package com.project.identranaccess.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.project.identranaccess.Activity.HomePageActivity;
import com.project.identranaccess.Adapter.FavListDisplayDataAdapter;
import com.project.identranaccess.R;
import com.project.identranaccess.database.MyLocalDatabase;
import com.project.identranaccess.model.FavData;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavListFragment extends Fragment {
    RecyclerView recyclerView_fav;
    ImageView backIv;
    FavListDisplayDataAdapter favListDisplayDataAdapter;
    public ArrayList<FavData> favdataArraylist;
    private MyLocalDatabase dbHandler;
    Context context;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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


        View view= inflater.inflate(R.layout.fragment_fav_list, container, false);
        recyclerView_fav=view.findViewById(R.id.recyclerView_fav);
        backIv=view.findViewById(R.id.backIv);
        context=getActivity();
        dbHandler= new MyLocalDatabase(context);

        favdataArraylist=dbHandler.fav_listData();

        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, HomePageActivity.class);
                startActivity(i);
            }
        });

        Collections.reverse(favdataArraylist);
        favListDisplayDataAdapter = new FavListDisplayDataAdapter(favdataArraylist,context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        recyclerView_fav.setLayoutManager(linearLayoutManager);
        recyclerView_fav.setAdapter(favListDisplayDataAdapter);

        return view;
    }
}