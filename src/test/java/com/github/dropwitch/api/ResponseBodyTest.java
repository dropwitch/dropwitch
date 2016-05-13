package com.github.dropwitch.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import org.junit.Ignore;
import org.junit.Test;
import org.msgpack.jackson.dataformat.MessagePackFactory;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;

public class ResponseBodyTest {
    private static final ObjectMapper JSON_MAPPER = Jackson.newObjectMapper();
    private static final ObjectMapper MSGPACK_MAPPER = Jackson.newObjectMapper(new MessagePackFactory());

    @Test
    public void serializeToJson() throws Exception {
        final ResponseBody body = ResponseBody
                .builder()
                .result(true)
                .data(null)
                .build();

        final String expected = JSON_MAPPER.writeValueAsString(
                JSON_MAPPER.readValue(fixture("fixtures/api/response_body.json"), ResponseBody.class));

        assertThat(JSON_MAPPER.writeValueAsString(body)).isEqualTo(expected);
    }

    @Test
    public void deserializeFromJson() throws Exception {
        final ResponseBody body = JSON_MAPPER.readValue(fixture("fixtures/api/response_body.json"), ResponseBody.class);

        final ResponseBody expected = ResponseBody
                .builder()
                .result(true)
                .data(null)
                .build();

        assertThat(body).isEqualTo(expected);
    }

    // TODO MsgPackのシリアライズのテスト
    @Ignore
    @Test
    public void serializeToMsgPack() throws Exception {
        final ResponseBody body = ResponseBody
                .builder()
                .result(true)
                .data(null)
                .build();

        final String expected = MSGPACK_MAPPER.writeValueAsString(
                MSGPACK_MAPPER.readValue(fixture("fixtures/api/response_body.msgpack"), ResponseBody.class));

        assertThat(MSGPACK_MAPPER.writeValueAsString(body)).isEqualTo(expected);
    }

    // TODO MsgPackのデシリアライズのテスト
    @Ignore
    @Test
    public void deserializeFromMsgPack() throws Exception {
        final ResponseBody body = MSGPACK_MAPPER.readValue(fixture("fixtures/api/response_body.msgpack"), ResponseBody.class);

        final ResponseBody expected = ResponseBody
                .builder()
                .result(true)
                .data(null)
                .build();

        assertThat(body).isEqualTo(expected);
    }
}
