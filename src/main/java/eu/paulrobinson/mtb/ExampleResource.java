package eu.paulrobinson.mtb;

import eu.paulrobinson.mtb.data.WeatherResult;
import eu.paulrobinson.mtb.loader.ForcastData;
import eu.paulrobinson.mtb.loader.ForcastService;
import eu.paulrobinson.mtb.loader.RainfallData;
import eu.paulrobinson.mtb.loader.RainfallService;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

@Path("/hello")
public class ExampleResource {

    @Inject
    @RestClient
    RainfallService rainfallService;

    @Inject
    @RestClient
    ForcastService forcastService;

    @GET
    @Path("/rainfall")
    @Produces(MediaType.APPLICATION_JSON)
    public List<WeatherResult.Reading> rainfall() {

        LocalDate current =  LocalDate.now();
        RainfallData rainfallData = rainfallService.getRainfallData(current.minusDays(4).toString(), "rainfall", true);


        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/London"));

        WeatherResult weatherResult = new WeatherResult();
        weatherResult.location = "Hamsterley Forest";

        for (RainfallData.Item item : rainfallData.items) {
            cal.setTime(item.dateTime);
            String date = cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH) + "-" + cal.get(Calendar.DAY_OF_MONTH);
            weatherResult.incrementRainfall(date, item.value);
        }
        return weatherResult.getReadings();
    }


    @GET
    @Path("/forcast")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ForcastData.Feature.Properties.TimeSeries> forcast() {

        ForcastData forcastData = forcastService.forcastDataThreeHourly(false, 54.6586, -1.9325, "a4dc9522-17c6-460f-8096-c6f757203eeb", "bU6tF0cC3lV5bP5eN1cB4hA5pM6bA1jM6lF4aW1uR7lA6eS6cQ");

        List<ForcastData.Feature.Properties.TimeSeries> forcastResult = new ArrayList<>();
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/London"));
        for (ForcastData.Feature.Properties.TimeSeries data : forcastData.getData()) {
            cal.setTime(data.time);
            if (cal.get(Calendar.HOUR_OF_DAY) == 18) {
                System.out.println(data.time);
                forcastResult.add(data);
            }
        }

        return forcastResult;
    }
}