package com.example.aamezencev.weatherinfo.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.aamezencev.weatherinfo.Inrerfaces.CheckBoxClick;
import com.example.aamezencev.weatherinfo.MainActivity;
import com.example.aamezencev.weatherinfo.R;
import com.example.aamezencev.weatherinfo.ViewModels.ViewPromptCityModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aa.mezencev on 10.01.2018.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<ViewPromptCityModel> viewPromptCityModelList;
    private CheckBoxClick checkBoxClick;

    public MainAdapter(List<ViewPromptCityModel> viewPromptCityModelList, CheckBoxClick checkBoxClick) {
        this.viewPromptCityModelList = viewPromptCityModelList;
        this.checkBoxClick = checkBoxClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_item, parent, false);

        final ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ViewPromptCityModel viewPromptCityModel = viewPromptCityModelList.get(position);
        holder.cbCity.setText(stringConcatenation(viewPromptCityModelList.get(position)));
        holder.cbCity.setChecked(viewPromptCityModel.isChecked());
        holder.cbCity.setOnClickListener(view -> {
            checkBoxClick.checkBoxClick(view, viewPromptCityModelList.get(position));
        });
    }

    public List<ViewPromptCityModel> selectIsCheckedItem() {
        List<ViewPromptCityModel> viewPromptCityModelList = new ArrayList<>();
        for (ViewPromptCityModel viewPromptCityModel : this.viewPromptCityModelList) {
            if (viewPromptCityModel.isChecked()) viewPromptCityModelList.add(viewPromptCityModel);
        }
        return viewPromptCityModelList;
    }

    @Override
    public int getItemCount() {
        return viewPromptCityModelList.size();
    }

    private String stringConcatenation(ViewPromptCityModel viewPromptCityModel) {
        String separator = String.format("%n");
        String output = viewPromptCityModel.getId() + separator + viewPromptCityModel.getDescription() + separator +
                viewPromptCityModel.getStructuredFormatting().toString();
        return output;
    }

    public int isVisibleFloatingButton() {
        for (ViewPromptCityModel viewPromptCityModel : viewPromptCityModelList) {
            if (viewPromptCityModel.isChecked()) return View.VISIBLE;
        }
        return View.INVISIBLE;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        private CheckBox cbCity;
        private Context context;

        public ViewHolder(View itemView) {
            super(itemView);
            cbCity = itemView.findViewById(R.id.cbCity);
            context = cbCity.getContext();
        }
    }

    public List<ViewPromptCityModel> getViewPromptCityModelList() {
        return viewPromptCityModelList;
    }

    public void setViewPromptCityModelList(List<ViewPromptCityModel> viewPromptCityModelList) {
        this.viewPromptCityModelList = viewPromptCityModelList;
    }
}
