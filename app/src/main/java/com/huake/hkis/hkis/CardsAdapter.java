package com.huake.hkis.hkis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.huake.hkis.hkis.model.MaterialShelves;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by arjun on 4/25/16.
 */
public class CardsAdapter extends BaseAdapter {
  private final ArrayList<MaterialShelves> mss;
  private final LayoutInflater layoutInflater;

  private MDOnScanListener scanListener;

  public CardsAdapter(Context context, ArrayList<MaterialShelves> mss) {
    super();
    this.mss = mss;
    this.layoutInflater = LayoutInflater.from(context);
    this.scanListener = (MDOnScanListener)context;
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    MaterialShelves ms = mss.get(position);
    View view = layoutInflater.inflate(R.layout.fragment_store_info_item, parent, false);
    ((TextView) view.findViewById(R.id.tv_wlh)).setText(ms.getMaterialNO());
    ((TextView) view.findViewById(R.id.tv_desc)).setText(ms.getMaterialDesc());
    ((TextView) view.findViewById(R.id.tv_inNum)).setText(ms.getAmount());
    ((TextView) view.findViewById(R.id.tv_unit)).setText(ms.getCalculateUnit());
    ((TextView) view.findViewById(R.id.tv_suggest)).setText(ms.getSysRecommendWarehouse());
    EditText qrEt = (EditText) view.findViewById(R.id.et_qr);
    EditText storeEt = (EditText) view.findViewById(R.id.et_store);
    qrEt.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        scanListener.onScanListener(v);
      }
    });

    storeEt.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        scanListener.onScanListener(v);
      }
    });
    ((EditText) view.findViewById(R.id.et_storeNum)).setText(ms.getRecommendAmount());
    ((EditText) view.findViewById(R.id.et_boxNum)).setText(ms.getRecommendAmount());
    return view;
  }

  public void addAll(Collection<MaterialShelves> collection) {
    if (isEmpty()) {
      mss.addAll(collection);
      notifyDataSetChanged();
    } else {
      mss.addAll(collection);
    }
  }

  @Override
  public MaterialShelves getItem(int position) {
    if(mss==null ||mss.size()==0) return null;
    return mss.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }
  public void clear() {
    mss.clear();
    notifyDataSetChanged();
  }

  public boolean isEmpty() {
    return mss.isEmpty();
  }

  public void remove(int index) {
    if (index > -1 && index < mss.size()) {
      mss.remove(index);
      notifyDataSetChanged();
    }
  }


  @Override
  public int getCount() {
    return mss.size();
  }
}
