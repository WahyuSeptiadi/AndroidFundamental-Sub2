package com.wahyu.githubapi.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.wahyu.githubapi.constant.Base;
import com.wahyu.githubapi.Model.SearchUserInfo;
import com.wahyu.githubapi.R;
import com.wahyu.githubapi.View.DetailActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wahyu_septiadi on 28, June 2020.
 * Visit My GitHub --> https://github.com/WahyuSeptiadi
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<SearchUserInfo> mInfo_Users;
    private Context mContext;

    public ListAdapter(Context baseContext) {
        this.mContext = baseContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_itemlist, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.bind(mInfo_Users.get(position));
        holder.item.setOnClickListener(v -> {
            Intent toDetail = new Intent(mContext, DetailActivity.class);
            toDetail.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            Bundle bundle = new Bundle();
            bundle.putString(Base.DATA_KEY, mInfo_Users.get(position).getLogin());
            toDetail.putExtras(bundle);

            mContext.startActivity(toDetail);
        });
    }

    @Override
    public int getItemCount() {
        return mInfo_Users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView imgAvatar;
        private TextView username, typeUser, idUser;
        private CardView item;

        public ViewHolder(View itemView) {
            super(itemView);

            imgAvatar = itemView.findViewById(R.id.civ_search);
            username = itemView.findViewById(R.id.usernameValue_listSearch);
            typeUser = itemView.findViewById(R.id.typeUserValue_listSearch);
            idUser = itemView.findViewById(R.id.idUserValue_listSearch);
            item = itemView.findViewById(R.id.cardListSearch);
        }

        public void bind(SearchUserInfo searchUserInfo) {
            Picasso.get().load(searchUserInfo.getAvatarUrl())
                    .placeholder(R.drawable.ic_profile)
                    .into(imgAvatar);
            username.setText(searchUserInfo.getLogin());
            typeUser.setText(String.valueOf(searchUserInfo.getType()));
            idUser.setText(String.valueOf(searchUserInfo.getId()));
        }
    }

    public void setData(List<SearchUserInfo> infoUser){
        this.mInfo_Users = infoUser;
        notifyDataSetChanged();
    }

    public void clearList(List<SearchUserInfo> clearListUser){
        this.mInfo_Users = clearListUser;
        this.mInfo_Users.clear();
        notifyDataSetChanged();
    }
}
