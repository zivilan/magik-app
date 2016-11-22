package com.kaltura.playkitdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by itanbarpeled on 13/11/2016.
 */

class SubMenuRecyclerAdapter extends RecyclerView.Adapter<SubMenuRecyclerAdapter.DataObjectHolder> {

    private ArrayList<String> mDataSet;
    private MenuRecyclerAdapter.MenuClickListener mClickListener;
    private int mLayoutId;

    private int mSelectedItem;


    SubMenuRecyclerAdapter(ArrayList<String> myDataSet, MenuRecyclerAdapter.MenuClickListener clickListener, int layoutId) {
        mDataSet = myDataSet;
        mClickListener = clickListener;
        mLayoutId = layoutId;
        mSelectedItem = 0; // the behaviour is to play the first player variant as the default
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);

        return new DataObjectHolder(view);
    }


    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.subMenuTitle.setText(mDataSet.get(position));
        holder.radioButton.setChecked(position == mSelectedItem);
    }



    @Override
    public int getItemCount() {
        return mDataSet.size();
    }


    // TODO does this inner class should be static
    class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Context context;
        TextView subMenuTitle;
        RadioButton radioButton;


        DataObjectHolder(View itemView) {

            super(itemView);

            context = itemView.getContext();
            subMenuTitle = (TextView) itemView.findViewById(R.id.sub_menu_title);
            radioButton = (RadioButton) itemView.findViewById(R.id.sub_menu_radio);
            itemView.setOnClickListener(this);
            radioButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mSelectedItem = getAdapterPosition();
            notifyItemRangeChanged(0, mDataSet.size());
            mClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

}