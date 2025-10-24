package mutilayerPerceptron;

import java.util.ArrayList;
//rudmentary class to hold training data 
//should be updated to read from files
public class Data {
	private ArrayList<ArrayList<Double>> rawData;
	private ArrayList<ArrayList<Double>> answers;

	public Data() {
		rawData = new ArrayList<ArrayList<Double>>();
		answers = new ArrayList<ArrayList<Double>>();
	}

	/*
	 * precondition newRawData.size() = newAnswers.size()
	 */
	public void addData(ArrayList<ArrayList<Double>> newRawData, ArrayList<ArrayList<Double>> newAnswers) {
		for (int index = 0; index < newRawData.size(); index++) {
			rawData.add(newRawData.get(index));
			answers.add(newAnswers.get(index));
		}
	}

	/**
	 * @return the rawData
	 */
	public ArrayList<ArrayList<Double>> getRawData() {
		return rawData;
	}

	/**
	 * @return the answers
	 */
	public ArrayList<ArrayList<Double>> getAnswers() {
		return answers;
	}

}
