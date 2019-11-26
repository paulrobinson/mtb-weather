package eu.paulrobinson.mtb.loader;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;

@Path("/forecasts/point/")
@RegisterRestClient
@Produces("application/json")
@Consumes("application/json")
public interface ForcastService {

    @GET
    @Path("/three-hourly")
    @Produces("application/json")
    ForcastData forcastDataThreeHourly(@QueryParam("includeLocationName") boolean includeLocationName, @QueryParam("latitude") Double latitude, @QueryParam("longitude") Double longitude, @HeaderParam("x-ibm-client-id") String clientID, @HeaderParam("x-ibm-client-secret") String secret);


}
