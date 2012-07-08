package mzm.library;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import mzm.de.IViewport;
import mzm.de.interfaces.ICamera;
import mzm.de.interfaces.ISceneNode;

public class SceneGraph {
	protected HashMap<Integer, ISceneNode> nodes;
	protected LinkedList<IViewport> viewports;
	
	
	public SceneGraph(){
		this.nodes = new HashMap<Integer, ISceneNode>();
	}
	
	public void render(float elapsed){
		for(IViewport viewport: this.viewports){
			for(Map.Entry<Integer, ISceneNode> entry : nodes.entrySet()){
				entry.getValue().render(viewport, elapsed);
			}
		}
	}
	
	public void addNode(ISceneNode node){
		this.nodes.put(node.getId(), node);
	}
	
	public ISceneNode getNode(int id){
		ISceneNode node = this.nodes.get(id);
		return node;
	}
	
	public void removeNode(int id){
		this.nodes.remove(id);
	}
}
