package mzm.de;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import mzm.de.interfaces.IGameLogic;
import mzm.de.interfaces.ISceneNode;

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
	
	public void accept(IGameActorVisitor visitor){
		for(Map.Entry<Integer, GameActor> entry : actors.entrySet()){
			visitor.visit(entry.getValue());
		}
	}
	
	public Map<Integer, GameActor> getActors(){
		return Collections.unmodifiableMap(actors);
	}
}
