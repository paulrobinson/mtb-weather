package eu.paulrobinson.mtb.data;

import eu.paulrobinson.mtb.loader.ForcastData;
import eu.paulrobinson.mtb.loader.RainfallData;

import java.util.ArrayList;
import java.util.List;

public class WeatherResult2 {

    public String locationName;
    public String postcode;
    public Double latitude;
    public Double longitude;

    public List<RainfallData.PastWeatherData> pastWeatherData = new ArrayList<>();

    public List<ForcastData.Feature.Properties.FutureWeatherData> futureWeatherData = new ArrayList<>();

    public WeatherResult2(String locationName, String postcode, Double latitude, Double longitude) {
        this.locationName = locationName;
        this.postcode = postcode;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
