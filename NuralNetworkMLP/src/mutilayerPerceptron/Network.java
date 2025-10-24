package mutilayerPerceptron;

import java.util.*;

public class Network {
	private ArrayList<ArrayList<Neuron>> network = new ArrayList<ArrayList<Neuron>>();
	ArrayList<ArrayList<ArrayList<Double>>> GVWeights = new ArrayList<ArrayList<ArrayList<Double>>>();
	ArrayList<ArrayList<Double>> GVBias = new ArrayList<ArrayList<Double>>();

	/**
	 * @param layerNum
	 * @param outputNeuronNum
	 * @param inputNeuronNum
	 * @param hiddenNeuronNum
	 */
	// constructor
	public Network(int layerNum, int outputNeuronNum, int inputNeuronNum, int hiddenNeuronNum) {

		for (int Layers = 0; Layers < layerNum; Layers++) {
			network.add(new ArrayList<Neuron>());

			if (Layers == 0) {
				for (int numInputN = 0; numInputN < inputNeuronNum; numInputN++) {
					// initlizes the first layer of the network as input neurons - the second
					// pramiter is true to the network adds input nrueons(new Neuron(1, TRUE)
					network.get(0).add(new Neuron(1, true));
				}
			} else if (Layers == layerNum - 1) {
				for (int numOutputN = 0; numOutputN < outputNeuronNum; numOutputN++) {
					network.get(Layers).add(new Neuron(network.get(layerNum - 2).size(), false));
				}
			} else {
				for (int numHiddenN = 0; numHiddenN < hiddenNeuronNum; numHiddenN++) {
					network.get(Layers).add(new Neuron(network.get(Layers - 1).size(), false));
				}
			}
		}
		// iniltlized the gradent vector for weights and biases
		System.out.println("network:" + network);
		for (int q = 0; q < network.size(); q++) {
			GVWeights.add(q, new ArrayList<ArrayList<Double>>());
			GVBias.add(q, new ArrayList<Double>());
			for (int w = 0; w < network.get(q).size(); w++) {
				GVWeights.get(q).add(w, new ArrayList<Double>());
				GVBias.get(q).add(w, 0.0);
				for (int e = 0; e < network.get(q).get(w).getWeights().size(); e++) {
					GVWeights.get(q).get(w).add(0.0);
				}
			}
		}
	}

// getters and setters
	/**
	 * @return the gVBias
	 */
	public ArrayList<ArrayList<Double>> getGVBias() {
		return GVBias;
	}

	/**
	 * @param gVBias the gVBias to set
	 */
	public void setGVBias(ArrayList<ArrayList<Double>> gVBias) {
		GVBias = gVBias;
	}

	/**
	 * @return the gVWeights
	 */
	public ArrayList<ArrayList<ArrayList<Double>>> getGVWeights() {
		return GVWeights;
	}

	/**
	 * @param gVWeights the gVWeights to set
	 */
	public void setGVWeights(ArrayList<ArrayList<ArrayList<Double>>> gVWeights) {
		GVWeights = gVWeights;
	}

	// setting the GV enterys for the weights to zero
	public void resetGVWeights() {
		for (int q = 0; q < network.size(); q++) {
			for (int w = 0; w < network.get(q).size(); w++) {
				for (int e = 0; e < network.get(q).get(w).getWeights().size(); e++) {
					GVWeights.get(q).get(w).set(e, 0.0);
				}
			}
		}
	}

