package com.example.bengkel.bengkellist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bengkel.BengkelListActivity;

import java.util.List;

public class BengkelListAdapter extends BaseAdapter {
    private final Context context;
    private final List<BengkelListActivity.Data> dataList;
    private final int image;
    private int templateLayoutId;
    private int templateImageId;
    private int templateNameId;
    private final LayoutInflater inflater;

    public BengkelListAdapter(Context appContext, List<BengkelListActivity.Data> dataList, int image) {
        this.context = appContext;
        this.dataList = dataList;
        this.image = image;
        this.inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        System.out.println("memproses view");
        view = inflater.inflate(templateLayoutId, null);

        ImageView imageView = (ImageView) view.findViewById(templateImageId);
        imageView.setImageResource(image); // set logo images

        TextView textView = (TextView) view.findViewById(templateNameId);
        String text = dataList.get(i).nama;
        if (text.length() > 12) {
            text = text.substring(0, 11) + "...";
        }
        textView.setText(text);

        return view;
    }

    public void setTemplateLayoutId(int id) {
        this.templateLayoutId = id;
    }

    public void setTemplateImageId(int id) {
        this.templateImageId = id;
    }

    public void setTemplateNameId(int id) {
        this.templateNameId = id;
    }
}
