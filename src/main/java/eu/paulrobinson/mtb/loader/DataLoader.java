package eu.paulrobinson.mtb.loader;

import eu.paulrobinson.mtb.data.WeatherResult;
import eu.paulrobinson.mtb.data.WeatherResult2;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.LocalDate;
import java.util.*;

@Singleton
public class DataLoader {

    @Inject
    @RestClient
    RainfallService rainfallService;

    @Inject
    @RestClient
    ForcastService forcastService;

    private int pastDataDays = 4;
    private int futureDataDays = 7;

    private List<WeatherResult2> weatherResults = new ArrayList<>();

    public List<WeatherResult2> getWeatherResults() {

        refreshDate();

        return weatherResults;
    }

    public void refreshDate() {

        LocalDate current =  LocalDate.now();
        RainfallData rainfallData = rainfallService.getRainfallData(current.minusDays(pastDataDays).toString(), "rainfall", true);

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/London"));

        WeatherResult2 weatherResult = new WeatherResult2("Hamsterley Forrest", "DL13 3NL", 54.6586, -1.9325);

        Map<Integer, RainfallData.PastWeatherData> readings = new HashMap<>();
        for (RainfallData.PastWeatherData pastWeatherData : rainfallData.items) {

            cal.setTime(pastWeatherData.dateTime);
            Integer dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

            RainfallData.PastWeatherData reading = readings.get(dayOfMonth);
            if (reading == null) {
                reading = new RainfallData.PastWeatherData();
                readings.put(dayOfMonth, reading);
                reading.dateTime = pastWeatherData.dateTime;
            }

            reading.value += pastWeatherData.value;
        }

        List<RainfallData.PastWeatherData> readingsResults = new ArrayList<>(readings.values());
        Collections.sort(readingsResults);

        weatherResult.pastWeatherData = readingsResults;

        weatherResults.add(weatherResult);

    }
}
