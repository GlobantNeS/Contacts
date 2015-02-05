package com.kaineras.contacts;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created the first version by kaineras on 5/02/15.
 */
public class LinkAdapter extends ArrayAdapter<Contact> {

    private List<Contact> contactList;
    private Context ctx;

    public LinkAdapter(List<Contact> contactList, Context ctx) {
        super(ctx, R.layout.contact_layout, contactList);
        this.ctx = ctx;
        this.contactList = contactList;
    }

    @Override
    public int getCount() {
        return contactList.size();
    }

    @Override
    public Contact getItem(int position) {
        return contactList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return contactList.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if ( v == null) {
            LayoutInflater inf = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.contact_layout, null);
            TextView tName = (TextView) v.findViewById(R.id.contName);
            ImageView contImage =(ImageView) v.findViewById(R.id.contImage);
            if(contactList.get(position).getNickName().isEmpty())
                tName.setText(contactList.get(position).getName());
            else
                tName.setText(contactList.get(position).getNickName());
            byte[] imaByte=contactList.get(position).getImage();
            BitmapFactory.Options options = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeByteArray(imaByte, 0, imaByte.length, options);
            contImage.setImageBitmap(bitmap );
        }
        return v;
    }
}
