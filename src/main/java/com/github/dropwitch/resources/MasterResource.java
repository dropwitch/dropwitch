package com.github.dropwitch.resources;

import com.codahale.metrics.annotation.Timed;
import com.github.dropwitch.api.ResponseBody;
import com.github.dropwitch.api.data.MasterResponseData;
import com.github.dropwitch.entity.dao.MasterCommonDao;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Optional;

@Path("/master")
@Produces({MediaType.APPLICATION_JSON, "application/x-msgpack"})
@Timed
public class MasterResource {
    private MasterCommonDao masterCommonDao;
    private Optional<MasterResponseData> data;

    public MasterResource(MasterCommonDao masterCommonDao) {
        this.masterCommonDao = masterCommonDao;
        this.data = Optional.empty();
    }

    @GET
    @UnitOfWork
    public ResponseBody get() {
        return new ResponseBody(this.data.orElseGet(() -> {
            List common = masterCommonDao.getAll();
            this.data = Optional.of(new MasterResponseData(common));
            return this.data.orElse(null);
        }));
    }

    @DELETE
    @Path("/cache")
    public ResponseBody deleteCache() {
        this.data = Optional.empty();
        return new ResponseBody(null);
    }
}
