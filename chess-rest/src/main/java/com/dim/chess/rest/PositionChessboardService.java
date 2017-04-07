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
public class PositionChessboardService {

	@Path("/{position}/{player}")
	@GET
	public String getPos(@PathParam("position") String position, @PathParam("player") String player){
		ChessService service = new ChessService();
		return service.selectAndMovePiece(position, player);
	}
	
	@Path("/startChess")
	@GET
	@Consumes(value = MediaType.APPLICATION_JSON)
	@Produces(value = MediaType.APPLICATION_JSON)
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
