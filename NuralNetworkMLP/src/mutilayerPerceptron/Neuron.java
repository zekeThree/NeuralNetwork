package mutilayerPerceptron;

import java.util.*;

//neuron class acts as the basic unit of the network 
//the input neurons only take one data entry and apaly pass it through a the nonlinearity then feeds it into the next layer
//regular neurons take a arrayList of doubles and multiply it by the weight associated, then add some bias and then take that number and pump it through a nonlinearity
//like the sigmoid in this case- then feed its output to the next layer of neurons
public class Neuron {
	private double bias;
	private ArrayList<Double> weights = new ArrayList<Double>();
	private boolean isInputNeuron = false;
//constructor initlizes the weights and bias between (-2,2)
	public Neuron(int weightsNum, boolean isInputNeuron) {
		this.isInputNeuron = isInputNeuron;
		if (!isInputNeuron) {
			bias = -2 + (double) (Math.random() * (4));
			for (int q = 0; q < weightsNum; q++) {
				weights.add(q, -2 + (double) (Math.random() * (4)));
			}
			System.out.println(
					" weights:" + weights.subList(0, weights.size()) + " and the bias :" + Util.round(bias, 2));
		}
		System.out.println("input Neuron(just) sigmoid ");
	}

// computes the output of the neuron based on the output of previous neurons. 
	public double comput(ArrayList<Double> input, boolean print) {
		if (!isInputNeuron) {
			double compOutput = 0;

			for (int q = 0; q < input.size(); q++) {
				if (print) {
					System.out.print(" input #" + q + " = " + Util.round(input.get(q), 2));

					System.out.println("  compOutput += input:" + Util.round(input.get(q), 2) + " * weight:"
							+ Util.round(weights.get(q), 2));
				}
				compOutput += (weights.get(q) * input.get(q));
				if (q == (input.size() - 1)) {
					if (print) {
						System.out.println("sig ( compOutput:" + Util.round(compOutput, 2) + " + bias:"
								+ Util.round(bias, 2) + " ) :" + Util.round(Util.sigmoid(compOutput + bias), 2));
					}

				}

			}

			double outPut = Util.sigmoid(compOutput + bias);
			return outPut;
		} else {
			System.out.println("Error each input should be one value");
			return 0;
		}
	}

// computs the output if only one input- for the input neurons
	public double comput(double input, boolean print) {
		if (!isInputNeuron) {
			double INPUT = input;
			input = (input * weights.get(0)) + bias;
			if (print) {
				System.out.println("sig(" + INPUT + " * " + Util.round(weights.get(0), 2) + " weight + "
						+ Util.round(bias, 2) + " bias  ) : " + Util.round(Util.sigmoid(input), 2));
			}
			return Util.sigmoid(input);
		} else {
			if (print) {
				System.out.println("sig(" + Util.round(input, 2) + ") : " + Util.round(Util.sigmoid(input), 2));
			}
			return Util.sigmoid(input);
		}
	}

	@Override
	public String toString() {
		String info = "O";
//		for (int i = 0; i < weights.size(); i++) {
//			info = info +" Weight["+i+"] "+ Util.round(weights.get(i), 2);
//		}
		return info;
	}
	//getters and setters

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

	public void setWeight(int weight, double val) {
		weights.set(weight, val);
	}

}
