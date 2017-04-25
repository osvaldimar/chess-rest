package com.dim.chess.rest.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.chess.core.ResponseChessboard;
import com.chess.core.client.ResponseClient;
import com.chess.core.client.TransformJson;

@Provider
public class ChessExceptionMapper implements ExceptionMapper<ChessParametersException> {
	
	@Override
	public Response toResponse(ChessParametersException exception) {
		String res = TransformJson.createResponseJson(new ResponseClient.Builder()
				.status(ResponseChessboard.StatusResponse.INVALID.toString())
				.build());
		return Response.status(Response.Status.BAD_REQUEST)
				.entity(res).type(MediaType.APPLICATION_JSON).build();
	}

}
