package com.umar.devcrewcodechallange.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.umar.devcrewcodechallange.R;
import com.umar.devcrewcodechallange.interfaces.GenericCallback;
import com.umar.devcrewcodechallange.model.UserModel;
import com.umar.devcrewcodechallange.utility.WordUtil;

import java.util.List;

/**
 * Created by UmarQasim on 9/20/2016.
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {


    List<UserModel> mListData;
    Context mContext;
    GenericCallback<Integer> mOnClickCallBackListener;

    public void setOnClickCallBackListener(GenericCallback<Integer> mOnClickCallBackListener)
    {
        this.mOnClickCallBackListener = mOnClickCallBackListener;
    }

    public UserAdapter(List<UserModel> mListData, Context mContext) {
        this.mListData = mListData;
        this.mContext = mContext;
    }

    @Override
    public UserHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        UserHolder viewHolder;
        View viewIncomming = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_user, viewGroup, false);
        viewHolder = new UserHolder(viewIncomming);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final UserHolder viewHolder, final int i) {
        final UserModel itemData = mListData.get(i);
        viewHolder.mTxtName.setText(WordUtil.capitalizeFully(itemData.getFirstName())+", "+WordUtil.capitalizeFully(itemData.getLastName()));
    }


    @Override
    public int getItemCount() {
        return mListData.size();
    }

    public UserModel getItemForIndex(int i){
        return mListData.get(i);
    }

    public class UserHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        TextView mTxtName;

        public UserHolder(View itemView) {
            super(itemView);

            mTxtName = (TextView) itemView.findViewById(R.id.item_user_txt_name);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if(mOnClickCallBackListener!=null){
                mOnClickCallBackListener.onCallback(getAdapterPosition());
            }
        }
    }

}





