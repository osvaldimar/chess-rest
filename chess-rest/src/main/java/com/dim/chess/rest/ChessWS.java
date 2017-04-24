package com.dim.chess.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.chess.core.client.ChessServiceRemote;
import com.chess.core.client.ResponseClient;
import com.chess.core.client.TransformJson;
import com.chess.core.service.ChessServiceImpl;

@RequestScoped
@Path(value = "/")
public class ChessWS {

	@Inject
	private ChessPoolService chessPool;
	private ChessServiceRemote service = new ChessServiceImpl();
	
	@Path("/startChessSingle")
	@GET
	@Produces(value = MediaType.APPLICATION_JSON)
	public String startChessSinglePlayer(){
		return TransformJson.createResponseJson(
				chessPool.getChessGamePool().joinSinglePlayerOnlineChessPool());
	}
	
	@Path("/startChessMulti")
	@GET
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseClient startChessMultiplayer(){
		return chessPool.getChessGamePool().joinMultiPlayerOnlineChessPool();
	}
	
	@Path("/move/{id}/{player}/{position}")
	@GET
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseClient selectAndMove(@PathParam("id") String id, @PathParam("player") String player, 
			@PathParam("position") String position){		
		service.play(chessPool.getChessGamePool().findGameApp(id, player));
		return service.selectAndMovePiece(position, player);
	}
	
	@Path("/verifyCheck/{id}/{player}")
	@GET
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseClient verifyCheck(@PathParam("id") String id, @PathParam("player") String player){
		service.play(chessPool.getChessGamePool().findGameApp(id, player));
		return service.verifyCheckmateTurn();
	}
	
	@Path("/promotion/{id}/{player}/{piece}")
	@GET
	@Produces(value = MediaType.APPLICATION_JSON)
	public ResponseClient promotion(@PathParam("id") String id, @PathParam("player") String player, 
			@PathParam("piece") String promotedPiece){		
		service.play(chessPool.getChessGamePool().findGameApp(id, player));
		return service.choosePromotion(promotedPiece, player);
	}
	
	@Path("/layout/{id}/{player}")
	@GET
	@Produces(value = MediaType.APPLICATION_JSON)
	public String layoutChessboard(@PathParam("id") String id, @PathParam("player") String player){
		service.play(chessPool.getChessGamePool().findGameApp(id, player));
		return service.getLayoutChessboard();
	}
	
	@Path("/layoutJson/{id}/{player}")
	@GET
	@Produces(value = MediaType.APPLICATION_JSON)
	public String layoutJsonChessboard(@PathParam("id") String id, @PathParam("player") String player){
		service.play(chessPool.getChessGamePool().findGameApp(id, player));
		return service.getSquaresChessboardJson();
	}
}
