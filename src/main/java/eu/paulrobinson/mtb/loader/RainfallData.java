package eu.paulrobinson.mtb.loader;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RainfallData {

    public List<PastWeatherData> items;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PastWeatherData implements Comparable<PastWeatherData> {

        public Date dateTime;
        public Double value = 0.0;

        @Override
        public int compareTo(PastWeatherData o) {
            return dateTime.compareTo(o.dateTime);
        }
    }
}
