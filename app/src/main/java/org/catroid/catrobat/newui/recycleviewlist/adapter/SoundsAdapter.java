package org.catroid.catrobat.newui.recycleviewlist.adapter;

import android.support.v7.widget.RecyclerView;

import org.catroid.catrobat.newui.R;
import org.catroid.catrobat.newui.data.SoundInfo;

import java.util.ArrayList;

public class SoundsAdapter extends RecyclerViewAdapter<SoundInfo> {

    public SoundsAdapter(ArrayList<SoundInfo> soundInfos, int listItem) {
        super(soundInfos, listItem);
    }

    @Override
    public void bindDataToViewHolder(SoundInfo item, ViewHolder holder, boolean isSelected) {
        holder.mNameView.setText(item.getName());
        holder.mDetailsView.setText("Duration: " + item.getDuration());

        if (isSelected) {
            holder.mImageView.setImageResource(R.drawable.ic_check_circle_black_24dp);
        } else {
            holder.mImageView.setImageResource(item.getThumbnailResource());
        }
    }
}