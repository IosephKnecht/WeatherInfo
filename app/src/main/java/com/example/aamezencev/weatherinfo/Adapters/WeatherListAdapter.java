package com.example.aamezencev.weatherinfo.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.aamezencev.weatherinfo.App;
import com.example.aamezencev.weatherinfo.DaoModels.DaoSession;
import com.example.aamezencev.weatherinfo.DaoModels.PromptCityDbModel;
import com.example.aamezencev.weatherinfo.Events.WeatherDeleteItemEvent;
import com.example.aamezencev.weatherinfo.Queries.DeleteItemOfDb;
import com.example.aamezencev.weatherinfo.R;
import com.example.aamezencev.weatherinfo.ViewModels.ViewPromptCityModel;
import com.example.aamezencev.weatherinfo.WeatherInfoActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by aa.mezencev on 12.01.2018.
 */

public class WeatherListAdapter extends RecyclerView.Adapter<WeatherListAdapter.ViewHolder> {

    private List<ViewPromptCityModel> viewPromptCityModelList;
    private List<PromptCityDbModel> promptCityDbModelList;
    private DaoSession daoSession;

    public WeatherListAdapter(List<ViewPromptCityModel> viewPromptCityModelList,
                              List<PromptCityDbModel> promptCityDbModelList) {
        this.viewPromptCityModelList = viewPromptCityModelList;
        this.promptCityDbModelList = promptCityDbModelList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        daoSession = ((App) viewHolder.context.getApplicationContext()).getDaoSession();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.textView.setText(viewPromptCityModelList.get(position).getStructuredFormatting().getMainText() +
                System.lineSeparator()
                + viewPromptCityModelList.get(position).getStructuredFormatting().getSecondaryText());

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.context, WeatherInfoActivity.class);
                String placeId = viewPromptCityModelList.get(position).getPlaceId();
                intent.putExtra("placeId", placeId);
                holder.context.startActivity(intent);
            }
        });

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteItemOfDb deleteItemOfDb = new DeleteItemOfDb(promptCityDbModelList.get(position).getStructureFormattingId(), daoSession);
                deleteItemOfDb.execute();
            }
        });
    }

    @Override
    public int getItemCount() {
        return viewPromptCityModelList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private Button button;
        private Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.weatherItem);
            this.button = itemView.findViewById(R.id.deleteItemButton);
            context = textView.getContext();
        }
    }
}
