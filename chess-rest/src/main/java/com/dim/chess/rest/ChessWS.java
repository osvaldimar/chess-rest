package com.dim.chess.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.chess.core.service.ChessServiceImpl;

@RequestScoped
@Path(value = "/")
public class ChessWS {

	@Inject
	private ChessboardPoolServices chessboards;
	
	@Path("/startChess")
	@GET
	@Consumes(value = MediaType.APPLICATION_JSON)
	@Produces(value = MediaType.APPLICATION_JSON)
	public String startChess(){
		ChessServiceImpl service = chessboards.create();
		return service.startChess();
	}
	
	@Path("/move/{id}/{player}/{position}")
	@GET
	@Consumes(value = MediaType.APPLICATION_JSON)
	@Produces(value = MediaType.APPLICATION_JSON)
	public String getPos(@PathParam("id") int id, @PathParam("player") String player, 
			@PathParam("position") String position){
		
		ChessServiceImpl service = chessboards.findById(id);
		if(service != null){
			return service.selectAndMovePiece(position, player);
		}
		return "INVALID";
	}
	
	@Path("/verifyCheck/{id}/{player}")
	@GET
	@Consumes(value = MediaType.APPLICATION_JSON)
	@Produces(value = MediaType.APPLICATION_JSON)
	public String verifyCheck(@PathParam("id") int id, @PathParam("player") String player){
		ChessServiceImpl service = chessboards.findById(id);
		if(service != null){
			return service.verifyCheckmateTurn();
		}
		return "INVALID";
	}
	
	
}
