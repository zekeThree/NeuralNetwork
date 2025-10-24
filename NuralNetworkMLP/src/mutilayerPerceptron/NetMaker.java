package mutilayerPerceptron;

import java.util.*;
//main class
public class NetMaker {
	public static void main(String[] args) {
		//to hold testing data
		Data dataSet1 = new Data();
		ArrayList<ArrayList<Double>> rawData1 = new ArrayList<ArrayList<Double>>();
		ArrayList<ArrayList<Double>> answers1 = new ArrayList<ArrayList<Double>>();
		ArrayList<Double> data11 = new ArrayList<Double>(Arrays.asList(0.0, 0.0));
		ArrayList<Double> answer11 = new ArrayList<Double>(Arrays.asList(0.0));
		ArrayList<Double> data12 = new ArrayList<Double>(Arrays.asList(1.0, 0.0));
		ArrayList<Double> answer12 = new ArrayList<Double>(Arrays.asList(1.0));
		ArrayList<Double> data13 = new ArrayList<Double>(Arrays.asList(1.0, 1.0));
		ArrayList<Double> answer13 = new ArrayList<Double>(Arrays.asList(0.0));
		ArrayList<Double> data14 = new ArrayList<Double>(Arrays.asList(0.0, 1.0));
		ArrayList<Double> answer14 = new ArrayList<Double>(Arrays.asList(1.0));
		rawData1.add(data11);
		answers1.add(answer11);
		rawData1.add(data12);
		answers1.add(answer12);
		rawData1.add(data13);
		answers1.add(answer13);
		rawData1.add(data14);
		answers1.add(answer14);
		dataSet1.addData(rawData1, answers1);
		
		Data dataSet2 = new Data();
		ArrayList<ArrayList<Double>> rawData2 = new ArrayList<ArrayList<Double>>();
		ArrayList<ArrayList<Double>> answers2 = new ArrayList<ArrayList<Double>>();
		ArrayList<Double> data21 = new ArrayList<Double>(Arrays.asList(0.0, 0.0));
		ArrayList<Double> answer21 = new ArrayList<Double>(Arrays.asList(0.0));
		ArrayList<Double> data22 = new ArrayList<Double>(Arrays.asList(1.0, 0.0));
		ArrayList<Double> answer22 = new ArrayList<Double>(Arrays.asList(0.0));
		ArrayList<Double> data23 = new ArrayList<Double>(Arrays.asList(1.0, 1.0));
		ArrayList<Double> answer23 = new ArrayList<Double>(Arrays.asList(1.0));
		ArrayList<Double> data24 = new ArrayList<Double>(Arrays.asList(0.0, 1.0));
		ArrayList<Double> answer24 = new ArrayList<Double>(Arrays.asList(0.0));
		rawData2.add(data21);
		answers2.add(answer21);
		rawData2.add(data22);
		answers2.add(answer22);
		rawData2.add(data23);
		answers2.add(answer23);
		rawData2.add(data24);
		answers2.add(answer24);
		dataSet2.addData(rawData2, answers2);
		
		Network testNet1 = new Network(5, 1, 2, 10);
		testNet1.train(6000, .1, dataSet1);
		
		testNet1.errorOfExample(testNet1.feedForward(data11, false), answer11, true);
		testNet1.errorOfExample(testNet1.feedForward(data12, false), answer12, true);
		testNet1.errorOfExample(testNet1.feedForward(data13, false), answer13, true);
		testNet1.errorOfExample(testNet1.feedForward(data14, false), answer14, true);
		
		
//		Network testNet2 = new Network(4, 1, 2, 5);
//		testNet2.train(800, 1, dataSet2);
//		
//		testNet2.errorOfExample(testNet2.feedForward(data21, false), answer21, true);
//		testNet2.errorOfExample(testNet2.feedForward(data22, false), answer22, true);
//		testNet2.errorOfExample(testNet2.feedForward(data23, false), answer23, true);
//		testNet2.errorOfExample(testNet2.feedForward(data24, false), answer24, true);
		
	}

}
