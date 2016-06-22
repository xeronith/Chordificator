package com.xeronith.chordificator;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ChordsAdapter extends RecyclerView.Adapter<ChordsAdapter.ViewHolder> {

    private List<Note> items;
    private int itemLayout;

    public ChordsAdapter(List<Note> items, int itemLayout) {
        this.items = items;
        this.itemLayout = itemLayout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Note item = items.get(position);
        holder.text.setText(item.getName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text;
        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView)itemView.findViewById(R.id.textView);
        }
    }
}
