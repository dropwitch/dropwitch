package com.github.dropwitch.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dropwitch.api.ResponseBody;
import com.github.dropwitch.api.user.UserRegisterRequestData;
import com.github.dropwitch.entity.User;
import com.github.dropwitch.entity.dao.UserDao;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Entity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class UserResourceTest {
    private static final ObjectMapper JSON_MAPPER = Jackson.newObjectMapper();
    private static final UserDao dao = mock(UserDao.class);

    private Long userId = 1L;
    private String userName = "test_user";
    private final User user = User
            .builder()
            .id(userId)
            .name(userName)
            .createdAt(DateTime.now())
            .updatedAt(DateTime.now())
            .build();

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule
            .builder()
            .addResource(new UserResource(dao))
            .build();

    @Before
    public void setUp() throws Exception {
        when(dao.findById(eq(userId))).thenReturn(user);
        when(dao.create(userName)).thenReturn(user);
    }

    @After
    public void tearDown() throws Exception {
        reset(dao);
    }

    @Test
    public void get() throws Exception {
        ResponseBody expected = ResponseBody
                .<User>builder()
                .result(true)
                .data(user)
                .build();

        assertThat(resources.client().target("/user/"+userId).request().get(String.class),
                is(JSON_MAPPER.writeValueAsString(expected)));
    }

    @Test
    public void register() throws Exception {
        UserRegisterRequestData requestData = UserRegisterRequestData
                .builder()
                .name(userName)
                .screenName(userName)
                .build();

        ResponseBody expected = ResponseBody
                .<User>builder()
                .result(true)
                .data(user)
                .build();

        assertThat(resources.client().target("/user/register").request()
                .post(Entity.json(requestData), String.class),
                is(JSON_MAPPER.writeValueAsString(expected)));
    }
}
