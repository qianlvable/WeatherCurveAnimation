package com.lvable.ningjiaqi.weathercurveanim;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherPageFragment extends Fragment {

    String locationName = "";
    String temperature = "";
    public WeatherPageFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_weather_page, container, false);
        TextView locationTv = (TextView) root.findViewById(R.id.tv_location_name);
        TextView tempTv = (TextView) root.findViewById(R.id.tv_temperature);
        locationTv.setText(locationName);
        tempTv.setText(temperature);

        return root;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
