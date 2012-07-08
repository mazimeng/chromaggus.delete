package mzm.de;

import java.util.Iterator;

public class QueuedTaskManager extends TaskManager {
	public void update(float elapsed){
		if(this.tasks.isEmpty()){
			return;
		}
		
		ITask task = this.tasks.getFirst();
		task.update(elapsed);
		
		if(task.completed()){
			this.tasks.pollFirst();
		}		
	}
}
