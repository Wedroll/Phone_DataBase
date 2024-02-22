package com.example.phone_database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ElementListAdapter extends RecyclerView.Adapter<ElementListAdapter.ElementViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<Element> mElementList;
    private OnItemClickListener mItemClickListener;

    public ElementListAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        this.mElementList = null;
    }

    public Element getElementAtPosition(int position) {
        if (mElementList != null && position >= 0 && position < mElementList.size()) {
            return mElementList.get(position);
        }
        return null;
    }

    @NotNull
    @Override
    public ElementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item, parent, false);
        return new ElementViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NotNull ElementViewHolder holder, int position) {
        if (mElementList != null) {
            Element element = mElementList.get(position);
            holder.textView1.setText(element.getManufacturer());
            holder.textView2.setText(element.getModel());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(element);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (mElementList != null)
            return mElementList.size();
        return 0;
    }

    public void setElementList(List<Element> elementList) {
        this.mElementList = elementList;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(Element element);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mItemClickListener = listener;
    }

    public class ElementViewHolder extends RecyclerView.ViewHolder {
        public TextView textView1;
        public TextView textView2;

        public ElementViewHolder(View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);
        }
    }
}
