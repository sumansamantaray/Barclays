/**
 * 
 */
package com.barclays.main;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * @author SUSHREE
 *
 */
public class TheatreSeatArrngmnt {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Read the input from command line
		Scanner scanner = new Scanner(System.in);
		try {
			List<String> inputList = new ArrayList<String>();
	        while (scanner.hasNextLine()) {
	        	String inputVal = scanner.nextLine();
	        	// Read the input until "Compute" is encountered
	        	if ("compute".equalsIgnoreCase(inputVal)) {
	        		break;
	        	} else {
	        		inputList.add(inputVal);
	        	}
	        	
	        }
	        computeTheatreSeating(inputList);
		} catch (Exception exp) {
			
		} finally {
			scanner.close();
		}
	}

	/**
	 * @param inputList - Complete list of command line input data
	 */
	private static void computeTheatreSeating(List<String> inputList) {
		List<String> seatMapping = new ArrayList<String>();
		List<String> customerMapping = new ArrayList<String>();
		
		// Segregate the seat layout and Customer entries
		int indexOfSeparator = inputList.indexOf("");
		seatMapping = inputList.subList(0, indexOfSeparator); // Holds the seat layout
		customerMapping = inputList.subList(indexOfSeparator+1, inputList.size()); // Holds the customer request
		
		Map<String, List<Integer>> seatingMap = new LinkedHashMap<String, List<Integer>>(); // Map to hold the seat layout
		
		// Put the seat layout in a LinkedHashMap for easy computation and maintaining the order
		for (int counter = 0 ; counter < seatMapping.size() ; counter++) {
			String[] seatArray = seatMapping.get(counter).split(" ");
			List<Integer> seatCountList = new ArrayList<Integer>();
			for (String seatCount : seatArray) {
				seatCountList.add(Integer.valueOf(seatCount));
			}
			seatingMap.put("Row "+(counter+1), seatCountList);
		}
		
		Map<String, String> customerToSeatMap = new LinkedHashMap<String, String>(); // Map to hold the final customer to seat mapping
		for (String value : customerMapping) {
			String[] customerSeatCount = value.split(" ");
			String customerName = customerSeatCount[0];
			int seatCount = Integer.valueOf(customerSeatCount[1]);
			for (String keySet : seatingMap.keySet()) {
				List<Integer> NumOfSeatList = seatingMap.get(keySet);
				for (int seatCounter = 0 ; seatCounter < seatingMap.get(keySet).size() ; seatCounter++) {
					if (seatCount <= NumOfSeatList.get(seatCounter) ) {
						customerToSeatMap.put(customerName, keySet+" Section "+(seatCounter+1));
//						seatingMap.remove(keySet);
						NumOfSeatList.set(seatCounter, NumOfSeatList.get(seatCounter) - seatCount);
						break;
					}
				}
				if (customerToSeatMap.get(customerName) != null) {
					break;
				}
			}
			if (customerToSeatMap.get(customerName) == null) {
				int totalSeatInRow = 0;
				for (String keySet : seatingMap.keySet()) {
					List<Integer> NumOfSeatList = seatingMap.get(keySet);
					for (int seats : NumOfSeatList) {
						totalSeatInRow = totalSeatInRow + seats;
					}
					if (seatCount <= totalSeatInRow) {
						customerToSeatMap.put(customerName, "Call to split party.");
						seatingMap.remove(keySet);
						break;
					} 
				}
			}
			if (customerToSeatMap.get(customerName) == null) {
				customerToSeatMap.put(customerName, "Sorry, we can't handle your party.");
			}
		}
		for (String customerSeatMap : customerToSeatMap.keySet()) {
			System.out.println(customerSeatMap+" "+customerToSeatMap.get(customerSeatMap));
		}
		
	}

}
