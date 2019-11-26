package eu.paulrobinson.mtb.loader;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import java.util.Date;

@Path("/flood-monitoring")
@RegisterRestClient
@Produces("application/json")
@Consumes("application/json")
public interface RainfallService {

    @GET
    @Path("/id/stations/022163/readings")
    @Produces("application/json")
    RainfallData getRainfallData(@QueryParam("since") String since, @QueryParam("parameter") String type, @QueryParam("_sorted") boolean sorted);

}
