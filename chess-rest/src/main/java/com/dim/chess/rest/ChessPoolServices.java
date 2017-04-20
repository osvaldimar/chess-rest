package com.dim.chess.rest;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import com.chess.core.service.ChessServiceImpl;

@Named
@ApplicationScoped
public class ChessPoolServices {

	Map<String, ChessServiceImpl> mapServices = new HashMap<>();

	public ChessPoolServices(){
		
	}
	
	public synchronized ChessServiceImpl findById(String id) {
		return mapServices.get(id);
	}
	
	public synchronized ChessServiceImpl create() {
		ChessServiceImpl service = new ChessServiceImpl();
		mapServices.put(service.getUuidChess().toString(), service);
		return mapServices.get(service.getUuidChess().toString());
	}

	public Map<String, ChessServiceImpl> getMapServices() {
		return mapServices;
	}

	public void setMapServices(Map<String, ChessServiceImpl> mapServices) {
		this.mapServices = mapServices;
	}

	
}
