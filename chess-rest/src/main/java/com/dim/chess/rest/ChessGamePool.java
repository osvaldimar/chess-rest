package com.dim.chess.rest;

import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.chess.core.GameApplication;
import com.chess.core.ResponseChessboard;
import com.chess.core.client.KeyClient;
import com.chess.core.client.KeyUUIDChess;
import com.chess.core.client.PlayerMode;
import com.chess.core.client.ResponseClient;
import com.chess.core.enums.TypePlayer;
import com.chess.core.model.Difficulty;
import com.chess.core.model.Player;
import com.chess.core.service.ChessMultiplayerAI;
import com.chess.core.service.ChessMultiplayerOnline;
import com.dim.chess.ai.PlayerMachineAI;

public class ChessGamePool {
	
	private Map<KeyUUIDChess, GameApplication> map = new ConcurrentHashMap<>();
	private Queue<KeyClient> queue = new LinkedList<>();
	
	public GameApplication findGameApp(String uuid, String typePlayer) {
		try{
			if(TypePlayer.getEnum(typePlayer) == TypePlayer.W){
				KeyUUIDChess key = new KeyUUIDChess(UUID.fromString(uuid), null);
				Optional<KeyUUIDChess> findFirst = map.keySet().stream().filter(k -> k.equals(key)).findFirst();
				if(findFirst.isPresent()) 
					return map.get(findFirst.get());
			}else if(TypePlayer.getEnum(typePlayer) == TypePlayer.B){
				KeyUUIDChess key = new KeyUUIDChess(null, UUID.fromString(uuid));
				Optional<KeyUUIDChess> findFirst = map.keySet().stream().filter(k -> k.equals(key)).findFirst();
				if(findFirst.isPresent()) 
					return map.get(findFirst.get());
			}
		} catch(NumberFormatException e) {
			return null;
		}
		return null;
	}

	public ResponseClient joinSinglePlayerOnlineChessPool() {
		UUID singleUuid = UUID.randomUUID();
		KeyClient keyClientW = new KeyClient(singleUuid, TypePlayer.W);	
		KeyClient keyClientB = new KeyClient(singleUuid, TypePlayer.B);			
		ChessMultiplayerOnline chessOnline = new ChessMultiplayerOnline();
		GameApplication game = chessOnline.startChess(new Player(TypePlayer.W), new Player(TypePlayer.B));
		map.put(new KeyUUIDChess(keyClientW.getKey(), keyClientB.getKey()), game);
		return this.buildResponseClientStartAdequateForThePlayer(keyClientW);
	}
	
	public ResponseClient joinMultiPlayerOnlineChessPool() {
		KeyClient keyClient = this.joinPoolGameMultiAddQueue();
		while(true){
			try {
				if(this.isJoinPoolGameMulti(keyClient))
					break;
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return buildResponseClientStartAdequateForThePlayer(keyClient);
	}
	
	public ResponseClient joinMultiPlayerAIOnlineChessPool(Difficulty difficultyAI) {
		UUID singleUuid = UUID.randomUUID();
		KeyClient keyClientW = new KeyClient(singleUuid, TypePlayer.W);	
		KeyClient keyClientB = new KeyClient(singleUuid, TypePlayer.B);
		ChessMultiplayerAI chessOnline = new ChessMultiplayerAI();
		PlayerMode[] players = this.randomPlayerCommonAndAI(difficultyAI);
		GameApplication game = chessOnline.startChess(players[0], players[1]);
		map.put(new KeyUUIDChess(keyClientW.getKey(), keyClientB.getKey()), game);
		return this.buildResponseClientStartAdequateForThePlayer(!players[0].isAI() ? keyClientW : keyClientB);
	}
	
	private PlayerMode[] randomPlayerCommonAndAI(Difficulty difficultyAI){
		PlayerMode[] players = {new Player(TypePlayer.W), new Player(TypePlayer.B)};
		int r = new Random().nextInt(2);
		players[r] = new PlayerMachineAI(players[r].getTypePlayer(), difficultyAI);
		return players;
	}
	
	private ResponseClient buildResponseClientStartAdequateForThePlayer(KeyClient keyClient){
		return new ResponseClient.Builder()
				.status(ResponseChessboard.StatusResponse.START.toString())
				.currentPlayer(TypePlayer.W.toString())
				.turn(TypePlayer.W.toString())
				.keyClientID(keyClient.getKey().toString())
				.keyClientType(keyClient.getType().toString())
				.build();
	}
	
	private synchronized KeyClient joinPoolGameMultiAddQueue(){
		if(queue.isEmpty()){
			KeyClient keyClient = new KeyClient(UUID.randomUUID(), TypePlayer.W);
			queue.add(keyClient);
			System.out.println("Thread name: " + Thread.currentThread().getName() + " - queue new W = " + keyClient);
			return keyClient;
		}else{
			KeyClient keyClientW = queue.remove();
			KeyClient keyClientB = new KeyClient(UUID.randomUUID(), TypePlayer.B);			
			ChessMultiplayerOnline chessOnline = new ChessMultiplayerOnline();
			GameApplication game = chessOnline.startChess(new Player(TypePlayer.W), new Player(TypePlayer.B));
			map.put(new KeyUUIDChess(keyClientW.getKey(), keyClientB.getKey()), game);
			System.out.println("Thread name: " + Thread.currentThread().getName() + " - queue new B = " + keyClientB);
			return keyClientB;
		}
	}
	
	private synchronized boolean isJoinPoolGameMulti(KeyClient keyClient){
		GameApplication findGameApp = this.findGameApp(keyClient.getKey().toString(), keyClient.getType().toString());
		return findGameApp != null;
	}

	public int getTotalChessPool(){
		return this.map.size();
	}	
	public int getTotalChessQueuePending(){
		return this.queue.size();
	}
}
