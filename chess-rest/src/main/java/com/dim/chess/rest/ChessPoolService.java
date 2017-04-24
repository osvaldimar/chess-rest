package com.dim.chess.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import com.chess.core.ChessGamePool;

@Named
@ApplicationScoped
public class ChessPoolService {

	private ChessGamePool chessGamePool = new ChessGamePool();

	public ChessGamePool getChessGamePool() {
		return chessGamePool;
	}

	public void setChessGamePool(ChessGamePool chessGamePool) {
		this.chessGamePool = chessGamePool;
	}
	
}
