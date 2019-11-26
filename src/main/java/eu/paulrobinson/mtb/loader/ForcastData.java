package eu.paulrobinson.mtb.loader;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ForcastData {

    public List<Feature> features;

    public List<Feature.Properties.TimeSeries> getData() {
        return features.get(0).properties.timeSeries;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Feature {

        public Properties properties;

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Properties {

            public List<TimeSeries> timeSeries;

            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class TimeSeries {

                public Date time;
                public Double feelsLikeTemp;
                public Double totalPrecipAmount;
                public Double probOfHeavyRain;
                public Double probOfRain;
                public Double windSpeed10m;

            }
        }
    }
}