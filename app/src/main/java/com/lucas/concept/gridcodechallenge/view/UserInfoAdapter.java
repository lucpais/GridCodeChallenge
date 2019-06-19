package com.lucas.concept.gridcodechallenge.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import com.lucas.concept.gridcodechallenge.R;
import com.lucas.concept.gridcodechallenge.controller.UsersController;
import com.lucas.concept.gridcodechallenge.controller.IItemClickListener;
import com.lucas.concept.gridcodechallenge.model.UserInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


class UserInfoAdapter extends RecyclerView.Adapter<UserInfoAdapter.MyViewHolder> implements Filterable {
    private final Context mContext;
    private ArrayList<UserInfo> mData;
    private ArrayList<UserInfo> mDataFiltered;
    private IItemClickListener mClickListener;

    UserInfoAdapter(Context context) {
        mContext = context;
        mData = UsersController.getInstance(mContext).getData();
        mDataFiltered = mData;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mDataFiltered = mData;
                } else {
                    ArrayList<UserInfo> filteredList = new ArrayList<>();
                    for (UserInfo row : mData) {

                        if (row.getFirstName().toLowerCase().contains(charString.toLowerCase()) || row.getLastName().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }
                    mDataFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mDataFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mDataFiltered = (ArrayList<UserInfo>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView mContactThumbnail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mContactThumbnail = itemView.findViewById(R.id.user_picture);
        }

        @Override
        public void onClick(View v) {
            mClickListener.onClickDevice(v, mData.indexOf(mDataFiltered.get(getAdapterPosition())));
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contact_cell, viewGroup, false);
        UserInfoAdapter.MyViewHolder vh = new UserInfoAdapter.MyViewHolder(itemView);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Picasso.with(mContext)
                .load(mDataFiltered.get(i).getThumbUrl())
                .resize((int) mContext.getResources().getDimension(R.dimen.width_image_thumb), (int) mContext.getResources().getDimension(R.dimen.height_image_thumb))
                .error(android.R.drawable.ic_menu_camera)
                .placeholder(android.R.drawable.ic_menu_camera)
                .into(myViewHolder.mContactThumbnail);
    }

    @Override
    public int getItemCount() {
        return mDataFiltered.size();
    }

    public void setClickListener(IItemClickListener itemClickListener) {
        mClickListener = itemClickListener;
    }
}
