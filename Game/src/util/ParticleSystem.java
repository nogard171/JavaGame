package util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class ParticleSystem {
	private ArrayList<Particle> particles = new ArrayList<Particle>(500);
	 
	 public void addRain(int width, int height,Color color){
	        int dx=0,dy =0;
	        int newX = 0;
	        //if(bool){
	            //dx = (int) (Math.random()*5);
	            dy = (int) (Math.random()*5);
	            newX = (int)(Math.random()*width);
	        /*}
	        else{
	           // dx = (int) (Math.random()*-5);
	            //dy = (int) (Math.random()*-5);
	        }*/
	           
	        int size = 1;
	        int life = (int) Math.random()*(120)+height;
	        particles.add(new Particle(newX,-1,dx,dy,size,life,color));
	    }
	 public void renderParticles(Graphics2D g2d){
	        for(Particle particle:particles){
	            particle.render(g2d);
	        }
	    }
	 public void updateParticles()
	 {
		 for(int i =0;i<particles.size()-1;i++){
	            if(particles.get(i).update())
	                particles.remove(i);
	        }
	 }
}
