package com.example.ivor_hu.meizhi.ui.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ivor_hu.meizhi.R;
import com.example.ivor_hu.meizhi.db.entity.Image;
import com.example.ivor_hu.meizhi.ui.RatioImageView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Ivor on 2016/2/6.
 */
public class GirlsAdapter extends RecyclerView.Adapter<GirlsAdapter.MyViewHolder> {
    private static final String TAG = "GirlsAdapter";

    private final Context mContext;
    private final List<Image> mImages;
    private OnItemClickListener mOnItemClickListener;

    public GirlsAdapter(Context mContext) {
        this.mContext = mContext;
        mImages = new ArrayList<>();
        setHasStableIds(true);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.girls_item, parent, false));
    }

    @Override
    public long getItemId(int position) {
        return mImages.get(position).getId().hashCode();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Image image = mImages.get(position);

        holder.imageView.setOriginalSize(image.getWidth(), image.getHeight());
        Glide.with(mContext)
                .load(image.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);
        ViewCompat.setTransitionName(holder.imageView, image.getUrl());

        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v, holder.getLayoutPosition());
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onItemLongClick(v, holder.getLayoutPosition());
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }

    public String getUrlAt(int pos) {
        return mImages.get(pos).getUrl();
    }

    public void clear() {
        mImages.clear();
    }

    public void addGirls(List<Image> girls) {
        if (girls == null) {
            return;
        }

        mImages.addAll(girls);
    }

    public List<Image> getImages() {
        return mImages;
    }

    public interface OnItemClickListener {

        void onItemClick(View view, int pos);

        void onItemLongClick(View view, int pos);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        RatioImageView imageView;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardview);
            imageView = itemView.findViewById(R.id.network_imageview);
        }

    }

}
