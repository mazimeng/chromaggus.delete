package mzm.de;

import java.util.HashMap;

import mzm.de.interfaces.IGameLogic;

public class GameLogic implements IGameLogic{
	protected HashMap<Integer, GameActor> actors;
	
	public GameLogic(){
		this.actors = new HashMap<Integer, GameActor>();
	}
	
	public void update(float elapsed){
		
	}
	
	public void addActor(GameActor actor){
		this.actors.put(actor.getId(), actor);
	}
}
