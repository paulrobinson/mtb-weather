package eu.paulrobinson.mtb;

import eu.paulrobinson.mtb.data.WeatherResult;
import eu.paulrobinson.mtb.loader.*;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/weather")
public class WeatherDataResource {

    @Inject
    DataLoader dataLoader;

    @GET
    @Path("/data")
    @Produces(MediaType.APPLICATION_JSON)
    public List<WeatherResult> data() {

        return dataLoader.getWeatherResults();
    }
}