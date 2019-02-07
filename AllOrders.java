// maintains a map of Order objects as a TreeMap

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class AllOrders {
	
	// Storage for a certain amount of orders
	private static TreeMap<Integer, Order> allOrders;
	
	// 
	public AllOrders() {
		allOrders = new TreeMap<Integer, Order>();
	}
	
	public static void main (String arg[]) {
		loadOrders();
		System.out.println("Loaded");
//		System.out.println(findOrderId(6));
//		
//		Order o1 = findOrderId(6);
//		System.out.println(o1.getCustomerId());
//		ArrayList<Integer> list;
//		list = findCustomerOrders(o1.getCustomerId());
//		System.out.println(list);
//		
//		Order o2 = findOrderId(9);
//		System.out.println(o1.compareTo(o2)); //  o1.compareTo(o2) -1 o1 < o2 0 o1 = o2 1 o1 > o2
//		
		
		Order o3 = findOrderId(10);
		System.out.println(o3.toString());
		
	}
	
	/**
	 * loads a text file of orders
	 */
	public static void loadOrders() {
    	//initialise empty treemap of orders
        TreeMap<Integer, Order> entries = new TreeMap<Integer, Order>();
        BufferedReader buff = null;
    	String data [] = new String[4];
    	try {
			buff = new BufferedReader(new FileReader("orders.txt"));
			String inputLine = null;

			inputLine = buff.readLine();
			//read first line
	    	while(inputLine != null){  
	    		//split line into parts
	    		data  = inputLine.split(",");
	    		
	    		//create Order object (orderId, customerId, itemId, timestamp)
	    		Integer orderId = Integer.parseInt(data[0].trim());
	    		Integer customerId = Integer.parseInt(data[1].trim());
	    		String itemId = data[2].trim();
	    		Timestamp timestamp = Timestamp.valueOf(data[3].trim());
//	    		System.out.println(Integer.parseInt(data[0].trim()));
//	    		System.out.println(data[1].trim());
//	    		System.out.println(data[2].trim());
//	    		System.out.println(data[3].trim());
	    		Order o = new Order(orderId, customerId, itemId, timestamp); //Integer String Integer timestamp
	    		//add to treemap
	            entries.put(orderId, o);
	            //read next line
	            inputLine = buff.readLine();	            
	    	}
            
	    }
	    catch(FileNotFoundException e) {
	    	System.out.println(e.getMessage());
	        System.exit(1);
	    }
	    catch (IOException e) {
	    	e.printStackTrace();
	        System.exit(1);        	
	    }
	    finally  {
	    	try{
	    		buff.close();
	    	}
	    	catch (IOException ioe) {
	    		//don't do anything
	    	}
	    }
    	allOrders = entries;
	}
	
	/**
	 * adds an order (if more than one order, divides in a certain number of orders)
	 */
	public static void addOrder(int nb_orders, String data[]) {
		int i = 1;
		while(i < nb_orders+1) {
			for (int j = 0; j < nb_orders*4; j+=4) {
				//Order object (orderId, customerId, itemId, timestamp)
	    		Integer orderId = Integer.parseInt(data[j].trim());
	    		Integer customerId = Integer.parseInt(data[j+1].trim());
	    		String itemId = data[j+2].trim();
	    		Timestamp timestamp = Timestamp.valueOf(data[j+3]);
	    		try(FileWriter fw = new FileWriter("orders.txt", true);
	    			    BufferedWriter bw = new BufferedWriter(fw);
	    			    PrintWriter out = new PrintWriter(bw))
	    			{
	    			    out.println(orderId.toString() + "," + customerId.toString() + "," + itemId.toString() + "," + timestamp.toString());
	    			} catch (IOException e) {
	    			    //exception handling
	    			}
	    		loadOrders();
			}
		}
		
	}
	
	/**
	 * checks if the customer is already created
	 */
	public void checkCustomer(Integer orderId) {
		
	}
	
	/**
	 * deletes an order 
	 * if the customer ordered only one item, then it deletes also the customer
	 * if the customer ordered several items, it will delete only this order
	 */
	public void deleteOrder(Integer orderId) {
		Order o = findOrderId(orderId);
		ArrayList<Integer> list;
		list = findCustomerOrders(o.getCustomerId());
		System.out.println(list);
		if(list.size() == 1)
			//remove Customer
			System.out.println("We remove the customer: there is only one order");
		allOrders.remove(orderId);
		
	}
	
	/**
	 * finds an order when we are given an Id
	 * @return 
	 */
	public static Order findOrderId(Integer orderId) {
		return allOrders.get(orderId);
	}
	
	/**
	 * finds all the orders of one customer when we are given a customerId
	 */
	public static ArrayList<Integer> findCustomerOrders(Integer customerId) {
		ArrayList<Integer> orderIds = new ArrayList<Integer>();
		Set<Entry<Integer, Order>> mapset = allOrders.entrySet();
		for(Entry<Integer, Order> ent: mapset){
			if(ent.getValue().getCustomerId() == customerId) {
            	orderIds.add(ent.getKey());
			}
        }
		return orderIds;	   
	}
	
//	public void listByOrderId() {
//		
//	}
	
	public void listByTimestamp() {
		
	}
	
	public void listByCustomerId() {
	}
	
	public void listByItemId() {
	}
	
}
