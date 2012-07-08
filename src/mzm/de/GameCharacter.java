package mzm.de;

import java.util.LinkedList;
import java.util.Queue;

import mzm.library.Vector2f;

public class GameCharacter extends GameActor{
	protected float speed;
	protected Vector2f position;
	protected QueuedTaskManager taskManager;
	
	public GameCharacter(float speed, Vector2f position){
		this.speed = speed;
		this.position = new Vector2f(position);
		this.taskManager = new QueuedTaskManager();
	}
	
	public Vector2f getPosition(){
		return new Vector2f(this.position);
	}
	
	public void update(float elapsed){
		this.taskManager.update(elapsed);
	}
}
