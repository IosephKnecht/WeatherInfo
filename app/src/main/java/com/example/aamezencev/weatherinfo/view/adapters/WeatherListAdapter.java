package com.example.aamezencev.weatherinfo.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aamezencev.weatherinfo.view.interfaces.WeatherItemClick;
import com.example.aamezencev.weatherinfo.R;
import com.example.aamezencev.weatherinfo.view.viewModels.ViewPromptCityModel;

import java.util.List;

/**
 * Created by aa.mezencev on 12.01.2018.
 */

public class WeatherListAdapter extends RecyclerView.Adapter<WeatherListAdapter.ViewHolder> {

    private List<ViewPromptCityModel> viewPromptCityModelList;
    private WeatherItemClick weatherItemClick;

    public WeatherListAdapter(List<ViewPromptCityModel> viewPromptCityModelList, WeatherItemClick weatherItemClick) {
        this.viewPromptCityModelList = viewPromptCityModelList;
        this.weatherItemClick = weatherItemClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        String lineSep = String.format("%n");
        holder.textView.setText(viewPromptCityModelList.get(position).getStructuredFormatting().getMainText() +
                lineSep
                + viewPromptCityModelList.get(position).getStructuredFormatting().getSecondaryText() + lineSep +
                viewPromptCityModelList.get(position).getBriefInformation());

        holder.textView.setOnClickListener(view -> weatherItemClick.weatherItemClick(view,
                Long.valueOf(viewPromptCityModelList.get(holder.getAdapterPosition()).getKey()),
                holder.textView.getText().toString()));
    }

    @Override
    public int getItemCount() {
        return viewPromptCityModelList.size();
    }

    public List<ViewPromptCityModel> getViewPromptCityModelList() {
        return viewPromptCityModelList;
    }

    public void setViewPromptCityModelList(List<ViewPromptCityModel> viewPromptCityModelList) {
        this.viewPromptCityModelList = viewPromptCityModelList;
    }

    public void removeItem(int position) {
        viewPromptCityModelList.remove(position);
        notifyItemRemoved(position);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private LinearLayout viewForeground;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.weatherItem);
            this.viewForeground = itemView.findViewById(R.id.viewForeground);
        }

        public LinearLayout getViewForeground() {
            return viewForeground;
        }
    }
}
