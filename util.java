import java.util.*;
//checked 
public class util {
// good returns the sigmoid 1/(1-e^-x);
	public static double sigmoid(double in) {
		return 1 / (1 + Math.exp(-in));
	}
// good - reterns the derivative of the sigmoid e^-x/(1-e^-x)^2
	public static double sigmoidDeriv(double in) {
		double sigmoid = sigmoid(in);
		return sigmoid * (1 - in);
	}

	// 1 = t(1+e^-x)
	// 1 = t +t(e^-x)
	// 1-t = t(e^-x)
	// 1/t -1 = e^-x
	// log(1/t -1) = -xlog(e )
	// log(1/t -1)/log(e) = -x
	// -1(log(1/t -1)/log(e)) = x
	// good - takes a sigmoid output and spits a number corresponding to the input.
	public static double unSigmoid(double in) {

		double unSigmoid = (Math.log10((1 / in) - 1));
		unSigmoid = unSigmoid / Math.log10(Math.E);
		unSigmoid = (-1 * unSigmoid);
		return unSigmoid;
	}
// good - gives the meanSquareLoss
	public static double meanSquareLoss(List<Double> correctAnswers, List<Double> predictedAnswers) {
		double sumSquare = 0;
		for (int i = 0; i < correctAnswers.size(); i++) {
			double error = correctAnswers.get(i) - predictedAnswers.get(i);
			sumSquare += (error * error);
		}
		return sumSquare / correctAnswers.size();
	}
	// good - rounds
	public static double round(double num, int place) {
		double roundedNumber = Math.round(num * Math.pow(10, place)) / Math.pow(10, place);
		return (roundedNumber);
	}
	//good - gives the number of ways the weight/neuron influences the cost function
	public static int pathCount(ArrayList<ArrayList<Neuron>> net, int layer, int neuron) {
		int paths = 1;
		for (int l = layer + 1; l < net.size(); l++) {
			paths = paths * net.get(l).size();
		}

		return paths;

	}
	// re check and understand - gives a arraylist of ints the position in the arraylist representing the layers starting from the neurons layer and the value representing the neuron in that layer, for a specific path. 
	public static ArrayList<Integer> pathFinder(int path, int layer, int neuronNum, ArrayList<ArrayList<Neuron>> net) {
		ArrayList<Integer> pathCode = new ArrayList<Integer>();
		int count = 1;
		for (int w = net.size() - layer - 1; w >= 0; w--) { 
			pathCode.add(null);
		}
		for (int w = net.size() - layer - 1; w >= 0; w--) {
			pathCode.set(w, count);
			count = net.get(layer + w).size() * count;
		}
		for (int q = 0; q < net.size() - layer; q++) {
			int d = pathCode.get(q);
			pathCode.set(q, (path / pathCode.get(q)));
			path = path % d;
		}
		pathCode.set(0, neuronNum);
		return pathCode;
	}
}
