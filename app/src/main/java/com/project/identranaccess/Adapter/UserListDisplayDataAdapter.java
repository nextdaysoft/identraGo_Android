package com.project.identranaccess.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.identranaccess.Activity.CreateUserListActivity;
import com.project.identranaccess.R;
import com.project.identranaccess.model.UserListModel;

import java.util.ArrayList;

public class UserListDisplayDataAdapter extends RecyclerView.Adapter<UserListDisplayDataAdapter.ViewHolder> {
    private ArrayList<UserListModel> userListModels;
    private Context context;
    public UserListDisplayDataAdapter(ArrayList<UserListModel> userList, Context mContext) {
        this.userListModels = userList;
        this.context = mContext;
    }

    @NonNull
    @Override
    public UserListDisplayDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.userlisst, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListDisplayDataAdapter.ViewHolder holder, int position) {
        UserListModel modal = userListModels.get(position);
        holder.email.setText(modal.getEmail());
        String combinetext= modal.getName()+modal.getLastName();
        holder.name.setText(combinetext);
    }

    @Override
    public int getItemCount() {
        return userListModels.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,email;
        LinearLayout profileLL;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
          //  lastName=itemView.findViewById(R.id.lastName);
            email=itemView.findViewById(R.id.email);
            profileLL=itemView.findViewById(R.id.profileLL);
            profileLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.getContext().startActivity(new Intent(context, CreateUserListActivity.class));
                }
            });
        }
    }
}

