/**
 * Created by Samuel James Patmore for The App Experts
 *
 * Released for limited use for education purposes under:
 *
 * Attribution-NonCommercial-NoDerivs 2.0 UK: England & Wales (CC BY-NC-ND 2.0 UK)
 */

package com.muelpatmore.week1assignment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.muelpatmore.week1assignment.realm.RealmCustomer;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Class to manage the
 * Created by Samuel on 18/11/2017.
 */
public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.mCustomerViewHolder> {

    private static final String TAG = "CustomerAdapter";
    private ArrayList<RealmCustomer> mRealmCustomers;
    private int mRowCustomer;
    private Context mApplicationContext;

    public CustomerAdapter(ArrayList<RealmCustomer> realmCustomers, int mRowCustomer, Context applicationContext) {
        this.mRealmCustomers = realmCustomers;
        this.mRowCustomer = mRowCustomer;
        this.mApplicationContext = applicationContext;
    }

    /**
     * Expands empty XML layout to be filled with Recycler entry data.
     * @param parent View that ViewHolder will be a child of.
     * @param viewType Integer value describing type of view. Required by super-class.
     * @return mCustomerViewHolder containing expanded XML values.
     */
    @Override
    public mCustomerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder");

        // get layout that is passed from from app context
        // inflate row from this context, from this parent in this pattern
        View view = LayoutInflater.from(mApplicationContext).inflate(R.layout.customer_card_layout, parent, false);

        //viewholder init view elements and is then passed back
        return new mCustomerViewHolder(view);
    }

    /**
     * Extracts spesified values from RealmCustomer object and bind to View links held by
     * ViewHolder object
     */
    @Override
    public void onBindViewHolder(mCustomerViewHolder holder, int position) {
        // collect data from Realm item and plug into View
        Log.i(TAG, "onBindViewHolder(holder, "+position+")");
        RealmCustomer customer = mRealmCustomers.get(position);
        String name = customer.getForename() + " " + customer.getSurname();
        holder.tvName.setText(name);
        holder.tvDob.setText(customer.getDob());
        String gender = customer.getGender();
        Log.v(TAG, "RETRIEVED GENDER: "+gender);
        if (holder.tvGender != null) {
            holder.tvGender.setText(customer.getGender());
        }
        holder.tvAddress.setText(customer.getAaddress());
        holder.tvNationality.setText(customer.getNationality());
        InputStream inputStream  = new ByteArrayInputStream(customer.getImage());
        Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
        if (holder.ivPhoto != null) {
            holder.ivPhoto.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        return mRealmCustomers.size();
    }

    /**
     * Class to create and hold links to specific Views in a context view.
     */
    public class mCustomerViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "mCustomerViewHolder";

        TextView tvName, tvAddress, tvNationality, tvGender, tvDob;
        ImageView ivPhoto;

        mCustomerViewHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvGender = itemView.findViewById(R.id.tvGender);
            tvNationality = itemView.findViewById(R.id.tvNationality);
            tvDob = itemView.findViewById(R.id.tvDob);
            ivPhoto = itemView.findViewById(R.id.ivPhoto);
        }
    }
}
