import java.util.*;

public class NetMaker {
	//outdated
//	good - needs to have a data gathering method/class
	public static void main(String[] args) {
		ArrayList<Double> data = new ArrayList<Double>();
		data.add(.22);
		data.add(1.32);
		data.add(0.52);
		data.add(1.4);
		ArrayList<Integer> answers = new ArrayList<Integer>();
		answers.add(1);
		answers.add(1);
		answers.add(1);
		answers.add(1);
//	good - creates a network of dynamic size, but the hidden layers are all one size
		ArrayList<ArrayList<Neuron>> TestNet = new ArrayList<ArrayList<Neuron>>();
		int maxLayers = 3;
		int netWorkOutputs = 1;
		int netWorkInput = 2;
		int netWorkHiddenNeurons = 2;
		int neuronNum;

		for (int q = 0; q <= maxLayers - 1; q++) {
			TestNet.add(q, new ArrayList<Neuron>());
			if (q == 0) {
				neuronNum = netWorkInput;
			} else if (q < maxLayers - 1) {
				neuronNum = netWorkHiddenNeurons;
			} else {
				neuronNum = netWorkOutputs;
			}
			for (int w = 0; w < neuronNum; w++) {
				if (w < data.size() && q == 0) {
					System.out.println("input neuron: " + w + " layer:" + q);
					TestNet.get(q).add(w, new Neuron(1));
				} else if (q < maxLayers - 1) {
					System.out.println(" hidden Neuron: " + w + " layer:" + (q + 1));
					TestNet.get(q).add(w, new Neuron(TestNet.get(q - 1).size()));
				}
				if (q == maxLayers - 1 && w < netWorkOutputs) {
					System.out.println("output Neuron:" + w + " layer:" + q);
					TestNet.get(q).add(w, new Neuron(TestNet.get(q - 1).size()));
				}
			}
		}
		System.out.println();
		System.out.println("-----initlization-----");
		System.out.println();
		// feedForward(data, answers, TestNet);
		for (int epoch = 0; epoch < 10000; epoch++) {
			Backpropagation(data, answers, TestNet, 2);
			System.out.println("epoch :" + epoch);
		}
	}

//	good - computes the cost of a example
	public static double feedForward(ArrayList<Double> data, ArrayList<Integer> answers,
			ArrayList<ArrayList<Neuron>> net) {
		ArrayList<ArrayList<Double>> computations = new ArrayList<ArrayList<Double>>();
		for (int q = 0; q < net.size(); q++) {
			computations.add(q, new ArrayList<Double>());
			for (int w = 0; w < net.get(q).size(); w++) {
				computations.get(q).add(w, 0.0);
			}
		}
		double error = 0;
		// start of method
		for (int NumLayer = 0; NumLayer < net.size(); NumLayer++) {
			if (NumLayer == 0) {
				System.out.println("\n ----------- Input layer:" + NumLayer);
			} else if (NumLayer < (net.size() - 1)) {
				System.out.println("\n ----------- Hidden layer:" + NumLayer);
			} else {
				System.out.println("\n ----------- Output layer:" + NumLayer);
			}
			for (int NeuronsInLayer = 0; NeuronsInLayer < net.get(NumLayer).size(); NeuronsInLayer++) {
				if (NumLayer == 0) {
					double data1 = data.get(NeuronsInLayer);
					double inputNeuronComp = net.get(0).get(NeuronsInLayer).comput(data1);
					computations.get(0).set(NeuronsInLayer, inputNeuronComp);
				} else {
					double data2 = net.get(NumLayer).get(NeuronsInLayer).comput(computations.get(NumLayer - 1));
					computations.get(NumLayer).set(NeuronsInLayer, data2);
				}
			}
			System.out.println("   layer output " + computations.subList(NumLayer, NumLayer + 1));
		}
		System.out.println("the final output " + computations.get(net.size() - 1));
		for (int q = 0; q < net.get(net.size() - 1).size(); q++) {
			double errorPart = computations.get(net.size() - 1).get(q) - answers.get(q);
			error += errorPart * errorPart;
		}
		return (error);
	}

