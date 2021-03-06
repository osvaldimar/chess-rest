package com.dim.chess.rest;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Assert;
import org.junit.Test;

import com.chess.core.ResponseChessboard.StatusResponse;
import com.chess.core.client.ResponseClient;
import com.chess.core.client.TransformJson;

public class ChessWSTest extends JerseyTest {

	@Override
    public Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        ResourceConfig config = new ResourceConfig();
        config.register(ChessWS.class);
        AbstractBinder binder = new AbstractBinder() {			
			@Override
			protected void configure() {
				bind(ChessPoolService.class).to(ChessPoolService.class);
			}
		};
		config.register(binder);
        return config;
    }
	
	@Test
	public void testStartGameChess(){
		Response response = target("/startChessSingle").request().get();
		Assert.assertEquals("Method startChessSingle should return status 200", 200, response.getStatus());
		
		String strJson = response.readEntity(String.class);
		ResponseClient resClient = TransformJson.fromJson(strJson);
		Assert.assertEquals(resClient.getStatus(), StatusResponse.START.toString());
	}
	
	@Test
	public void should_create_greeting() {
	}

}
