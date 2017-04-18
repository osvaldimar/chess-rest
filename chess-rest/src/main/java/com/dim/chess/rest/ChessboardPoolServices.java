package com.dim.chess.rest;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import com.chess.core.service.ChessService;

@Named
@ApplicationScoped
public class ChessboardPoolServices {

	Map<Integer, ChessService> mapServices = new HashMap<>();

	public ChessboardPoolServices(){
		
	}
	
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