	// setting the GV enterys for the bias to zero
	public void resetGVBias() {
		for (int q = 0; q < network.size(); q++) {
			for (int w = 0; w < network.get(q).size(); w++) {
				GVBias.get(q).set(w, 0.0);
			}
		}
	}

//This method takes input as a ArrayList of doubles and propgates is through the network and returns the network output as a ArrayList of doubles
	public ArrayList<Double> feedForward(ArrayList<Double> data, boolean print) {
		ArrayList<ArrayList<Double>> computations = new ArrayList<ArrayList<Double>>();
		for (int q = 0; q < network.size(); q++) {
			computations.add(q, new ArrayList<Double>());
			for (int w = 0; w < network.get(q).size(); w++) {
				computations.get(q).add(w, 0.0);
			}
		}

		for (int NumLayer = 0; NumLayer < network.size(); NumLayer++) {
			if (print) {
				if (NumLayer == 0) {
					System.out.println("\n ----------- Input layer:" + NumLayer);
				} else if (NumLayer < (network.size() - 1)) {
					System.out.println("\n ----------- Hidden layer:" + NumLayer);
				} else {
					System.out.println("\n ----------- Output layer:" + NumLayer);
				}
			}
			for (int NeuronsInLayer = 0; NeuronsInLayer < network.get(NumLayer).size(); NeuronsInLayer++) {
				if (NumLayer == 0) {
					double data1 = data.get(NeuronsInLayer);
					double inputNeuronComp = network.get(0).get(NeuronsInLayer).comput(data1, print);
					computations.get(0).set(NeuronsInLayer, inputNeuronComp);
				} else {
					double data2 = network.get(NumLayer).get(NeuronsInLayer).comput(computations.get(NumLayer - 1),
							print);
					computations.get(NumLayer).set(NeuronsInLayer, data2);
				}
			}
			if (print) {
				System.out.print("   layer output ");
				for (Double val : computations.get(NumLayer)) {
					System.out.print("[" + Util.round(val, 6) + "],");
				}
				System.out.println();
			}
		}
		return computations.get(network.size() - 1);

	}

	// method to print the error of a example given the answer as arrayList and
	// output as arrayList
	// Precondition the output and answers are the same size
	public double errorOfExample(ArrayList<Double> output, ArrayList<Double> answers, boolean print) {
		double error = 0;
		for (int q = 0; q < network.get(network.size() - 1).size(); q++) {
			if (print) {
				System.out
						.println("	Output " + q + " :" + Util.round(output.get(q), 3) + " Answer:" + answers.get(q));
			}
			double errorPart = output.get(q) - answers.get(q);
			error += errorPart * errorPart;
		}
		if (print) {
			System.out.println("		Error:" + Util.round(error, 3) + "\n ----------- ");
		}
		return error;
	}

	// Backpropagation to calculate the entrys of the gradent vector for a spcific
	// example
	public ArrayList<ArrayList<ArrayList<Double>>> BackpropagationWeights(ArrayList<Double> data,
			ArrayList<Double> answer) {
		// part of the gradiant vector and other arrayLists
		ArrayList<ArrayList<ArrayList<Double>>> GVdrivWeights = new ArrayList<ArrayList<ArrayList<Double>>>();
		// computations holds the specific computations of the network for this example
		ArrayList<ArrayList<Double>> computations = new ArrayList<ArrayList<Double>>();

		// ArrayLists initialization
		for (int q = 0; q < network.size(); q++) {
			computations.add(q, new ArrayList<Double>());
			GVdrivWeights.add(q, new ArrayList<ArrayList<Double>>());
			for (int w = 0; w < network.get(q).size(); w++) {
				computations.get(q).add(w, 0.0);
				GVdrivWeights.get(q).add(w, new ArrayList<Double>());
				for (int e = 0; e < network.get(q).get(w).getWeights().size(); e++) {
					GVdrivWeights.get(q).get(w).add(0.0);
				}
			}

		}
		// initialization of computations
		for (int NumLayer = 0; NumLayer < network.size(); NumLayer++) {
			for (int NeuronsInLayer = 0; NeuronsInLayer < network.get(NumLayer).size(); NeuronsInLayer++) {
				if (NumLayer == 0) {
					double data1 = data.get(NeuronsInLayer);
					double inputNeuronComp = network.get(0).get(NeuronsInLayer).comput(data1, false);
					computations.get(0).set(NeuronsInLayer, inputNeuronComp);
				} else {
					double data2 = network.get(NumLayer).get(NeuronsInLayer).comput(computations.get(NumLayer - 1),
							false);
					computations.get(NumLayer).set(NeuronsInLayer, data2);
				}
			}
		}
// computation of partal drivatives of cost with respect to each weight 
		ArrayList<Double> chainDrivativeSum = new ArrayList<Double>();
		ArrayList<Double> chainDrivativeSumTemp = new ArrayList<Double>();
		for (int Layer = network.size() - 1; Layer > 0; Layer--) {
			for (int Neuron = 0; Neuron < network.get(Layer).size(); Neuron++) {
				if (Layer == network.size() - 1) {
					double costRaL = 2 * (computations.get(Layer).get(Neuron) - answer.get(Neuron));
					double aLRzL = Util.sigmoidDerivative(Util.unSigmoid(computations.get(Layer).get(Neuron)));
					chainDrivativeSum.add(costRaL * aLRzL);
				} else {
					double chain = 0;
					for (int chainLink = 0; chainLink < chainDrivativeSum.size(); chainLink++) {
						chain += chainDrivativeSum.get(chainLink)
								* network.get(Layer + 1).get(chainLink).getWeights().get(Neuron);
					}
					double aLRzL = Util.sigmoidDerivative(Util.unSigmoid(computations.get(Layer).get(Neuron)));
					chainDrivativeSumTemp.add(Neuron, aLRzL * chain);
				}
				for (int Weight = 0; Weight < network.get(Layer).get(Neuron).getWeights().size(); Weight++) {
					if (Layer == network.size() - 1) {
						double costRaL = 2 * (computations.get(Layer).get(Neuron) - answer.get(Neuron));
						double aLRzL = Util.sigmoidDerivative(Util.unSigmoid(computations.get(Layer).get(Neuron)));
						double zLRwL = computations.get(Layer - 1).get(Weight);
						GVdrivWeights.get(Layer).get(Neuron).set(Weight, costRaL * aLRzL * zLRwL);

					} else {
						double aLRzL = Util.sigmoidDerivative(Util.unSigmoid(computations.get(Layer).get(Neuron)));
						double zLRwL = computations.get(Layer - 1).get(Weight);
						double chain = 0;
						for (int chainLink = 0; chainLink < chainDrivativeSum.size(); chainLink++) {
							chain += chainDrivativeSum.get(chainLink)
									* network.get(Layer + 1).get(chainLink).getWeights().get(Neuron);
						}

						GVdrivWeights.get(Layer).get(Neuron).set(Weight, chain * aLRzL * zLRwL);
					}
				}

			}
			if (Layer < network.size() - 1) {
				chainDrivativeSum.clear();
				for (double valSwap : chainDrivativeSumTemp) {
					chainDrivativeSum.add(valSwap);
				}
				chainDrivativeSumTemp.clear();
			}
		}

		return GVdrivWeights;
	}

