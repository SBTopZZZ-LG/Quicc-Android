package com.sbtopzzz.quicc.Activities.UserViewEventActivity_Objects;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sbtopzzz.quicc.R;

import java.util.List;

public class OrganizersAdapter extends RecyclerView.Adapter<OrganizersAdapter.ViewHolder> {
    private Context context;
    private List<Organizer> organizers;

    public OrganizersAdapter(@NonNull Context context, List<Organizer> organizers) {
        this.context = context;
        this.organizers = organizers;
    }

    @NonNull
    @Override
    public OrganizersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.uservieweventactivity_organizers_tile1, parent, false);
        OrganizersAdapter.ViewHolder viewHolder = new OrganizersAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrganizersAdapter.ViewHolder holder, int position) {
        final Organizer organizer = organizers.get(position);

        holder.tvUserName.setText(organizer.getUserName());
        holder.tvUserTitle.setText(organizer.getUserTitle());
    }

    @Override
    public int getItemCount() {
        return organizers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvUserName, tvUserTitle;

        public ViewHolder(View itemView) {
            super(itemView);

            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvUserTitle = itemView.findViewById(R.id.tvUserTitle);
        }
    }
}
