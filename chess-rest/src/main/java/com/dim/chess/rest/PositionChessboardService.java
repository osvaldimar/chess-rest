package com.dim.chess.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.chess.core.ResponseChessboard;
import com.chess.core.enums.PositionChessboard;
import com.chess.core.enums.TypePlayer;
import com.chess.core.service.ChessService;

@Path(value = "/")
@Consumes(value = MediaType.APPLICATION_JSON)
@Produces(value = MediaType.APPLICATION_JSON)
public class PositionChessboardService {

	@Path("/{position}/{player}")
	@GET
	public PositionChessboard getPos(@PathParam("position") String position, @PathParam("player") String player){
		PositionChessboard posChess = PositionChessboard.getEnum(position);
		TypePlayer type = TypePlayer.getEnum(player);
		Response res = Response.status(200).entity("ok").build();
		return posChess;
	}
	
	@Path("/startChess")
	@GET
	public String startChess(){
		ChessService service = new ChessService();
		return service.startChess();
	}
	
	@Path("/verifyCheck")
	@GET	
	public String verifyCheck(){
		ChessService service = new ChessService();
		return service.verifyCheckmateTurn();
	}
	
	
}
