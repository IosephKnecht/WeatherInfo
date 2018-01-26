package com.example.aamezencev.weatherinfo.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.aamezencev.weatherinfo.App;
import com.example.aamezencev.weatherinfo.DaoModels.PromptCityDbModel;
import com.example.aamezencev.weatherinfo.Events.WeatherDeleteItemEvent;
import com.example.aamezencev.weatherinfo.Inrerfaces.DeleteBtnClick;
import com.example.aamezencev.weatherinfo.Inrerfaces.WeatherItemClick;
import com.example.aamezencev.weatherinfo.JsonModels.OWMApi.JsonWeatherModel;
import com.example.aamezencev.weatherinfo.Mappers.PromptCityDbModelToViewPromptCityModel;
import com.example.aamezencev.weatherinfo.Queries.RxDbManager;
import com.example.aamezencev.weatherinfo.R;
import com.example.aamezencev.weatherinfo.ViewModels.ViewPromptCityModel;

import org.greenrobot.eventbus.EventBus;
import org.reactivestreams.Subscriber;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by aa.mezencev on 12.01.2018.
 */

public class WeatherListAdapter extends RecyclerView.Adapter<WeatherListAdapter.ViewHolder> {

    private List<ViewPromptCityModel> viewPromptCityModelList;
    private List<PromptCityDbModel> promptCityDbModelList;
    private WeatherItemClick weatherItemClick;
    private DeleteBtnClick deleteBtnClick;

    public WeatherListAdapter(List<ViewPromptCityModel> viewPromptCityModelList,
                              List<PromptCityDbModel> promptCityDbModelList, WeatherItemClick weatherItemClick,
                              DeleteBtnClick deleteBtnClick) {
        this.viewPromptCityModelList = viewPromptCityModelList;
        this.promptCityDbModelList = promptCityDbModelList;
        this.weatherItemClick = weatherItemClick;
        this.deleteBtnClick = deleteBtnClick;
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
                Long.valueOf(viewPromptCityModelList.get(position).getKey()), holder.textView.getText().toString()));

        holder.button.setOnClickListener(view -> deleteBtnClick.deleteBtnClick(view, promptCityDbModelList.get(position)));
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
