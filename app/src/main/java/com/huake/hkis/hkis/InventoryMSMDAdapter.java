package com.huake.hkis.hkis;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.huake.hkis.hkis.model.CheckDetail;
import com.huake.hkis.hkis.model.ShelvesDetail;

import java.util.ArrayList;
import java.util.List;

class InventoryMSMDAdapter extends RecyclerView.Adapter<InventoryMSMDAdapter.SimpleViewHolder> {

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
    private List<CheckDetail> datas;
    private SparseBooleanArray selectedItems;

    public InventoryMSMDAdapter(Context context, List<CheckDetail> datas) {
        this.context = context;
        this.datas = datas;
        this.selectedItems = new SparseBooleanArray();
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    public void clearSelectedState() {
        List<Integer> selection = getSelectedItems();
        selectedItems.clear();
        for (Integer i : selection) {
            notifyItemChanged(i);
        }
    }

    public boolean isSelected(int position) {
        return getSelectedItems().contains(position);
    }

    public void switchSelectedState(int position) {
        if (selectedItems.get(position, false)) {
            selectedItems.delete(position);
        } else {
            selectedItems.put(position, true);
        }
        //notifyItemChanged(position);
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); ++i) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SimpleViewHolder holder = new SimpleViewHolder(LayoutInflater.from(
                context).inflate(R.layout.pd_material_detail_item2, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder holder, int position) {
        holder.wlhTv.setText(datas.get(position).getMaterialNO());
        holder.wlmTv.setText(datas.get(position).getMaterialNO());
        holder.unitTv.setText(datas.get(position).getCalculateUnit());
        holder.zmTv.setText(datas.get(position).getRepertoryAmount());

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

        ImageView selectImg;
        TextView wlhTv;
        TextView wlmTv;
        TextView unitTv;
        TextView zmTv;
        EditText realTv;

        public SimpleViewHolder(View view) {
            super(view);
            wlhTv = (TextView) view.findViewById(R.id.tv_wlh);
            wlmTv = (TextView) view.findViewById(R.id.tv_wlm);
            unitTv = (TextView) view.findViewById(R.id.tv_unit);
            zmTv = (TextView) view.findViewById(R.id.tv_kc);
            realTv = (EditText) view.findViewById(R.id.tv_real);
            selectImg = (ImageView) view.findViewById(R.id.select_img);
        }
    }
}