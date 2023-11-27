package org.atar.simulator.api;

import com.google.gson.Gson;
import org.atar.supportsys.publisher.Publisher;
import org.atar.supportsys.publisher.PublisherType;
import org.atar.supportsys.event.Event;
import org.atar.supportsys.event.EventBroker;
import org.atar.supportsys.event.SATValidator;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/")
public class Rest {

    @GET
    @Path("latestEvents")
    public Response getLatestEvents() {
        final List<Event> latestEvents = EventBroker.getLatestEvents();
        final String eventsAsJson = new Gson().toJson(latestEvents);
        return Response.ok(eventsAsJson).build();
    }

    @POST
    @Path("sat")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response postSat(String sat) {
        SATValidator validator = new SATValidator(sat);
        if(validator.isValid()) {
            Publisher.publish(PublisherType.SAT_PROBLEM, sat);
            return Response.ok().build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(validator.getErrorsAsString()).build();
    }
}
