package com.dim.chess.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
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
	@Produces(value = MediaType.APPLICATION_JSON)
	public String startChess(){
		ChessServiceImpl service = chessboards.create();
		return service.startChess();
	}
	
	@Path("/move/{id}/{player}/{position}")
	@GET
	@Produces(value = MediaType.APPLICATION_JSON)
	public String selectAndMove(@PathParam("id") String id, @PathParam("player") String player, 
			@PathParam("position") String position){		
		ChessServiceImpl service = chessboards.findById(id);
		if(service != null){
			return service.selectAndMovePiece(position, player);
		}
		return "INVALID";
	}
	
	@Path("/verifyCheck/{id}/{player}")
	@GET
	@Produces(value = MediaType.APPLICATION_JSON)
	public String verifyCheck(@PathParam("id") String id, @PathParam("player") String player){
		ChessServiceImpl service = chessboards.findById(id);
		if(service != null){
			return service.verifyCheckmateTurn();
		}
		return "INVALID";
	}
	
	@Path("/promotion/{id}/{player}/{piece}")
	@GET
	@Produces(value = MediaType.APPLICATION_JSON)
	public String promotion(@PathParam("id") String id, @PathParam("player") String player, 
			@PathParam("piece") String promotedPiece){		
		ChessServiceImpl service = chessboards.findById(id);
		if(service != null){
			return service.choosePromotion(promotedPiece, player);
		}
		return "INVALID";
	}
	
	@Path("/layout/{id}")
	@GET
	@Produces(value = MediaType.APPLICATION_JSON)
	public String layoutChessboard(@PathParam("id") String id){
		ChessServiceImpl service = chessboards.findById(id);
		if(service != null){
			return service.getLayoutChessboard();
		}
		return "INVALID";
	}
	
	@Path("/layoutJson/{id}")
	@GET
	@Produces(value = MediaType.APPLICATION_JSON)
	public String layoutJsonChessboard(@PathParam("id") String id){
		ChessServiceImpl service = chessboards.findById(id);
		if(service != null){
			return service.getSquaresChessboardJson();
		}
		return "INVALID";
	}
}
