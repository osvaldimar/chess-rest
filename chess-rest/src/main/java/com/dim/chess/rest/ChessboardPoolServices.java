package com.dim.chess.rest;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import com.chess.core.service.ChessServiceImpl;

@Named
@ApplicationScoped
public class ChessboardPoolServices {

	Map<Integer, ChessServiceImpl> mapServices = new HashMap<>();

	public ChessboardPoolServices(){
		
	}
	
	public synchronized ChessServiceImpl findById(Integer id) {
		return mapServices.get(1);
	}
	
	public synchronized ChessServiceImpl create() {
		mapServices.put(1, new ChessServiceImpl());
		return mapServices.get(1);
	}

	public Map<Integer, ChessServiceImpl> getMapServices() {
		return mapServices;
	}

	public void setMapServices(Map<Integer, ChessServiceImpl> mapServices) {
		this.mapServices = mapServices;
	}

	
}
