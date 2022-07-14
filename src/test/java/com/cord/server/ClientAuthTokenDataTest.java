package com.cord.server;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import com.cord.server.ClientAuthTokenData.ClientAuthTokenDataBuilder;
import com.cord.server.PlatformOrganizationVariables.PlatformOrganizationVariablesBuilder;
import com.cord.server.PlatformUserVariables.PlatformUserVariablesBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonInclude;

public class ClientAuthTokenDataTest {
    private static final ObjectMapper mapper = new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    @Test
    public void simpleSerialization() throws Exception {
        ClientAuthTokenData authData = new ClientAuthTokenDataBuilder("userID", "orgID")
                .userDetails(new PlatformUserVariablesBuilder("email@example.com")
                        .status(Status.ACTIVE)
                        .name("Example")
                        .firstName("Jane")
                        .lastName("Doe")
                        .build())
                .organizationDetails(new PlatformOrganizationVariablesBuilder("name")
                        .status(Status.DELETED)
                        .members(Arrays.asList("north", "south", "dennis"))
                        .build())
                .build();
        assertEquals(
                "{\"user_id\":\"userID\",\"organization_id\":\"orgID\",\"user_details\":{\"email\":\"email@example.com\",\"name\":\"Example\",\"status\":\"active\",\"first_name\":\"Jane\",\"last_name\":\"Doe\"},\"organization_details\":{\"name\":\"name\",\"status\":\"deleted\",\"members\":[\"north\",\"south\",\"dennis\"]}}",
                mapper.writeValueAsString(authData));
    }
}
