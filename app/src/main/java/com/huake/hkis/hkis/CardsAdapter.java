package com.huake.hkis.hkis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.huake.hkis.hkis.model.MaterialShelves;

import java.util.ArrayList;

/**
 * Created by arjun on 4/25/16.
 */
public class CardsAdapter extends ArrayAdapter<MaterialShelves> {
  private final ArrayList<MaterialShelves> mss;
  private final LayoutInflater layoutInflater;

  public CardsAdapter(Context context, ArrayList<MaterialShelves> mss) {
    super(context, -1);
    this.mss = mss;
    this.layoutInflater = LayoutInflater.from(context);
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    MaterialShelves ms = mss.get(position);
    View view = layoutInflater.inflate(R.layout.fragment_store_info_item, parent, false);
    ((TextView) view.findViewById(R.id.tv_wlh)).setText(ms.getMaterialNO());
    ((TextView) view.findViewById(R.id.tv_desc)).setText(ms.getMaterialDesc());
    ((TextView) view.findViewById(R.id.tv_inNum)).setText(ms.getAmount());
    ((TextView) view.findViewById(R.id.tv_unit)).setText(ms.getCalculateUnit());
    ((TextView) view.findViewById(R.id.tv_suggest)).setText(ms.getSysRecommendWarehouse());
    ((TextView) view.findViewById(R.id.et_qr)).setText(ms.getBarcode());
    ((TextView) view.findViewById(R.id.et_store)).setText(ms.getStorageSpace());
    ((TextView) view.findViewById(R.id.et_storeNum)).setText(ms.getRecommendAmount());
    ((TextView) view.findViewById(R.id.et_boxNum)).setText(ms.getRecommendAmount());
    return view;
  }

  @Override public MaterialShelves getItem(int position) {
    return mss.get(position);
  }

  @Override public int getCount() {
    return mss.size();
  }
}
