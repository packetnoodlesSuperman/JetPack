package com.jetpack.xhb;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class UserAdapter extends PagedListAdapter<DataBean, UserAdapter.ViewHolder> {

    public UserAdapter(Context context, DiffUtil.ItemCallback<DataBean> diffCallback) {
        super(diffCallback);
        this.context = context;
    }

    Context context;



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(View.inflate(context, R.layout.item_tv, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        DataBean data = getItem(position);
        viewHolder.text1.setText(data.name);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView text1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text1 = itemView.findViewById(R.id.item_content);
        }
    }

}
