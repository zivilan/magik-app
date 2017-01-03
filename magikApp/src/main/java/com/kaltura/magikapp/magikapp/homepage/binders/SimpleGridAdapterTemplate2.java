package com.kaltura.magikapp.magikapp.homepage.binders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kaltura.magikapp.R;
import com.kaltura.magikapp.magikapp.homepage.ViewType;

import java.util.List;

/**
 * Created by vladir on 02/01/2017.
 */

public class SimpleGridAdapterTemplate2 extends RecyclerView.Adapter<SimpleGridAdapterTemplate2.ViewHolder>{

    private Context mContext;
    private List<String> mUrls;
    int[] mDrawableRes;
    private ItemClick mOnItemClicked;

    public SimpleGridAdapterTemplate2(Context context, int[] drawableRes){
        mContext = context;
        mDrawableRes = drawableRes;
    }

    @Override
    public SimpleGridAdapterTemplate2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        if (viewType == ViewType.Image.num){
            view = LayoutInflater.from(mContext).inflate(R.layout.fourimage_item_template2_image_layout, parent, false);
        } else if (viewType == ViewType.Player.num) {
            view = LayoutInflater.from(mContext).inflate(R.layout.fourimage_item_template2_player_layout, parent, false);
        }

        // same viewHolder same data
        return new SimpleGridAdapterTemplate2.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleGridAdapterTemplate2.ViewHolder holder, final int position) {
        Glide.with(mContext).load(mDrawableRes[position]).centerCrop().crossFade().into(holder.mImageView);
        holder.mRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClicked.onClick(position);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 2){
            return ViewType.Image.ordinal();
        }
        return ViewType.Player.ordinal();
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public void setOnClickListener(ItemClick onItemClicked) {
        mOnItemClicked = onItemClicked;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mImageView;
        View mRoot;

        public ViewHolder(View view) {
            super(view);
            mRoot = view;
            mImageView = (ImageView) view.findViewById(R.id.four_image_item_image_view);
        }
    }
}
