package mutilayerPerceptron;

import java.util.*;

public class Util {
//  returns the sigmoid 1/(1-e^-x);
// used as a nonlinerrity to make the network work, converts input to 0-1 with high values aproching 1 and low numbers aproching 0
	public static double sigmoid(double in) {
		return 1 / (1 + Math.exp(-in));
	}

// good - returns the derivative of the sigmoid e^-x/(1-e^-x)^2
	public static double sigmoidDerivative(double x) {
		double sigmoidValue = sigmoid(x);
		return sigmoidValue * (1 - sigmoidValue);
	}

	// 1 = t(1+e^-x)
	// 1 = t +t(e^-x)
	// 1-t = t(e^-x)
	// 1/t -1 = e^-x
	// log(1/t -1) = -xlog(e )
	// log(1/t -1)/log(e) = -x
	// -1(log(1/t -1)/log(e)) = x
	// takes a sigmoid output and gives input.
	// used to get the input of the computations before the sigmoid was aplied for
	// the partal drivative
	public static double unSigmoid(double in) {

		double unSigmoid = (Math.log10((1 / in) - 1));
		unSigmoid = unSigmoid / Math.log10(Math.E);
		unSigmoid = (-1 * unSigmoid);
		return unSigmoid;
	}

//  gives the meanSquareLoss(obsoleat method)
	public static double meanSquareLoss(List<Double> correctAnswers, List<Double> predictedAnswers) {
		double sumSquare = 0;
		for (int i = 0; i < correctAnswers.size(); i++) {
			double error = correctAnswers.get(i) - predictedAnswers.get(i);
			sumSquare += (error * error);
		}
		return sumSquare / correctAnswers.size();
	}

	// good - rounds for clear display
	public static double round(double num, int place) {
		double roundedNumber = Math.round(num * Math.pow(10, place)) / Math.pow(10, place);
		return (roundedNumber);
	}

	/**
	 * 
	 * @param GVs precondition the vectors are the same size
	 * @return
	 */
	// adds the 3d arrayLists into one used to add the Gradentvectors
	public static ArrayList<ArrayList<ArrayList<Double>>> addGVWeights(
			ArrayList<ArrayList<ArrayList<ArrayList<Double>>>> GVs) {
		ArrayList<ArrayList<ArrayList<Double>>> total = new ArrayList<ArrayList<ArrayList<Double>>>();
		for (int q = 0; q < GVs.get(0).size(); q++) {
			total.add(new ArrayList<ArrayList<Double>>());
			for (int w = 0; w < GVs.get(0).get(q).size(); w++) {
				total.get(q).add(w, new ArrayList<Double>());
				for (int e = 0; e < GVs.get(0).get(q).get(w).size(); e++) {
					total.get(q).get(w).add(0.0);
				}
			}
		}
		for (int GVnum = 0; GVnum < GVs.size(); GVnum++) {
			for (int D3 = 0; D3 < GVs.get(GVnum).size(); D3++) {
				for (int D2 = 0; D2 < GVs.get(GVnum).get(D3).size(); D2++) {
					for (int D1 = 0; D1 < GVs.get(GVnum).get(D3).get(D2).size(); D1++) {
						total.get(D3).get(D2).set(D1,
								Util.round(total.get(D3).get(D2).get(D1) + GVs.get(GVnum).get(D3).get(D2).get(D1), 7));
					}
				}
			}
		}
		return total;
	}

//same as the weights
	public static ArrayList<ArrayList<Double>> addGVBias(ArrayList<ArrayList<ArrayList<Double>>> GVs) {
		ArrayList<ArrayList<Double>> total = new ArrayList<ArrayList<Double>>();
		for (int q = 0; q < GVs.get(0).size(); q++) {
			total.add(new ArrayList<Double>());
			for (int w = 0; w < GVs.get(0).get(q).size(); w++) {
				total.get(q).add(w, 0.0);

			}
		}
		for (int GVnum = 0; GVnum < GVs.size(); GVnum++) {
			for (int D2 = 0; D2 < GVs.get(GVnum).size(); D2++) {
				for (int D1 = 0; D1 < GVs.get(GVnum).get(D2).size(); D1++) {
					total.get(D2).set(D1,
							Util.round(total.get(D2).get(D1) + GVs.get(GVnum).get(D2).get(D1), 7) / GVs.size());
				}
			}
		}
		return total;
	}

}
