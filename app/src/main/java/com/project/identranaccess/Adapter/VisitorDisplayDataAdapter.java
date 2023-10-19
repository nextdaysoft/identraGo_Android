package com.project.identranaccess.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.project.identranaccess.Activity.QRPageActivity;
import com.project.identranaccess.Fragment.VisitorFragment;
import com.project.identranaccess.FragmentCommunication;
import com.project.identranaccess.R;
import com.project.identranaccess.model.FavData;
import com.project.identranaccess.model.VisitorData;

import java.util.ArrayList;
import java.util.List;

public class VisitorDisplayDataAdapter extends RecyclerView.Adapter<VisitorDisplayDataAdapter.ViewHolder> implements Filterable {
    private List<VisitorData> visitorData;
    private final List<VisitorData> fullList = new ArrayList<>();

    ArrayList<FavData> favData;
    private Context context;
    FirebaseFirestore db;
    boolean clicked = true;
    private FragmentCommunication mCommunicator;
    //  int isfav;


    public VisitorDisplayDataAdapter(List<VisitorData> visitdataArraylist, Context mContext) {
        this.visitorData = visitdataArraylist;
        this.favData = favData;
        this.context = mContext;
        fullList.addAll(visitdataArraylist);
        db = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public VisitorDisplayDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.historicaldatadisplay, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VisitorDisplayDataAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        VisitorData modal = visitorData.get(position);

        holder.reasonFor_ET.setText(modal.getReasonOfVisit());
        String combinetext = modal.getName() + " " + modal.getLastName();
        holder.name.setText(combinetext);

        //      for (int i = 0; i < favData.size(); i++) {
        if (modal.getClicked()) {
            holder.like_btn.setImageResource(R.drawable.heart);
        } else {
            holder.like_btn.setImageResource(R.drawable.like_button);
        }
        //  }
        Log.e("getid", "onBindViewHoldermodel:new " + modal.getName());

     /*   for (int i = 0; i < favData.size(); i++) {
            Log.e("getid", "onBindViewHolder.....: " +favData.get(i).getId());
            if (modal.getId().equalsIgnoreCase(favData.get(i).getId())) {
                holder.like_btn.setImageResource(R.drawable.heart);
         //       dbHandler.remove(modal.getCode());
            }
            else {
              //  holder.like_btn.setImageResource(R.drawable.like_button);
                // clicked = true;
                // dbHandler.addfavListData(modal.getName(), modal.getLastName(), modal.getVisitDate(), modal.getComment(), modal.getReasonofvisit(), modal.getCode());
            }

        }*/
        holder.like_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (modal.getClicked()) {
                    db.collection("createNewUser").document(modal.getUniqueId()).update("clicked", false);
                    modal.setClicked(false);
                } else {
                    db.collection("createNewUser").document(modal.getUniqueId()).update("clicked", true);
                    modal.setClicked(true);

                }

                notifyItemChanged(position, "clicked");



               /* if(!modal.getClicked()){
                        db.collection("createNewUser").document(modal.getUniqueId()).update("clicked",true);
                  //  clicked=true;
              //      holder.like_btn.setImageResource(R.drawable.heart);
                   // dbHandler.addfavListData(modal.getName(), modal.getLastName(), modal.getReasonofvisit(), modal.getVisitDate(), modal.getComment(), modal.getCode(), modal.getId());
                   *//* FavListFragment fragmentB = new FavListFragment();
                    FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.fragment_container, fragmentB);
                    ft.commit();*//*
                }*/
                //  clicked=true;
                // dbHandler.remove(modal.getCode());

               /* for (int i = 0; i < favData.size(); i++) {
                    if (modal.getId().equalsIgnoreCase(favData.get(i).getId())) {
                        //dbHandler.remove(modal.getCode());
                    }
                  else {
                         holder.like_btn.setImageResource(R.drawable.like_button);
                       // clicked = true;
                           // dbHandler.addfavListData(modal.getName(), modal.getLastName(), modal.getVisitDate(), modal.getComment(), modal.getReasonofvisit(), modal.getCode());
                    }

                }*/
            }
        });

        holder.create_visit_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VisitorFragment fragmentB = new VisitorFragment();
                FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("NAME", modal.getName());
                bundle.putString("LASTNAME", modal.getLastName());
                bundle.putString("VISITREASON", modal.getReasonOfVisit());
                bundle.putString("VISITDATE", modal.getVisitDate());
                bundle.putString("COMMENT", modal.getComment());
                bundle.putString("CODE", modal.getCode());
                fragmentB.setArguments(bundle);
                ft.replace(R.id.fragment_container, fragmentB);
                ft.commit();
            }
        });

        holder.QR_Code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //view.getContext().startActivity(new Intent(context, QRPageActivity.class));
                Intent i = new Intent(view.getContext(), QRPageActivity.class);
                i.putExtra("QRCODE", modal.getCode());
                view.getContext().startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return visitorData.size();
    }


    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                if (constraint == null || constraint.length() == 0) { // if your editText field is empty, return full list of FriendItem
                    results.count = fullList.size();
                    results.values = fullList;
                } else {
                    ArrayList<VisitorData> filteredList = new ArrayList<>();

                    constraint = constraint.toString().toLowerCase(); // if we ignore case
                    for (VisitorData item : fullList) {
                        String firstName = item.getName().toLowerCase(); // if we ignore case
                        String lastName = item.getLastName().toLowerCase(); // if we ignore case
                        if (firstName.contains(constraint.toString()) || lastName.contains(constraint.toString())) {
                            filteredList.add(item); // added item witch contains our text in EditText
                        }
                    }

                    results.count = filteredList.size(); // set count of filtered list
                    results.values = filteredList; // set filtered list
                }
                return results; // return our filtered list
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                visitorData = (ArrayList<VisitorData>) results.values; // replace list to filtered list

                notifyDataSetChanged(); // refresh adapter
            }
        };
        return filter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, lastName, reasonFor_ET, QR_Code;
        ImageView like_btn;
        LinearLayout create_visit_LL;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            reasonFor_ET = itemView.findViewById(R.id.reasonFor_ET);
            QR_Code = itemView.findViewById(R.id.QR_Code);
            create_visit_LL = itemView.findViewById(R.id.create_visit_LL);
            like_btn = itemView.findViewById(R.id.like_btn);
            if (like_btn.isClickable()) {
                like_btn.setImageResource(R.drawable.heart);
            }
        }
        /*@Override
        public void onClick(View view) {
            mCommunicator.respond(getAdapterPosition());
        }*/

    }

}

