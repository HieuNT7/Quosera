package project.iuh.hh.quosera.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import project.iuh.hh.quosera.R;
import project.iuh.hh.quosera.entites.Tag_Item;

/**
 * Created by Nguyen Hoang on 30/03/2016.
 */
public class MyTagAdapter extends ArrayAdapter<Tag_Item> {
    private Activity ctx;
    private int resource;
    private ArrayList<Tag_Item> arr;
    public MyTagAdapter(Activity context, int resource, ArrayList<Tag_Item> objects) {
        super(context, resource, objects);
        ctx = context;
        this.resource = resource;
        arr = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ctx.getLayoutInflater();
        convertView = inflater.inflate(resource,null);


//        ImageView img = (ImageView)convertView.findViewById(R.id.img_tag);
        TextView txt = (TextView)convertView.findViewById(R.id.txt_tag);
        CheckBox chb = (CheckBox)convertView.findViewById(R.id.chb_tag);


        final Tag_Item item = arr.get(position);
//        img.setImageResource(item.getResources());
        txt.setText(item.getContent());
        chb.setChecked(item.ischecked());

        chb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               item.setIschecked(!item.ischecked());
            }
        });
        return convertView;
    }
    @Override
    public boolean areAllItemsEnabled()
    {
        return true;
    }

    @Override
    public boolean isEnabled(int arg0)
    {
        return true;
    }
}
