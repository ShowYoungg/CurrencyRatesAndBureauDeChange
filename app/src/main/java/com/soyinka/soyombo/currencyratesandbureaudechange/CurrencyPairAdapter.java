package com.soyinka.soyombo.currencyratesandbureaudechange;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SHOW on 2/16/2019.
 */

public class CurrencyPairAdapter extends RecyclerView.Adapter<CurrencyPairAdapter.CurrencyPairViewHolder> implements Filterable {
    private List<String> mStringList1;
    private List<String> mStringList2;
    private List<CountryAndRates> arrayList;
    private List<CountryAndRates> mCombinedList;
    private Context mContext;
    private String mIdentifier;


    public CurrencyPairAdapter(Context context, List<CountryAndRates> combinedList, String identifier) {

        mContext = context;
        mCombinedList = combinedList;
        mIdentifier = identifier;
        if (mCombinedList != null) {
            arrayList = new ArrayList<>(mCombinedList);
        }
    }

    @Override
    public CurrencyPairViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.currency_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        CurrencyPairViewHolder viewHolder = new CurrencyPairViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CurrencyPairViewHolder holder, int position) {

        final CountryAndRates st = mCombinedList.get(position);

        holder.currenceRate.setText(st.getRate());
        holder.currencyName.setText(st.getCurrencyName());
        holder.countryName.setText(st.getCountryName());

        try {
            holder.imageView.setImageResource(st.getCountryFlag());
        } catch (Exception e) {

        }

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) mContext).finish();
                if (mIdentifier.equals("baseImage") || mIdentifier.equals("convertedImage")) {
                    mContext.startActivity(new Intent(mContext, MainActivity.class)
                            .putExtra("ParcelableObject", st)
                            .putExtra("Image", mIdentifier));
                } else {
                    ((Activity) mContext).finish();
                    mContext.startActivity(new Intent(mContext, MultiCurrency.class)
                            .putExtra("ParcelableObject", st)
                            .putExtra("Identifier", mIdentifier).putParcelableArrayListExtra("ArrayOfCountryAndRates",
                                    (ArrayList<? extends Parcelable>) arrayList));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mCombinedList != null) {
            return mCombinedList.size();
        } else {
            return 1;
        }
    }

    public List<String> getmStringList1() {
        return mStringList1;
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }


    public class CurrencyPairViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView countryName, currencyName, currenceRate;
        RelativeLayout relativeLayout;

        public CurrencyPairViewHolder(View itemView) {
            super(itemView);

            relativeLayout = itemView.findViewById(R.id.relative_layout);
            imageView = itemView.findViewById(R.id.country_image);
            countryName = itemView.findViewById(R.id.country_name);
            currencyName = itemView.findViewById(R.id.currency_name);
            currenceRate = itemView.findViewById(R.id.currency_rate);
        }
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<CountryAndRates> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(arrayList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (CountryAndRates t : arrayList) {
                    if (t.getCountryName().toLowerCase().startsWith(filterPattern)) {
                        filteredList.add(t);
                    }
                    if (t.getCurrencyName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(t);
                    }
                    if (t.getRate().toLowerCase().contains(filterPattern)) {
                        filteredList.add(t);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            mCombinedList.clear();
            mCombinedList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}