	// Backpropagation to calculate the entrys of the gradent vector for a spcific
	// example
	public ArrayList<ArrayList<Double>> BackpropagationBias(ArrayList<Double> data, ArrayList<Double> answer) {
		// part of the gradiant vector and other arrayLists
		ArrayList<ArrayList<Double>> GVdrivBias = new ArrayList<ArrayList<Double>>();
		ArrayList<ArrayList<Double>> computations = new ArrayList<ArrayList<Double>>();

		// ArrayLists initialization
		for (int q = 0; q < network.size(); q++) {
			GVdrivBias.add(q, new ArrayList<Double>());
			computations.add(q, new ArrayList<Double>());
			for (int w = 0; w < network.get(q).size(); w++) {
				computations.get(q).add(w, 0.0);
				GVdrivBias.get(q).add(w, 0.0);
			}

		}
		// initialization of computations
		for (int NumLayer = 0; NumLayer < network.size(); NumLayer++) {
			for (int NeuronsInLayer = 0; NeuronsInLayer < network.get(NumLayer).size(); NeuronsInLayer++) {
				if (NumLayer == 0) {
					double data1 = data.get(NeuronsInLayer);
					double inputNeuronComp = network.get(0).get(NeuronsInLayer).comput(data1, false);
					computations.get(0).set(NeuronsInLayer, inputNeuronComp);
				} else {
					double data2 = network.get(NumLayer).get(NeuronsInLayer).comput(computations.get(NumLayer - 1),
							false);
					computations.get(NumLayer).set(NeuronsInLayer, data2);
				}
			}
		}
// computation of partal drivatives of cost with respect to bias
		ArrayList<Double> chainDrivativeSum = new ArrayList<Double>();
		ArrayList<Double> chainDrivativeSumTemp = new ArrayList<Double>();
		for (int Layer = network.size() - 1; Layer > 0; Layer--) {
			for (int Neuron = 0; Neuron < network.get(Layer).size(); Neuron++) {
				if (Layer == network.size() - 1) {
					double costRaL = 2 * (computations.get(Layer).get(Neuron) - answer.get(Neuron));
					double aLRzL = Util.sigmoidDerivative(Util.unSigmoid(computations.get(Layer).get(Neuron)));
					chainDrivativeSum.add(costRaL * aLRzL);
				} else {
					double chain = 0;
					for (int chainLink = 0; chainLink < chainDrivativeSum.size(); chainLink++) {
						chain += chainDrivativeSum.get(chainLink)
								* network.get(Layer + 1).get(chainLink).getWeights().get(Neuron);
					}
					double aLRzL = Util.sigmoidDerivative(Util.unSigmoid(computations.get(Layer).get(Neuron)));
					chainDrivativeSumTemp.add(Neuron, aLRzL * chain);
				}

				if (Layer == network.size() - 1) {
					double costRaL = 2 * (computations.get(Layer).get(Neuron) - answer.get(Neuron));
					double aLRzL = Util.sigmoidDerivative(Util.unSigmoid(computations.get(Layer).get(Neuron)));
					GVdrivBias.get(Layer).set(Neuron, costRaL * aLRzL);

				} else {
					double aLRzL = Util.sigmoidDerivative(Util.unSigmoid(computations.get(Layer).get(Neuron)));
					double chain = 0;
					for (int chainLink = 0; chainLink < chainDrivativeSum.size(); chainLink++) {
						chain += chainDrivativeSum.get(chainLink)
								* network.get(Layer + 1).get(chainLink).getWeights().get(Neuron);
					}

					GVdrivBias.get(Layer).set(Neuron, chain * aLRzL);
				}

			}
			if (Layer < network.size() - 1) {
				chainDrivativeSum.clear();
				for (double valSwap : chainDrivativeSumTemp) {
					chainDrivativeSum.add(valSwap);
				}
				chainDrivativeSumTemp.clear();
			}
		}

		return GVdrivBias;
	}

//changes the network by subtracting the entrys of the gravent vector from the corasponing weight
//	and bias to "step down hill" to minimize the cost function

