public class NBody{
	
	public static double readRadius(String fileName){
		
		In in = new In(fileName);
		int planetsNo = in.readInt();
		double radius = in.readDouble();
		return radius;
	}
	public static Planet[] readPlanets(String fileName){
		In in = new In(fileName);
		int planetsNo = in.readInt();
		double radius = in.readDouble();
		Planet[] planetsArray = new Planet[planetsNo];
		int k=0;
		while(k < planetsNo){
			double xP =  in.readDouble();
			double yP =  in.readDouble();
			double xV =  in.readDouble();
			double yV =  in.readDouble();
			double m =  in.readDouble();
	   		String img =  in.readString();
			planetsArray[k] = new Planet(xP, yP, xV, yV, m, img);
			k++;
		}
		return planetsArray;
	}

	public static void main(String[] args) {
		double T = Double.parseDouble(args[0]);
		double dt= Double.parseDouble(args[1]);
		String filename = args[2];
		double r = readRadius(filename);
		Planet[] Planets = readPlanets(filename);
		String background = "images/starfield.jpg";
		StdDraw.setScale(-r, r);
		StdDraw.clear();
		StdDraw.picture(0, 0, background);
		int n=0;
		while(n < Planets.length){
			Planets[n].draw();
			n++;
		}
		StdDraw.show();	
		
		
		StdDraw.enableDoubleBuffering();

		double time = 0;
		double[] xForces = new double[Planets.length]; 
		double[] yForces = new double[Planets.length];
		while(time <= T){
			 
			int i = 0;
			while(i < Planets.length){
				xForces[i] = Planets[i].calcNetForceExertedByX(Planets);
				yForces[i] = Planets[i].calcNetForceExertedByY(Planets);
				i++;
			}
			int j = 0;
			while(j < Planets.length){
				Planets[j].update(dt, xForces[j],yForces[j]);
				j++;
			}
			StdDraw.clear();
			StdDraw.picture(0, 0, background);
			int a = 0;
			while(a < Planets.length){
				Planets[a].draw();
				a++;
				}
			StdDraw.show();	
			StdDraw.pause(10);
			time = time + dt;
		}
	}
}