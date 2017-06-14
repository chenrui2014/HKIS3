package com.huake.hkis.hkis;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huake.hkis.hkis.model.Check;
import com.huake.hkis.hkis.model.Task;

import java.util.List;

class InventorySummaryAdapter extends RecyclerView.Adapter<InventorySummaryAdapter.SimpleViewHolder> {

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
    private List<Check> datas;

    public InventorySummaryAdapter(Context context, List<Check> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SimpleViewHolder holder = new SimpleViewHolder(LayoutInflater.from(
                context).inflate(R.layout.fragment_pd_sum_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, int position) {
        String dateStr = datas.get(position).getCheckTime();
        String day="";
        String month="";

        if(!dateStr.isEmpty()){
            String[] temp = dateStr.split("-");
            if(temp.length > 2){
                day=temp[0];
                month=temp[1];
            }
        }
        holder.dayTv.setText(day);
        holder.monthTv.setText(month);
        holder.warehouseNOTv.setText(datas.get(position).getWarehouseNO());
        holder.checkNOTv.setText(datas.get(position).getCheckNO());

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

        TextView dayTv;
        TextView monthTv;
        TextView checkNOTv;
        TextView warehouseNOTv;

        public SimpleViewHolder(View view) {
            super(view);
            dayTv = (TextView) view.findViewById(R.id.tv_day);
            monthTv = (TextView) view.findViewById(R.id.tv_month);
            checkNOTv = (TextView) view.findViewById(R.id.tv_pddh);
            warehouseNOTv = (TextView) view.findViewById(R.id.tv_ckh);
        }
    }
}