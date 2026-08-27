package org.acme.resource;


import org.acme.services.UserService;

import com.think.xartefactopagination.PaginationModel;

import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/users")
public class UserResource {

    @Inject
    UserService service;

    @POST
    @Path("/pagination")
    @Authenticated
    public Response pagination(PaginationModel paginationModel) throws ReflectiveOperationException{
        System.out.println("paginationModel: " + paginationModel);
        //return Response.ok( service.paginationProjections(paginationModel) ).build();
        return Response.ok( service.paginationProjections(paginationModel) ).build();
    }

}