	// unfinished
	public static void Backpropagation(ArrayList<Double> data, ArrayList<Integer> answers,
			ArrayList<ArrayList<Neuron>> net, double learningRate) {
		// the 2 parts of the gradiant vector and other arrayLists
		ArrayList<ArrayList<Double>> drivBias = new ArrayList<ArrayList<Double>>();
		ArrayList<ArrayList<ArrayList<Double>>> GVdrivWeights = new ArrayList<ArrayList<ArrayList<Double>>>();
		ArrayList<ArrayList<ArrayList<Double>>> nodePartialdDerivative = new ArrayList<ArrayList<ArrayList<Double>>>();
		// computations holds the specific computations of the net work
		ArrayList<ArrayList<Double>> computations = new ArrayList<ArrayList<Double>>();
		double totalError = feedForward(data, answers, net);
		
		//ArrayList initialization
		
		for (int q = 0; q < net.size(); q++) {
			drivBias.add(q, new ArrayList<Double>());
			computations.add(q, new ArrayList<Double>());
			GVdrivWeights.add(q, new ArrayList<ArrayList<Double>>());
			nodePartialdDerivative.add(q, new ArrayList<ArrayList<Double>>());
			for (int w = 0; w < net.get(q).size(); w++) {
				computations.get(q).add(w, 0.0);
				drivBias.get(q).add(w, 0.0);
				GVdrivWeights.get(q).add(w, new ArrayList<Double>());
				nodePartialdDerivative.get(q).add(w, new ArrayList<Double>());
				for (int e = 0; e < net.size(); e++) {
					GVdrivWeights.get(q).get(w).add(null);
					nodePartialdDerivative.get(q).get(w).add(null);
				}
			}

		}
		//initialization of computations
		for (int NumLayer = 0; NumLayer < net.size(); NumLayer++) {
			for (int NeuronsInLayer = 0; NeuronsInLayer < net.get(NumLayer).size(); NeuronsInLayer++) {
				if (NumLayer == 0) {
					double data1 = data.get(NeuronsInLayer);
					double inputNeuronComp = net.get(0).get(NeuronsInLayer).comput(data1);
					computations.get(0).set(NeuronsInLayer, inputNeuronComp);
				} else {
					double data2 = net.get(NumLayer).get(NeuronsInLayer).comput(computations.get(NumLayer - 1));
					computations.get(NumLayer).set(NeuronsInLayer, data2);
				}
			}
		}
		double nodeSigmoidDeriv = 0;
		double relevantWeight = 0;
		double directcost = 0;
		//initialization of nodePartialdDerivative
		for (int Layer = net.size() - 1; 0 < Layer; Layer--) {

			for (int Neuron = 0; Neuron < net.get(Layer).size(); Neuron++) {

				if (Layer == net.size() - 1) {
					nodeSigmoidDeriv = util.sigmoidDeriv(util.unSigmoid(computations.get(Layer).get(Neuron)));
					directcost = 2 * (computations.get(net.size() - 1).get(Neuron) - answers.get(Neuron));
					for (int weights = 0; weights < net.get(Layer).get(Neuron).weights.size(); weights++) {
						relevantWeight = net.get(Layer).get(Neuron).weights.get(weights);

						nodePartialdDerivative.get(Layer).get(Neuron).add(weights, nodeSigmoidDeriv * relevantWeight * directcost);
						 System.out.println("Layer :"+Layer+" Neuron :"+Neuron+" Weight:"+weights+" \n Node :"+ util.round(nodeSigmoidDeriv * relevantWeight * directcost,2));
					}

				} else {
					nodeSigmoidDeriv = util.sigmoidDeriv(util.unSigmoid(computations.get(Layer).get(Neuron)));
					for (int weights = 0; weights < net.get(Layer).get(Neuron).weights.size(); weights++) {
						relevantWeight = net.get(Layer).get(Neuron).weights.get(weights);

						nodePartialdDerivative.get(Layer).get(Neuron).add(weights, nodeSigmoidDeriv * relevantWeight );
						 System.out.println("Layer :"+Layer+" Neuron :"+Neuron+" Weight:"+weights+" Node :"+util.round(nodeSigmoidDeriv*relevantWeight,2));
					}

				}

			}
		}

		double partdriv = 1;

		// weights
		
		for (int numLayer = net.size() - 1; numLayer >= 0; numLayer--) {

			// System.out.println("| Layer #" + numLayer + " |");
			for (int numNeuron = 0; numNeuron < net.get(numLayer).size(); numNeuron++) {
				// System.out.println("Neuron (" + numLayer + "," + numNeuron + ")");

				for (int numWeight = 0; numWeight < net.get(numLayer).get(numNeuron).weights.size(); numWeight++) { 

					if (numLayer > 0) {
						partdriv = computations.get(numLayer - 1).get(numWeight);

					} else {
						partdriv = data.get(numNeuron);

					}

					partdriv = partdriv * util.sigmoidDeriv(util.unSigmoid(computations.get(numLayer).get(numNeuron)));

					if (numLayer == net.size() - 1) {
						partdriv = partdriv * 2 * (computations.get(numLayer).get(numNeuron) - answers.get(numNeuron));
					}

					int paths = util.pathCount(net, numLayer, numNeuron);
					double summation = 0;
					ArrayList<Integer> code = new ArrayList<Integer>();
					if (numLayer == net.size() - 1) {
						GVdrivWeights.get(numLayer).get(numNeuron).set(numWeight, partdriv);
					} else {
						double totalPathVal = 0;
						for (int path = 0; path < paths; path++) {
							// System.out.println("PATHS # " + (t + 1) + "/" + paths);
							code.clear();
							code.addAll(util.pathFinder(path, numLayer, numNeuron, net));
							for (int x = 1; x < code.size(); x++) {
								totalPathVal += nodePartialdDerivative.get(x + numLayer).get(code.get(x)).get(code.get(x - 1));
								
							}
							 System.out.println(" Path Code " + code.subList(0, code.size()) + "\n the weight value is now :"+totalPathVal );
							summation = summation + totalPathVal;
							totalPathVal = 0;

						}
						partdriv = partdriv * summation;
						GVdrivWeights.get(numLayer).get(numNeuron).set(numWeight, partdriv);

					}

					// System.out.println(" Weight(" + numLayer + "," + numNeuron + "," + numWeight
					// + ") = " + partdriv);
					partdriv = 1;

				}

			}

		}
		int NANCounter = 0;
		for (int Layer = 0; Layer < net.size(); Layer++) {
			for (int Neuron = 0; Neuron < net.get(Layer).size(); Neuron++) {
				for (int Weight = 0; Weight < net.get(Layer).get(Neuron).weights.size(); Weight++) {
					double Qone = net.get(Layer).get(Neuron).weights.get(Weight);
					double Qtwo = GVdrivWeights.get(Layer).get(Neuron).get(Weight);
					boolean NAN = GVdrivWeights.get(Layer).get(Neuron).get(Weight).isNaN();
					
					if (NAN) {
						System.out.println("NAN location (" + Layer + "," + Neuron + "," + Weight + ")");
						Qtwo = (Math.random() * 101) - 50;
						NANCounter++;
					}
					if(!(Layer==net.size()-1)){
					}
					System.out.println("Qtwo("+Layer+","+Neuron+","+Weight+") :" + Qtwo);
					net.get(Layer).get(Neuron).weights.set(Weight, Qone - Qtwo   );
				}
			}
		}
		double newError = feedForward(data, answers, net);
		System.out.println("	 Old Error :" + totalError + " New Error : " + newError+"\n NAN counter:"+NANCounter);

	}

}
