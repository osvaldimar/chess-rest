import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Assert;
import org.junit.Test;

import com.dim.chess.rest.ChessWS;

public class ChessWSTest extends JerseyTest {

	@Override
    public Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig(ChessWS.class);
    }
	
//	@Test
//	public void testStartGameChess(){
//		Response response = target("/ws/startChess").request().get();
//		Assert.assertEquals("Method start chess should return status 200", 200, response.getStatus());
//	}
	
}
