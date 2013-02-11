
import java.util.Random;

public enum RandomNumberGenerator {	
	
	Poisson {
		@Override
		public double getRandomVal(Random r, double lambda) {
			double L = Math.exp(-lambda);
			int k = 0;
			double p = 1.0;
			do {
				k++;
				p = p * r.nextDouble();
			} while (p > L);

			return k - 1;
		}
	}, 
	Exponential {
		@Override
		public double getRandomVal(Random r, double p) {
			return -(Math.log(r.nextDouble()) / p);
		}
	}, 
	Geometric {
		@Override
		public double getRandomVal(Random r, double geoSeed) {
			double p = 1.0 / ((double) geoSeed);
			return (int)(Math.ceil(Math.log(r.nextDouble())/Math.log(1.0-p)));
		}
	}, 
	Pareto {
		@Override
		public double getRandomVal(Random r, double p) {
			//Force setting xM before any random value generation
			assert(false);
			return 0;
		}
		
		public double getRandomVal(Random r, double alpha, double xM) {
			double v = r.nextDouble();
			while (v == 0){
				v = r.nextDouble();
			}
			
			return xM / Math.pow(v, 1.0/alpha);
		}
	},
	ParetoBounded {
		@Override
		public double getRandomVal(Random r, double p) {
			//Force setting xM before any random value generation
			assert(false);
			return 0;
		}

		
		public double getRandomVal(Random r, double alpha, double L, double H) {
			double u = r.nextDouble();
			while (u == 0){
				u = r.nextDouble();
			}
			
			double x = -(u*Math.pow(H,alpha)-u*Math.pow(L,alpha)-Math.pow(H,alpha)) / 
							(Math.pow(H*L,alpha));
			return Math.pow(x, -1.0/alpha);
		}
	},	
	Uniform {
		@Override
		public double getRandomVal(Random r, double p) {
			return r.nextDouble() * p;
		}
	}, 
	Constant {
		@Override
		public double getRandomVal(Random r, double N) {			
			return N;
		}
	};

	public double getRandom(double... p){		
		return getRandom(defaultR, p);		
	}
	
	public double getRandomVal(Random r, double p){
		assert(false);
		return 0;
	}
	
	public double getRandomVal(Random r, double a, double b){
		assert(false);
		return 0;
	}

	public double getRandomVal(Random r, double a, double b, double c){
		assert(false);
		return 0;
	}
	
//	public double getRandom(Random r, double p, double aux){
//		// In most cases this function should not be called.
//		// Exceptions: Pareto
//		assert(false);
//		return 0;
//	}
	
	public double getRandom(Random r, double... p) {
		switch (this){
		case Poisson:
		case Exponential:
		case Geometric:
		case Uniform:
		case Constant:
			assert (p.length == 1);
			return getRandomVal(r, p[0]);
		case Pareto:
			assert (p.length == 2);
			return getRandomVal(r, p[0], p[1]);
		case ParetoBounded:
			assert (p.length == 3);
			return getRandomVal(r, p[0], p[1], p[2]);
		default:
			assert(false);
			
		}
	// TODO Auto-generated method stub
	return 0;
}
	
	public static final Random defaultR = new Random();

	public int getNumberParameters() {
		switch(this){
			case Poisson:
			case Exponential:
			case Geometric:
			case Uniform:
			case Constant:
				return 1;
			case Pareto: 
				return 2;
			case ParetoBounded:
				return 3;
			default:
				assert(false);		
				return 0;
		}		
	}

	public static final void main(String[] args){
		//Testing 
		
		RandomNumberGenerator testStat = Exponential;
		double lambda = 1.0 / 25.0;
		System.out.println("Testing stat: " + testStat+ " with lamda: " + lambda);
		for (int i = 0; i < 1000; i++){
			System.out.println(testStat.getRandom(lambda));
		}
		
		
	}

}
