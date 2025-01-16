import java.util.*;

//checked
public class Neuron {
//	instance variables - bias to store the bias , weight arraylist to hold the weights
	public double bias;
	public ArrayList<Double> weights = new ArrayList<Double>();

//	good - constructor, the parameter weightsNum is the size of the weights arraylist
	public Neuron(int weightsNum) {
		bias = -2 + (double) (Math.random() * (4));
		bias = 0;
		for (int q = 0; q < weightsNum; q++) {
			weights.add(q, -2 + (double) (Math.random() * (4)));
		}
		System.out.println(" weights:" + weights.subList(0, weights.size()) + " and the bias :" + util.round(bias, 2));
	}

//	good - computes the output of the neuron based on the output of previous neurons. 
	public double comput(ArrayList<Double> input) {
		double compOutput = 0;

		for (int q = 0; q < input.size(); q++) {
			System.out.print(" input #" + q + " = " + util.round(input.get(q), 2));

			System.out.println("  compOutput += input:" + util.round(input.get(q), 2) + " * weight:"
					+ util.round(weights.get(q), 2));
			compOutput += (weights.get(q) * input.get(q));
			if (q == (input.size() - 1)) {
				System.out.println("sig ( compOutput:" + util.round(compOutput, 2) + " + bias:" + util.round(bias, 2)
						+ " ) :" + util.round(util.sigmoid(compOutput + bias), 2));

			}

		}

		double outPut = util.sigmoid(compOutput + bias);
		//System.out.println();
		return outPut;
	}

//	good - for the input neurons if the input is integers.
	public double comput(double input) {
		double INPUT = input;
		input = (input * weights.get(0)) + bias;
		System.out.println("sig(" + INPUT + " * " + util.round(weights.get(0), 2) + " weight + " + util.round(bias, 2)
				+ " bias  ) : " + util.round(util.sigmoid(input), 2));
		return util.sigmoid(input);
	}

	/**
	 * @return the bias
	 */
	public double getBias() {
		return bias;
	}

	/**
	 * @param bias the bias to set
	 */
	public void setBias(double bias) {
		this.bias = bias;
	}

	/**
	 * @return the weights
	 */
	public ArrayList<Double> getWeights() {
		return weights;
	}

	/**
	 * @param weights the weights to set
	 */
	public void setWeights(ArrayList<Double> weights) {
		this.weights = weights;
	}

}
