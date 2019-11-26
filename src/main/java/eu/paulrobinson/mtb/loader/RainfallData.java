package eu.paulrobinson.mtb.loader;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RainfallData {

    public Collection<Item> items;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Item {

        public Date dateTime;
        public Double value;
    }
}
