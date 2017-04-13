package com.dim.chess.rest;

import java.util.HashMap;
import java.util.Map;

import com.chess.core.service.ChessService;


public class ChessboardPoolServices {

	Map<Integer, ChessService> mapServices = new HashMap<>();
	
	public synchronized ChessService findById(Integer id) {
		return mapServices.get(1);
	}
	
	public synchronized ChessService create() {
		mapServices.put(1, new ChessService());
		return mapServices.get(1);
	}

	public Map<Integer, ChessService> getMapServices() {
		return mapServices;
	}

	public void setMapServices(Map<Integer, ChessService> mapServices) {
		this.mapServices = mapServices;
	}

	
}
