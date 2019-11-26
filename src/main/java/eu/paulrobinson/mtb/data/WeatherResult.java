package eu.paulrobinson.mtb.data;

import java.util.*;

public class WeatherResult {

    public String location;

    public Map<String, Reading> readings = new HashMap<>();

    public List<Reading> getReadings() {
        List<Reading> readingsResults = new ArrayList<>(readings.values());
        Collections.sort(readingsResults);
        return readingsResults;
    }

    public void incrementRainfall(String date, double rainfall) {

        Reading reading = readings.get(date);
        if (reading == null ) {
            reading = new Reading(date);
            readings.put(date, reading);
        }
        reading.incrimentRainfall(rainfall);
    }

    public static class Reading implements Comparable<Reading> {

        private String date;
        private double rainfall;

        public Reading(String date) {
            this.date = date;
            this.rainfall = 0;
        }

        public void incrimentRainfall(double amount) {
            rainfall += amount;
        }


        @Override
        public int compareTo(Reading o) {
            return date.compareTo(o.date);
        }

        public String getDate() {
            return date;
        }

        public double getRainfall() {
            return rainfall;
        }
    }

}
