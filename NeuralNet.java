package cp312;

public class NeuralNet {
	int num_layers;
	int [] sizes;
	static double [][][] biases;
	static double [][][] weights;
	
	NeuralNet(int[]arr) {
		num_layers=arr.length;
		System.out.println(num_layers);
		sizes=arr;
		biases = new double [num_layers-1][][];
		weights = new double [num_layers-1][][];
		for (int i=0;i<num_layers-1;i++) {
			biases[i]=np.random(sizes[i+1], 1);
			weights[i]=np.random(sizes[i+1], sizes[i]);
		}
		
	}
	
	public static double[][] ff(double [][]a){
		
		for (int i=0;i<biases.length;i++) {
			for (int j=0;j<weights.length;j++) {
				System.out.println(weights[j][0].length+""+a.length);
				a=np.sigmoid(np.add(np.dot(weights[j],a),biases[i]));
			}
		}
		return a;
	}
	

	
	
	public static void main(String[] args) {
		
		int [] layers={3,5,8};
		double [][] asdf={{53,21,33},{1,3,4},{3,4,5}};
		NeuralNet net=new NeuralNet(layers) ;
		
		for (int i=0;i<net.weights.length;i++) {
			for (int j=0;j<net.weights[i].length;j++) {
				for (int k=0;k<net.weights[i][j].length;k++) {
					System.out.print(net.weights[i][j][k]);
				}
				System.out.println();
			}
			System.out.println();
		}
		
		System.out.println(np.softmax(ff(asdf)));
		
	}
	
}
