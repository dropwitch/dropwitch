package com.github.dropwitch.resources;

import com.codahale.metrics.annotation.Timed;
import com.github.dropwitch.api.ResponseBody;
import com.github.dropwitch.api.user.UserRegisterRequestData;
import com.github.dropwitch.entity.User;
import com.github.dropwitch.entity.dao.UserDao;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/user")
@Produces({MediaType.APPLICATION_JSON, "application/x-msgpack"})
@Timed
public class UserResource {
    private UserDao userDao;

    public UserResource(UserDao userDao) {
        this.userDao = userDao;
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    public ResponseBody get(@PathParam("id") Long id) {
        User user = userDao.findById(id);
        return ResponseBody
                .builder()
                .result(true)
                .data(user)
                .build();
    }

    @POST
    @Path("/register")
    @Consumes({MediaType.APPLICATION_JSON, "application/x-msgpack"})
    @UnitOfWork
    public ResponseBody register(UserRegisterRequestData data) {
        User user = userDao.create(data.getName());
        return ResponseBody
                .builder()
                .result(true)
                .data(user)
                .build();
    }
}
