package com.dim.chess.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.chess.core.client.ChessServiceRemote;
import com.chess.core.client.TransformJson;
import com.chess.core.model.Difficulty;
import com.chess.core.service.ChessServiceImpl;
import com.dim.chess.rest.exception.ChessParametersException;

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
				chessPool.joinSinglePlayerOnlineChessPool());
	}
	
	@Path("/startChessMulti")
	@GET
	@Produces(value = MediaType.APPLICATION_JSON)
	public String startChessMultiplayer(){
		return TransformJson.createResponseJson(chessPool.joinMultiPlayerOnlineChessPool());
	}
	
	@Path("/startChessMultiAI/{level}")
	@GET
	@Produces(value = MediaType.APPLICATION_JSON)
	public String startChessMultiplayerAI(@PathParam("level") int level) throws ChessParametersException{
		Difficulty.SimpleDifficulty simpleDifficulty = Difficulty.SimpleDifficulty.getEnum(level);
		if(simpleDifficulty == null)
			throw new ChessParametersException();
		Difficulty difficultyAI = Difficulty.createSimpleDifficulty(simpleDifficulty);
		return TransformJson.createResponseJson(chessPool.joinMultiPlayerAIOnlineChessPool(difficultyAI));
	}
	
	@Path("/selectMove/{id}/{player}/{position}")
	@GET
	@Produces(value = MediaType.APPLICATION_JSON)
	public String selectMove(@PathParam("id") String id, @PathParam("player") String player, 
			@PathParam("position") String position) throws ChessParametersException{		
		service.play(chessPool.findGameAppInChessPool(id, player));
		return TransformJson.createResponseJson(service.selectAndMovePiece(position, player));
	}
	
	@Path("/moveDirect/{id}/{player}")
	@GET
	@Produces(value = MediaType.APPLICATION_JSON)
	public String moveDirect(@PathParam("id") String id, @PathParam("player") String player, 
			@QueryParam("from") String origin, @QueryParam("to") String destiny) 
					throws ChessParametersException{
		service.play(chessPool.findGameAppInChessPool(id, player));
		//service.clearPieceClickedMarkOff();
		service.selectAndMovePiece(origin, player);
		return TransformJson.createResponseJson(service.selectAndMovePiece(destiny, player));
	}
	
	@Path("/verifyCheck/{id}/{player}")
	@GET
	@Produces(value = MediaType.APPLICATION_JSON)
	public String verifyCheck(@PathParam("id") String id, @PathParam("player") String player) 
			throws ChessParametersException{
		service.play(chessPool.findGameAppInChessPool(id, player));
		return TransformJson.createResponseJson(service.verifyCheckmateTurn(player));
	}
	
	@Path("/promotion/{id}/{player}/{piece}")
	@GET
	@Produces(value = MediaType.APPLICATION_JSON)
	public String promotion(@PathParam("id") String id, @PathParam("player") String player, 
			@PathParam("piece") String promotedPiece) throws ChessParametersException{		
		service.play(chessPool.findGameAppInChessPool(id, player));
		return TransformJson.createResponseJson(service.choosePromotion(promotedPiece, player));
	}
	
	@Path("/layout/{id}/{player}")
	@GET
	@Produces(value = MediaType.APPLICATION_JSON)
	public String layoutChessboard(@PathParam("id") String id, @PathParam("player") String player) 
			throws ChessParametersException{
		service.play(chessPool.findGameAppInChessPool(id, player));
		return service.getLayoutChessboard();
	}
	
	@Path("/layoutJson/{id}/{player}")
	@GET
	@Produces(value = MediaType.APPLICATION_JSON)
	public String layoutJsonChessboard(@PathParam("id") String id, @PathParam("player") String player) 
			throws ChessParametersException{
		service.play(chessPool.findGameAppInChessPool(id, player));
		return service.getSquaresChessboardJson();
	}
	
	@Path("/totalGameChessOnPool")
	@GET
	@Produces(value = MediaType.TEXT_HTML)
	public String totalGameChessOnPool() {
		return this.chessPool.totalGameChessOnPool();
	}
	@Path("/cleanChessGamePool")
	@GET
	@Produces(value = MediaType.TEXT_HTML)
	public String cleanChessGamePool() {
		this.chessPool.cleanChessGamePool();
		return "Clean ok - " + this.chessPool.totalGameChessOnPool();
	}
}
