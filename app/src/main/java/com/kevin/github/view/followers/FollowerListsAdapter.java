package com.kevin.github.view.followers;

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
import com.kevin.github.helper.BaseConst;
import com.kevin.github.model.UserResultResponse;
import com.kevin.github.R;
import com.kevin.github.view.detail.DetailActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FollowerListsAdapter extends RecyclerView.Adapter<FollowerListsAdapter.ViewHolder> {

    private List<UserResultResponse> mInfo_Users;
    private final Context mContext;

    public FollowerListsAdapter(Context baseContext) {
        this.mContext = baseContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mInfo_Users.get(position));
        holder.item.setOnClickListener(v -> {
            Intent toDetail = new Intent(mContext, DetailActivity.class);
            toDetail.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            Bundle bundle = new Bundle();
            bundle.putString(BaseConst.DATA_KEY, mInfo_Users.get(position).getLogin());
            toDetail.putExtras(bundle);

            mContext.startActivity(toDetail);
        });
    }

    @Override
    public int getItemCount() {
        return mInfo_Users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CircleImageView imgAvatar;
        private final TextView username;
        private final TextView typeUser;
        private final CardView item;

        public ViewHolder(View itemView) {
            super(itemView);

            imgAvatar = itemView.findViewById(R.id.civ_search);
            username = itemView.findViewById(R.id.usernameValue_listSearch);
            typeUser = itemView.findViewById(R.id.typeUserValue_listSearch);
            item = itemView.findViewById(R.id.cardListSearch);
        }

        public void bind(UserResultResponse userResultResponse) {
            Picasso.get().load(userResultResponse.getAvatarUrl())
                    .placeholder(R.drawable.ic_profile)
                    .into(imgAvatar);
            username.setText(userResultResponse.getLogin());
            typeUser.setText(String.valueOf(userResultResponse.getType()));
        }
    }

    public void setData(List<UserResultResponse> infoUser) {
        this.mInfo_Users = infoUser;
        notifyDataSetChanged();
    }

    public void clearList(List<UserResultResponse> clearListUser) {
        this.mInfo_Users = clearListUser;
        this.mInfo_Users.clear();
        notifyDataSetChanged();
    }
}
