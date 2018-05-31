public class Planet{
	double xxPos;
	//current position
	double yyPos;
	double xxVel;
	//current velocity
	double yyVel;
	double mass;
	String imgFileName;
	public Planet(double xP, double yP, double xV,
				  double yV, double m, String img){
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img; 
	}
	public Planet(Planet p){
		xxPos = p.xxPos;
		yyPos = p.yyPos;
		xxVel = p.xxVel;
		yyVel = p.yyVel;
		mass = p.mass;
		imgFileName = p.imgFileName;
	}
	public double calcDistance(Planet p){
		double dx = p.xxPos - xxPos;
		double dy = p.yyPos - yyPos;
		double r2 = dx*dx + dy*dy;
		double r = Math.sqrt(r2);
		return r;
	}
	public double calcForceExertedBy(Planet p){
		double G = 6.67*Math.pow(10, -11);
		double F = G*p.mass*mass/(calcDistance(p)*calcDistance(p));
		return F;
	}
	public double calcForceExertedByX(Planet p){
		double dx = p.xxPos - xxPos;
		double Fx = calcForceExertedBy(p)*dx/calcDistance(p);
		//Don't forget to return a value!
		return Fx;
	}
	public double calcForceExertedByY(Planet p){
		double dy = p.yyPos - yyPos;
		double Fy = calcForceExertedBy(p)*dy/calcDistance(p);
		return Fy;
	}
	//constructor parameter: first dataType then dataName
	public double calcNetForceExertedByX(Planet[] allPlanets){
		int i = 0;
		double netFx = 0;
		while(i < allPlanets.length) {
			if(allPlanets[i].xxPos != xxPos || allPlanets[i].yyPos != yyPos){
				netFx = netFx + calcForceExertedByX(allPlanets[i]);
			}
			i++;
		}
		return netFx;	
	}
	public double calcNetForceExertedByY(Planet[] allPlanets){
		int j = 0;
		double netFy = 0;
		while(j < allPlanets.length) {
			if(allPlanets[j].xxPos != xxPos || allPlanets[j].yyPos != yyPos){
				netFy = netFy + calcForceExertedByY(allPlanets[j]);
			}
			j++;
		}
		return netFy;	
	}
	public void update(double dt, double fX, double fY){
		//acceleration
		double ax = fX/mass;
		double ay = fY/mass;
		xxVel = xxVel + dt*ax;
		yyVel = yyVel + dt*ay;
		xxPos = xxPos + dt*xxVel;
		yyPos = yyPos + dt*yyVel;
	}
	public void draw(){
		String imgPath = "images/" + imgFileName;
		StdDraw.picture(xxPos, yyPos, imgPath);
	}
}