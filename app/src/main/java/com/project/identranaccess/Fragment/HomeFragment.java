package com.project.identranaccess.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.project.identranaccess.R;
import com.project.identranaccess.database.RetrofitClient;
import com.project.identranaccess.model.QRClass;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment  {
    Context mContext;
    LinearLayout user_list, historical_ll, new_visitID;
    ArrayList<QRClass> codelist;
    ArrayList<QRClass>myheroList;
    String json;
    String userDetails="letestuser";


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String[] qr_codes;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        codelist=new ArrayList<>();
        mContext= getActivity();
        user_list = view.findViewById(R.id.user_list);
        historical_ll = view.findViewById(R.id.historical_ll);
        new_visitID = view.findViewById(R.id.new_visitID);

        user_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                FavListFragment llf = new FavListFragment();
                ft.replace(R.id.fragment_container, llf);
                ft.commit();
            }
        });

        historical_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                HistoricalListFragment llf = new HistoricalListFragment();
                ft.replace(R.id.fragment_container, llf);
                ft.commit();
            }
        });

        new_visitID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   if(json!=null) {
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    VisitorFragment llf = new VisitorFragment();
                    Log.e("data_user", "onCreateView:data " + json);
                    ft.replace(R.id.fragment_container, llf);
                    ft.commit();
            }
        });
        return view;
    }

    public void callQrApi() {
        Call<ArrayList<QRClass>> call = RetrofitClient.getInstance().getMyApi().getAllData();
        call.enqueue(new Callback<ArrayList<QRClass>>() {
            @Override
            public void onResponse(Call<ArrayList<QRClass>> call, Response<ArrayList<QRClass>> response) {
                myheroList = response.body();
                Gson gson = new Gson();
                 json = gson.toJson(myheroList);
//                Log.v("userListData", json);

                qr_codes = new String[myheroList.size()];

            /*    for ( int i = 0; i < myheroList.size();i++ ) {
                   // codelist.add(myheroList.get(i));
                    qr_codes[i] = String.valueOf(myheroList.get(i).getCode());

                    Random random = new Random();
                    int randomIndex = random.nextInt(myheroList.size());
                    int randomNumber = Integer.parseInt(myheroList.get(randomIndex).getCode());

                 //   qrGenerateMethod(qr_codes[i]);

                    // Toast.makeText(QRPageActivity.this, qr_codes[j], Toast.LENGTH_SHORT).show();
                    Log.v("userList", qr_codes[i]);
                }*/
            }

            @Override
            public void onFailure(Call<ArrayList<QRClass>> call, Throwable t) {
            //   Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }




}