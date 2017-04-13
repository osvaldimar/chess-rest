package com.dim.chess.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.chess.core.service.ChessService;
import com.dim.chess.rest.model.PositionPlayer;

@Path(value = "/")
public class ChessWS {

	//@Inject
	private ChessboardPoolServices chessboards = new ChessboardPoolServices();
	
	@Path("/move")
	@GET
	@Consumes(value = MediaType.APPLICATION_JSON)
	@Produces(value = MediaType.APPLICATION_JSON)
	public String getPos(PositionPlayer model){
		ChessService service = chessboards.findById(model.getId());
		if(service != null){
			return service.selectAndMovePiece(model.getPosition(), model.getPlayer());
		}
		return "INVALID";
	}
	
	@Path("/startChess")
	@GET
	public String startChess(){
		ChessService service = chessboards.create();
		return service.startChess();
	}
	
	@Path("/verifyCheck")
	@GET
	@Consumes(value = MediaType.APPLICATION_JSON)
	@Produces(value = MediaType.APPLICATION_JSON)
	public String verifyCheck(PositionPlayer model){
		ChessService service = chessboards.findById(model.getId());
		if(service != null){
			return service.verifyCheckmateTurn();
		}
		return "INVALID";
	}
	
	
}
