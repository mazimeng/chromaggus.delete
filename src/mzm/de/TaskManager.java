package mzm.de;

import java.util.Iterator;
import java.util.LinkedList;

public class TaskManager {
	protected LinkedList<ITask> tasks;
	
	public TaskManager(){
		tasks = new LinkedList<ITask>();
	}
	
	public void addTask(ITask task){
		this.tasks.add(task);
	}
	
	public void update(float elapsed){
		for(Iterator<ITask> i=this.tasks.iterator(); i.hasNext();){
			ITask task = i.next();
			if(task.completed()){
				i.remove();
			}
			else{
				task.update(elapsed);
			}
		}
	}
}
