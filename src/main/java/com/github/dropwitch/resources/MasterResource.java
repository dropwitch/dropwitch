package com.github.dropwitch.resources;

import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Timed;
import com.github.dropwitch.api.ResponseBody;
import com.github.dropwitch.api.master.MasterResponseData;
import com.github.dropwitch.core.DropwitchMediaType;
import com.github.dropwitch.entity.dao.MasterCommonDao;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;

@Path("/master")
@Produces({DropwitchMediaType.APPLICATION_JSON, DropwitchMediaType.APPLICATION_MSGPACK})
public class MasterResource {
    private MasterCommonDao masterCommonDao;
    private MasterResponseData data;

    public MasterResource(MasterCommonDao masterCommonDao) {
        this.masterCommonDao = masterCommonDao;
        this.data = null;
    }

    @GET
    @UnitOfWork
    @Timed
    @ExceptionMetered
    public ResponseBody get() {
        if (data == null) {
            data = MasterResponseData
                    .builder()
                    .common(masterCommonDao.findAll())
                    .build();
        }
        return ResponseBody
                .builder()
                .result(true)
                .data(data)
                .build();
    }

    @DELETE
    @Path("/cache")
    @Timed
    @ExceptionMetered
    public ResponseBody deleteCache() {
        data = null;
        return ResponseBody
                .builder()
                .result(true)
                .data(null)
                .build();
    }
}
