package mzm.library;


public class NormalHelper {
//	public static void calculateFaceNormal(float[] p0, float[] p1, float[] p2, float[] out, int offset){
//    	//float[] u = new float[3];
//    	//float[] v = new float[3];
//    	
//    	Vector3f vec0 = new Vector3f(p0[0], p0[1], p0[2]);
//    	Vector3f vec1 = new Vector3f(p1[0], p1[1], p1[2]);
//    	Vector3f vec2 = new Vector3f(p2[0], p2[1], p2[2]);
//    	
//    	
//    	Triangle t = new Triangle(vec0, vec1, vec2);
//    	t.calculateNormal();
//    	Vector3f n = t.getNormal();
//    	out[offset+0] = n.x;
//    	out[offset+1] = n.y;
//    	out[offset+2] = n.z;
//    }
//	
//	public static Vector3f computeFaceNormal(Vector3f p0, Vector3f p1, Vector3f p2){
//		Vector3f u = new Vector3f();
//		Vector3f v = new Vector3f();
//		Vector3f n = new Vector3f();
//		
//		u = p1.subtract(p0);
//		v = p2.subtract(p0);
//    	
//    	n = v.cross(u);
//    	n.normalizeLocal();
//    	
//    	return n;
//    	
////    	Vector3.normalize(n);
////    	Vector3.scale(n, -1.0f, n);
////    	out[offset]=n[0];
////    	out[offset+1]=n[1];
////    	out[offset+2]=n[2];
//
//	}
//	
//	public static Vector3f[] computeVertexNormals(Vector3f[] vertexes, int[] indexes){
//		Vector3f[] faceNormals = new Vector3f[indexes.length/3];
//		Vector3f[] vertexNormals = new Vector3f[vertexes.length];
//		
//		int num = 0;
//		
//		for(int j=0; j<indexes.length/3; j++){
//			faceNormals[j] = computeFaceNormal(vertexes[indexes[3*j]], vertexes[indexes[3*j+1]], vertexes[indexes[3*j+2]]);				
//		}
//		
//		for(int j=0; j<vertexes.length; j++){
//			num = 0;
//			
//			Vector3f norm = new Vector3f();
//			
//			
//			for(int k=0; k<indexes.length/3; k++){
//				if(j==indexes[3*k] || j==indexes[3*k+1] || j==indexes[3*k+2]){
//					num++;		
//					norm.addLocal(faceNormals[k]);
//				}
//			}
//			
////			if(num>0){
////	        	norm.multLocal(1.0f/(float)num);
////	        }
//			
//			norm.normalizeLocal();
//			vertexNormals[j] = norm; 	        	
//		}
//		
//		return vertexNormals;
//	}
//	
//	public static void computeVertexNormals(float[] vertices, int[] indices, float[] vertexNormals){
//		float[] faceNormals = new float[indices.length];
//		
//		float[] vert = new float[3];
//		//float[] norm = new float[3];
//		
//		int num = 0;
//		
//		for(int j=0; j<indices.length; j+=3){
//			float[] p0 = {vertices[3*indices[j]], vertices[3*indices[j]+1], vertices[3*indices[j]+2]};
//			float[] p1 = {vertices[3*indices[j+1]], vertices[3*indices[j+1]+1], vertices[3*indices[j+1]+2]};
//			float[] p2 = {vertices[3*indices[j+2]], vertices[3*indices[j+2]+1], vertices[3*indices[j+2]+2]};
//
//			calculateFaceNormal(p0, p1, p2, faceNormals, j);				
//		}
//		
//		for(int j=0; j<vertices.length/3; j++){
//			num = 0;
////			norm[0] = 0.0f;
////			norm[1] = 0.0f; 
////			norm[2] = 0.0f; 
//			Vector3f norm = new Vector3f();
//			
//			
//			for(int k=0; k<indices.length; k+=3){
//				if(j==indices[k] || j==indices[k+1] || j==indices[k+2]){
//					num++;
//					Vector3f faceNormal = new Vector3f(faceNormals[k], faceNormals[k+1], faceNormals[k+2]);
//					
//					norm.addLocal(faceNormal);
//					//Vector3.add(norm, 0, faceNormals, k, norm, 0);
//				}
//			}
//			
//			if(num>0){
//	        	//Vector3.scale(norm, 1.0f/(float)num, norm);
//	        	norm.multLocal(1.0f/(float)num);
//	        }
//			
//			norm.normalizeLocal();
//			vertexNormals[j*3] = norm.x; 
//	        vertexNormals[j*3+1] = norm.y;
//	        vertexNormals[j*3+2] = norm.z;			
//		}
//	}
}
