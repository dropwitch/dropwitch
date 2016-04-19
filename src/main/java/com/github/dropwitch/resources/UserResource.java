package com.github.dropwitch.resources;

import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Timed;
import com.github.dropwitch.api.ResponseBody;
import com.github.dropwitch.api.user.UserRegisterRequestData;
import com.github.dropwitch.core.DropwitchMediaType;
import com.github.dropwitch.entity.User;
import com.github.dropwitch.entity.dao.UserDao;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;

@Path("/user")
@Produces({DropwitchMediaType.APPLICATION_JSON, DropwitchMediaType.APPLICATION_MSGPACK})
public class UserResource {
    private UserDao userDao;

    public UserResource(UserDao userDao) {
        this.userDao = userDao;
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    @Timed
    @ExceptionMetered
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
    @Consumes({DropwitchMediaType.APPLICATION_JSON, DropwitchMediaType.APPLICATION_MSGPACK})
    @UnitOfWork
    @Timed
    @ExceptionMetered
    public ResponseBody register(UserRegisterRequestData data) {
        User user = userDao.create(data.getName());
        return ResponseBody
                .builder()
                .result(true)
                .data(user)
                .build();
    }
}
