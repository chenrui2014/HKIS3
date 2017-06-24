package com.huake.hkis.hkis;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huake.hkis.hkis.model.Task;

import java.util.List;

class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.SimpleViewHolder> {

    /**
     * Item 点击事件监听的回调
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickLitener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    private Context context;
    private List<Task> datas;

    public SimpleAdapter(Context context, List<Task> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SimpleViewHolder holder = new SimpleViewHolder(LayoutInflater.from(
                context).inflate(R.layout.fragment_up_putin_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, int position) {
        holder.inDateTv.setText(datas.get(position).getInStorageTime());
        holder.inBillsTv.setText(datas.get(position).getTaskNO());
        holder.wareHouseNOTv.setText(datas.get(position).getWareHouseNum());
        holder.billTypeTv.setText(datas.get(position).getDocumentsType());

        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder {

        TextView inDateTv;
        TextView inBillsTv;
        TextView wareHouseNOTv;
        TextView billTypeTv;

        public SimpleViewHolder(View view) {
            super(view);
            inDateTv = (TextView) view.findViewById(R.id.tv_inDate);
            inBillsTv = (TextView) view.findViewById(R.id.tv_inBills);
            wareHouseNOTv = (TextView) view.findViewById(R.id.tv_wareHouse_NO);
            billTypeTv = (TextView) view.findViewById(R.id.tv_billType);
        }
    }
}