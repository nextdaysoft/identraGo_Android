package com.project.identranaccess.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.project.identranaccess.Activity.QRPageActivity;
import com.project.identranaccess.Fragment.VisitorFragment;
import com.project.identranaccess.R;
import com.project.identranaccess.model.FavData;

import java.util.ArrayList;

public class FavListDisplayDataAdapter extends RecyclerView.Adapter<FavListDisplayDataAdapter.ViewHolder> {
    private ArrayList<FavData> userListModels;
    private Context context;
    public FavListDisplayDataAdapter(ArrayList<FavData> userList, Context mContext) {
        this.userListModels = userList;
        this.context = mContext;
    }

    @NonNull
    @Override
    public FavListDisplayDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.historicaldatadisplay, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavListDisplayDataAdapter.ViewHolder holder, int position) {
        FavData modal = userListModels.get(position);
        holder.reasonFor_ET.setText(modal.getVisitDate());
        String combinetext= modal.getName()+"  "+modal.getLastName();
        holder.like_btn.setImageResource(R.drawable.heart);
        holder.name.setText(combinetext);

        holder.create_visit_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VisitorFragment fragmentB = new VisitorFragment();
                FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("NAME", modal.getName());
                bundle.putString("LASTNAME", modal.getLastName());
                bundle.putString("VISITREASON", modal.getVisitDate());
                bundle.putString("VISITDATE", modal.getReasonofvisit());
                bundle.putString("COMMENT", modal.getComment());
                bundle.putString("CODE", modal.getCode());
                bundle.putString("FirstCODE", "1");
                fragmentB.setArguments(bundle);
                ft.replace(R.id.fragment_container, fragmentB);
                ft.commit();
            }
        });

        holder.QR_Code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), QRPageActivity.class);
                i.putExtra("QRCODE", modal.getCode());
                view.getContext().startActivity(i);
            }
        });

        Log.e("userlistDatafav", "onBindViewHolder: "+modal.getName() );
        Log.e("userlistDatafav", "onBindViewHolder: "+modal.getLastName() );
        Log.e("userlistDatafav", "onBindViewHolder: "+modal.getVisitDate() );
        Log.e("userlistDatafav", "onBindViewHolder: "+modal.getComment() );
        Log.e("userlistDatafav", "onBindViewHolder: "+modal.getCode());
    }

    @Override
    public int getItemCount() {
        return userListModels.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,lastName,reasonFor_ET,QR_Code;
        LinearLayout create_visit_LL;
        ImageView like_btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            create_visit_LL=itemView.findViewById(R.id.create_visit_LL);
            reasonFor_ET=itemView.findViewById(R.id.reasonFor_ET);
            QR_Code=itemView.findViewById(R.id.QR_Code);
            like_btn=itemView.findViewById(R.id.like_btn);
        }
    }
}

