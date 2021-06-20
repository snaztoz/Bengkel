package com.example.bengkel.bengkellist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class BengkelListAdapter extends BaseAdapter {
    private final Context context;
    private final int[] images;
    private int templateLayoutId;
    private int templateId;
    private final LayoutInflater inflater;

    public BengkelListAdapter(Context appContext, int[] images) {
        this.context = appContext;
        this.images = images;
        this.inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return images.length;
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
        view = inflater.inflate(templateLayoutId, null);
        ImageView image = (ImageView) view.findViewById(templateId);
        image.setImageResource(images[i]); // set logo images
        return view;
    }

    public void setTemplateLayoutId(int id) {
        this.templateLayoutId = id;
    }

    public void setTemplateId(int id) {
        this.templateId = id;
    }
}
