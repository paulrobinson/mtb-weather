package eu.paulrobinson.mtb.loader;

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

    public DataLoader() {

        WeatherResult2 weatherResult = new WeatherResult2("Hamsterley Forrest", "DL13 3NL", 54.6586, -1.9325);
        weatherResults.add(weatherResult);
    }

    public List<WeatherResult2> getWeatherResults() {

        refreshDate();

        return weatherResults;
    }

    public void refreshDate() {

        loadPastWeather();
        loadFutureWeather();
    }

    private void loadPastWeather() {
        LocalDate current =  LocalDate.now();
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/London"));

        for (WeatherResult2 weatherResult : weatherResults) {

            RainfallData rainfallData = rainfallService.getRainfallData(current.minusDays(pastDataDays).toString(), "rainfall", true);
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
        }
    }

    private void loadFutureWeather() {

        for (WeatherResult2 weatherResult : weatherResults) {
            ForcastData forcastData = forcastService.forcastDataThreeHourly(false, weatherResult.latitude, weatherResult.longitude, "a4dc9522-17c6-460f-8096-c6f757203eeb", "bU6tF0cC3lV5bP5eN1cB4hA5pM6bA1jM6lF4aW1uR7lA6eS6cQ");

            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/London"));
            for (ForcastData.Feature.Properties.FutureWeatherData data : forcastData.getData()) {
                cal.setTime(data.time);
                if (cal.get(Calendar.HOUR_OF_DAY) == 18) {
                    System.out.println(data.time);
                    weatherResult.futureWeatherData.add(data);
                }
            }
        }

    }
}