	public void updateWeights(double learningRate) {
		for (int Layer = network.size() - 1; Layer > 0; Layer--) {
			for (int Neuron = 0; Neuron < network.get(Layer).size(); Neuron++) {
				for (int Weight = 0; Weight < network.get(Layer).get(Neuron).getWeights().size(); Weight++) {
					double PD = GVWeights.get(Layer).get(Neuron).get(Weight);
					network.get(Layer).get(Neuron).setWeight(Weight,
							network.get(Layer).get(Neuron).getWeights().get(Weight) - (PD * learningRate));
				}
			}
		}
	}

	public void updateBias(double learningRate) {
		for (int Layer = network.size() - 1; Layer > 0; Layer--) {
			for (int Neuron = 0; Neuron < network.get(Layer).size(); Neuron++) {
				double PD = GVBias.get(Layer).get(Neuron);
				network.get(Layer).get(Neuron).setBias(network.get(Layer).get(Neuron).getBias() - (PD * learningRate));

			}
		}
	}

	// method to tran the network for epoch times useing the data
	public void train(int epochs, double learningRate, Data data) {
		ArrayList<ArrayList<ArrayList<ArrayList<Double>>>> GVWeightsAdd = new ArrayList<ArrayList<ArrayList<ArrayList<Double>>>>();
		ArrayList<ArrayList<ArrayList<Double>>> GVBiasAdd = new ArrayList<ArrayList<ArrayList<Double>>>();
		double error = 0;
		for (int epoch = 1; epoch <= epochs; epoch++) {
			System.out.println("*************");
			for (int subEpoch = 0; subEpoch < data.getAnswers().size(); subEpoch++) {
				System.out.println("subEpoch:" + subEpoch);
				GVWeightsAdd
						.add(BackpropagationWeights(data.getRawData().get(subEpoch), data.getAnswers().get(subEpoch)));
				GVWeightsAdd.add(GVWeights);
				GVBiasAdd.add(BackpropagationBias(data.getRawData().get(subEpoch), data.getAnswers().get(subEpoch)));
				GVBiasAdd.add(GVBias);
				GVWeights = Util.addGVWeights(GVWeightsAdd);
				GVBias = Util.addGVBias(GVBiasAdd);
				error += errorOfExample(feedForward(data.getRawData().get(subEpoch), false),
						data.getAnswers().get(subEpoch), false);
				GVWeightsAdd.clear();
				GVBiasAdd.clear();
			}
			updateWeights(learningRate);
			updateBias(learningRate);
			updateWeights(learningRate);
			updateBias(learningRate);
			resetGVWeights();
			resetGVBias();

			System.out.println("Epoch:" + epoch);
			System.out.println("Average Error:" + Util.round((error / data.getAnswers().size()), 3));
			error = 0;
		}
	}

}
