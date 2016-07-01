package com.hyc.zhihu.ui.adpter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hyc.zhihu.R;
import com.hyc.zhihu.widget.PureColorCircleView;

import java.util.List;

/**
 * Created by ray on 16/5/14.
 */
public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ViewHolder> {
    private List<Integer> mColors;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    private ItemClickListener mItemClickListener;

    public ColorAdapter(List<Integer> colors) {
        mColors = colors;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_color, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.image.setColor(mColors.get(position));
        if (mItemClickListener != null) {
            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.OnItemClicked(mColors.get(position));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mColors == null ? 0 : mColors.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private PureColorCircleView image;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (PureColorCircleView) itemView.findViewById(R.id.color_ci);
        }
    }

    public interface ItemClickListener {
        void OnItemClicked(int color);
    }
}
