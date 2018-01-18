package com.example.aamezencev.weatherinfo.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.aamezencev.weatherinfo.App;
import com.example.aamezencev.weatherinfo.DaoModels.DaoSession;
import com.example.aamezencev.weatherinfo.DaoModels.PromptCityDbModel;
import com.example.aamezencev.weatherinfo.Events.WeatherDeleteItemEvent;
import com.example.aamezencev.weatherinfo.JsonModels.JsonResultsGeo;
import com.example.aamezencev.weatherinfo.JsonModels.OWMApi.JsonWeatherModel;
import com.example.aamezencev.weatherinfo.Queries.DeleteItemOfDb;
import com.example.aamezencev.weatherinfo.R;
import com.example.aamezencev.weatherinfo.Requests.GetCurrentWeather;
import com.example.aamezencev.weatherinfo.Requests.GetGeoToPlaceId;
import com.example.aamezencev.weatherinfo.ViewModels.ViewPromptCityModel;
import com.example.aamezencev.weatherinfo.WeatherInfoActivity;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import org.greenrobot.eventbus.EventBus;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by aa.mezencev on 12.01.2018.
 */

public class WeatherListAdapter extends RecyclerView.Adapter<WeatherListAdapter.ViewHolder> {

    private List<ViewPromptCityModel> viewPromptCityModelList;
    private List<PromptCityDbModel> promptCityDbModelList;
    private DaoSession daoSession;
    private Observable listObservable;

    public WeatherListAdapter(List<ViewPromptCityModel> viewPromptCityModelList,
                              List<PromptCityDbModel> promptCityDbModelList) {
        this.viewPromptCityModelList = viewPromptCityModelList;
        this.promptCityDbModelList = promptCityDbModelList;
        listObservable = Observable.timer(10000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io());
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

        JsonWeatherModel currentModel = new JsonWeatherModel();

        Subscriber<JsonWeatherModel> jsonWeatherModelSubscribe;
        Disposable disposable = listObservable.just(viewPromptCityModelList.get(position))
                .subscribeOn(Schedulers.io())
                .map(geo -> {
                    GetGeoToPlaceId getGeoToPlaceId = new GetGeoToPlaceId(geo.getPlaceId());
                    getGeoToPlaceId.execute();
                    JsonResultsGeo jsonResultsGeo = null;
                    jsonResultsGeo = getGeoToPlaceId.get();
                    return jsonResultsGeo;
                })
                .map(weather -> {
                    GetCurrentWeather getCurrentWeather = new GetCurrentWeather(weather.getJsonLocationModel().getLat(), weather.getJsonLocationModel().getLng());
                    getCurrentWeather.execute();
                    JsonWeatherModel jsonWeatherModel = new JsonWeatherModel();
                    jsonWeatherModel = getCurrentWeather.get();
                    return jsonWeatherModel;
                })
                .repeatWhen(completed -> {
                    Log.d("WLA", "Repeat at position " + position);
                    return completed.delay(10000, TimeUnit.MILLISECONDS);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber -> {
                    String output = holder.textView.getText().toString();
                    output += subscriber.getCod();
                    holder.textView.setText(output);
                });


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
                disposable.dispose();
                DeleteItemOfDb deleteItemOfDb = new DeleteItemOfDb(promptCityDbModelList.get(position).getKey(), daoSession);
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
