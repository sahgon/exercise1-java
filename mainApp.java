
/*
 * Konstantinos Ioannis Kornarakis, 2nd semester, p3170074
 * Anastasios Kastanis, 2nd semester, p3170062
 */

import java.util.*;
import java.io.*;

public class mainApp {

	private static final String FILENAME = "C:\\Users\\Sagnon\\Desktop\\test.txt";

	// This method checks if a specific product exists on stock's catalog
	public static boolean checkAvailability(ArrayList<Product> stock, Product product) {
		for (Product p : stock) {
			if (p.equals(product)) {
				return true;
			}
		}

		return false;
	}

	// This method makes a new sale if parameter saleOrOrder equals '1', else it
	// makes a new order
	public static void makeSaleOrOrder(int saleOrOrder, ArrayList<Sale> sales, ArrayList<Order> orders, String date,
			String ETA, Product p) {
		Scanner input = new Scanner(System.in);
		System.out.println("Enter your name: ");
		System.out.print("> ");
		String name = input.nextLine();
		System.out.println("Enter your telephone number: ");
		System.out.print("> ");
		String telNumb = input.nextLine();
		if (saleOrOrder == 1) {
			Sale sale = new Sale(p, name, telNumb, date, p.getPrice() - p.getPrice() * detectDiscount(p));
			System.out.println("Purchase completed.");
			sales.add(sale);
		} else {
			Order order = new Order(p, name, telNumb, date, ETA, p.getPrice() - p.getPrice() * detectDiscount(p),
					"Pending");
			orders.add(order);
			System.out.println("Order completed.");
		}
	}

	public static boolean isBracketsMatch(String str) {

		Stack<Character> stack = new Stack<Character>();
		char c;
		for (int i = 0; i < str.length(); i++) {
			c = str.charAt(i);

			if (c == '{')
				stack.push(c);
			else if (c == '}')
				if (stack.empty())
					return false;
				else if (stack.peek() == '{')
					stack.pop();
				else
					return false;
		}
		return stack.empty();
	}
	
	public static double detectDiscount(Product p) {
		if (p instanceof Mobo || p instanceof CPU || p instanceof GPU || p instanceof RAM || p instanceof Disk) {
			return 0.25;
		} else if (p instanceof Monitor || p instanceof Keyboard || p instanceof Mouse || p instanceof Printer) {
			return 0.1;
		}
		return 0;
	}
	
	public static void creatingObj(String[][] tags, String line) {
		for (int i = 0; i < tags.length - 20; i++) {
			//System.out.println("Line is: " + line);
			//System.out.println("tags[i][0] = " + tags[i][0]);
			//System.out.println(tags[i][0].equals(line.contains(tags[i][0])));
			if (line.contains(tags[i][0])) {
				String s = line;
				//System.out.println("Line is: " + s);
				s = s.replace(tags[i][0], "");
				s = s.replaceAll(" ", "");
				s = s.replaceAll("\r", "");
				s = s.replaceAll("\n", "");
				s = s.replaceAll("\t", "");
				s = s.replace("{", "");
				s = s.replace("}", "");
				//line = s;
				tags[i][1] = s;
				System.out.println("New TAG: " + "." + tags[i][1] +".");
				break;
			}
		}
		//System.out.println("Method terminated");

	}

	public static void main(String[] arguements) {

		System.out.println("Starting...");

		final String[][] TAG = {{ "MODEL_TYPE", "" },                                      // Position 0 
				{ "MODEL", "" }, { "YEAR", "" }, { "MANUFACTURER", "" }, { "PRICE", "" }, { "STOCK", "" }, // Position 1-5
				{ "CPU_TYPE", "" }, { "MOBO_MEMORY", "" }, { "MOBO_PORTS", "" },           // Position 6-8
				{ "CPU_GHZ", "" }, { "CPU_CORES", "" }, { "CPU_OBG", "" },                 // Position 9-11
				{ "GPU_CHIPSET", "" }, { "GPU_MEMORY", "" },                               // Position 12-13
				{ "RAM_CAPACITY", "" }, { "RAM_TYPE", "" }, { "RAM_FREQUENCY", "" },       // Position 14-16
				{ "DISK_CAPACITY", "" }, { "DISK_DIMENSIONS", "" }, { "DISK_TYPE", "" },   // Position 17-19
				{ "MONITOR_TYPE", "" }, { "MONITOR_DIMENSIONS", "" }, { "MONITOR_PORTS", "" }, { "MONITOR_RESOLUTION", "" }, // Position 20-23
				{ "KEYBOARD_CONNECTION", "" },                                             // Position 24
				{ "MOUSE_TECH", "" }, { "MOUSE_CONNECTION", "" },                          // Position 25-26
				{ "PRINTER_TECH", "" }, { "PRINTER_TYPE", "" },                            // Position 27-28
				{ "ITEM", "" }, { "ITEM_LIST", "" } };                                     // Position 29-30

		BufferedReader br = null;
		FileReader file = null;
		
		try {
			file = new FileReader(FILENAME);
			br = new BufferedReader(file);
			String line = "";
			int r;
			boolean searchMainBr = false, found = false, searchItemBr = false, found2 = false;
			while ( (line = br.readLine()) != null) {
				line = line.replaceAll(" ", "");
				line = line.replaceAll("\r", "");
				line = line.replace("\n", "");
				line = line.replaceAll("\t", "");
				//System.out.println("Line: " + line);
				if (searchMainBr && line.contains("{")) {
					//System.out.println("1st if (item_list)");
	            	found = true;
	           		searchMainBr = false;
	           		System.out.println("ITEM_LIST opening bracket found");
	           	} else if (searchMainBr && !(line.equals(""))){
	           		//System.out.println("2nd if (item_list)");
            		found = false;
	           		searchMainBr = false;
            		System.out.println("Something went wrong :(");
	           		//System.out.println("Line: " + line);
	           		break;
            	}
				if ( line.contains("ITEM_LIST") ) {
					searchMainBr = true;
	           		System.out.println("ITEM_LIST tag found");
				}
				if (found) {
					if (searchItemBr && line.contains("{")) {
		            	found2 = true;
		           		searchItemBr = false;
		           		System.out.println("ITEM opening bracket found");
		           	} else if (searchItemBr && !(line.equals(""))){
	            		found2 = false;
		           		searchItemBr = false;
	            		System.out.println("Something went wrong :(");
		           		//System.out.println("Line: " + line);
		           		break;
	            	}
					if ( line.contains("ITEM") ) {
						searchItemBr = true;
		           		System.out.println("ITEM tag found");
					}
					if (found2 && !(line.contains("}"))) {
						if (line.contains("}")) {
							found2 = false;
							System.out.println("Found } of item in line: " + line);
						} else {
							System.out.println("Calling method");
							creatingObj(TAG, line);
						}
					}
					if (!found2 && found && line.contains("}")) {
						System.out.println("Stopping reading");
					}
					if (TAG[0][1].equals("mobo")) {
						Product p = new Mobo(TAG[1][1], Integer.parseInt(TAG[2][1]), "3", 187, 5, "6", 1000, 4);
						//Product p = new Mobo(TAG[1][1], Integer.parseInt(TAG[2][1]), TAG[3][1], Double.parseDouble(TAG[4][1]), Integer.parseInt(TAG[5][1]), TAG[6][1], Integer.parseInt(TAG[7][1]), Integer.parseInt(TAG[8][1]));
						System.out.println("Product added!");
					}
				}
			}
	       System.out.println(TAG[0][1].equals("mobo"));
	       for (int i = 0; i < TAG.length - 1; i++) {
	    	   if (TAG[i][1] != "")
	    		   System.out.println(TAG[i][0] + ": " + TAG[i][1]);
	       }
		} catch (IOException ioe) {
			ioe.toString();
		} finally {
			
		}
		/*
		try {

			file = new FileReader(FILENAME);
			br = new BufferedReader(file);
			String line = "";
			int model_year, stock;
			String model, manufacturer, item_type;
			double price;
			
			
			while ( (line = br.readLine()) != null) {

				if (line != null) {
					if (line.contains("ITEM_LIST"))
						//System.out.println("ITEM_LIST ==> OK");
					if (line.contains("ITEM"))
						//System.out.println("ITEM ==> OK");
						for (int i = 0; i < TAG.length; i++) {
							TAG[i][1] = "";
						}
					if (line.contains("ITEM_TYPE")) {
						String s = line;
						s = s.replaceAll(" ", "");
						s = s.replace("ITEM_TYPE", "");
						s = s.replaceAll("\t", "");
						s = s.replaceAll("\n", "");
						s = s.replaceAll("\r", "");
						s = s.replace("{", "");
						item_type = s;
					}
					creatingObj(TAG, line);
					if (TAG[3][1].equals("mobo")) {
						Product p = new Mobo(TAG[1][1], Integer.parseInt(TAG[2][1]), TAG[3][1], Double.parseDouble(TAG[4][1]), Integer.parseInt(TAG[5][1]), TAG[6][1], Integer.parseInt(TAG[7][1]), Integer.parseInt(TAG[8][1]));
					} else if (TAG[3][1].equals("CPU")) {
						//Product p = new CPU(TAG[1][1], TAG[2][1], TAG[3][1], TAG[4][1], TAG[5][1], TAG[9][1], TAG[10][1], TAG[11][1]);
					} else if (TAG[3][1].equals("GPU")) {
						//Product p = new GPU(TAG[1][1], TAG[2][1], TAG[3][1], TAG[4][1], TAG[5][1], TAG[12][1], TAG[13][1]);
					} else if (TAG[3][1].equals("RAM")) {
						//Product p = new Disk(TAG[1][1], TAG[2][1], TAG[3][1], TAG[4][1], TAG[5][1], TAG[14][1], TAG[15][1], TAG[16][1]);
					} else if (TAG[3][1].equals("Disk")) {
						//Product p = new Disk(TAG[1][1], TAG[2][1], TAG[3][1], TAG[4][1], TAG[5][1], TAG[17][1], TAG[18][1], TAG[19][1]);
					} else if (TAG[3][1].equals("Monitor")) {
						//Product p = new Monitor(TAG[1][1], TAG[2][1], TAG[3][1], TAG[4][1], TAG[5][1], TAG[20][1], TAG[21][1], TAG[22][1], TAG[23][1]);
					} else if (TAG[3][1].equals("Keyboard")) {
						//Product p = new Keyboard(TAG[1][1], TAG[2][1], TAG[3][1], TAG[4][1], TAG[5][1], TAG[24][1]]);
					} else if (TAG[3][1].equals("Mouse")) {
						//Product p = new Mouse(TAG[1][1], TAG[2][1], TAG[3][1], TAG[4][1], TAG[5][1], TAG[25][1], TAG[26][1]);
					} else if (TAG[3][1].equals("Printer")) {
						//Product p = new Printer(TAG[1][1], TAG[2][1], TAG[3][1], TAG[4][1], TAG[5][1], TAG[27][1], TAG[28][1]);
					};
				}
			}

			for (int i = 0; i < TAG.length - 1; i++) {
				if ((TAG[i][0].contains(" ") && TAG[i][1].contains(" "))) {
					System.out.println(TAG[i][0] + ": " + TAG[i][1]);
			
				}
			}

		} catch (IOException ioe) {

			System.out.println(ioe.toString());

		} finally {

			try {

				if (br != null)
					br.close();

				if (file != null)
					file.close();

			} catch (IOException ioe) {

				System.out.println(ioe.toString());

			}

		}
		*/
		if (false) {
			// Creating ArrayLists that depict the three catalogs
			ArrayList<Product> stock = new ArrayList<Product>();
			ArrayList<Order> orders = new ArrayList<Order>();
			ArrayList<Sale> sales = new ArrayList<Sale>();

			// Creating objects for stock ArrayList that depict specific
			// products
			Product mobo1 = new Mobo("ZQ-11", 2017, "Asus", 100.0, 2, "AMD", 16, 8);
			Product mobo2 = new Mobo("X299-A", 2017, "Asus", 271.0, 2, "Intel", 32, 8);

			Product cpu1 = new CPU("AMD Ryzen 5 1600 Box", 2016, "AMD", 155.0, 2, 3, 4, false);
			Product cpu2 = new CPU("AMD Ryzen 7 1700 Box", 2017, "AMD", 250.39, 2, 3.6, 4, false);

			Product ram1 = new RAM("dd", 2015, "Kingston", 13.0, 2, "DDR4", 4, 2666);
			Product ram2 = new RAM("LPX", 2016, "Corsair", 81.0, 2, "DDR4", 8, 2400);

			Product gpu1 = new GPU("GTX 1060", 2017, "Palet", 678.0, 2, "nVidia", 6);
			Product gpu2 = new GPU("GTX 1080", 2016, "MSI", 673.0, 2, "nVidia", 8);

			Product disk1 = new Disk("PPP", 2016, "SAMSUNG", 50.0, 2, "SSD", 2.5, 256);
			Product disk2 = new Disk("NAS", 2016, "Western Digital", 115.0, 2, "HDD", 3.5, 4000);

			Product monitor1 = new Monitor("FLATRON E1900", 2015, "LG", 110.0, 2, "LCD", 22, 1080, "DVI");
			Product monitor2 = new Monitor("Dell SE2717H", 2016, "Dell", 188.0, 2, "LCD", 27, 1080, "HDMI");

			Product keyboard1 = new Keyboard("ORNATA", 2017, "Razer", 120.0, 2, "Wired");
			Product keyboard2 = new Keyboard("CK108", 2017, "Motospeed", 49.0, 2, "Wired");

			Product mouse1 = new Mouse("Deathader", 2017, "Razer", 65.8, 2, "Optical", "Wired");
			Product mouse2 = new Mouse("M185", 2016, "Logitech", 12.0, 2, "lazer", "Wired");

			Product printer1 = new Printer("PIXMA C1370", 2016, "Canon", 180.0, 2, "Inkjet", "Coloured");
			Product printer2 = new Printer("Xpress M2026", 2015, "Samsung", 47.0, 2, "Inkjet", "Coloured");

			// Adding the specific products to the catalog that stores stock
			// products
			stock.add(mobo1);
			stock.add(mobo2);
			stock.add(cpu1);
			stock.add(cpu2);
			stock.add(ram1);
			stock.add(ram2);
			stock.add(gpu1);
			stock.add(gpu2);
			stock.add(disk1);
			stock.add(disk2);
			stock.add(monitor1);
			stock.add(monitor2);
			stock.add(keyboard1);
			stock.add(keyboard2);
			stock.add(mouse1);
			stock.add(mouse2);
			stock.add(printer1);
			stock.add(printer2);

			Scanner sc = new Scanner(System.in);
			Product obj = null; // Here is stored a copy of the product user selected to buy or order
			int j, select, select2, select3, select4, select5;
			// j : counter used for printing ordered lists
			// date and order Estimated Time of Arrival are fixed
			String date = "20/4/2018", ETA = "24/42018"; 
			boolean done = false;

			while (!done) {
				// Printing Categories
				System.out.println("1. View Availability \n2. View Orders \n3. Exit");
				System.out.print("> ");
				// input: select => View availability, View orders or Exit?
				do {
					select = sc.nextInt(); // View availability(1), View orders(2) or Exit(3)
					if (select != 1 && select != 2 && select != 3) {
						System.out.println("Invalid selection - Try again");
						System.out.print("> ");
					}
				} while (select != 1 && select != 2 && select != 3);

				j = 1;

				/* Entering view availability - user input in main menu = 1 */
				if (select == 1) {
					System.out.println("1. Components \n2. Peripherals");
					System.out.print("> ");

					// input select2 => Components or Peripherals?
					do {
						select2 = sc.nextInt(); // Components or Peripherals
						if (select2 != 1 && select2 != 2) {
							System.out.println("Invalid selection - Try again");
							System.out.print("> ");
						}
					} while (select2 != 1 && select2 != 2);

					select3 = 0; // Initializing with a random number

					if (select2 == 1) { // Entering Component's section
						// Printing products in components section
						System.out.println("1. Motherboard \n2. CPU \n3. GPU \n4. Ram \n5. Disk");
						System.out.print("> ");

						// input: select3 => Which product?
						do {
							select3 = sc.nextInt();
							if (select3 != 1 && select3 != 2 && select3 != 3 && select3 != 4 && select3 != 5) {
								System.out.println("Invalid selection - Try again");
								System.out.print("> ");
							}
						} while (select3 != 1 && select3 != 2 && select3 != 3 && select3 != 4 && select3 != 5);

						// j = 1;

						// Printing all products(their model name) of product
						// category(CPU/GPU/...) user selected
						for (Product p : stock) {
							if (select3 == 1 && p instanceof Mobo) {
								System.out.println(j + ". " + p.getModel());
								j += 1;
							} else if (select3 == 2 && p instanceof CPU) {
								System.out.println(j + ". " + p.getModel());
								j += 1;
							} else if (select3 == 3 && p instanceof GPU) {
								System.out.println(j + ". " + p.getModel());
								j += 1;
							} else if (select3 == 4 && p instanceof RAM) {
								System.out.println(j + ". " + p.getModel());
								j += 1;
							} else if (select3 == 5 && p instanceof Disk) {
								System.out.println(j + ". " + p.getModel());
								j += 1;
							}
						}
					} else if (select2 == 2) { // Entering Peripherals' section
						System.out.println("1. Monitor \n2. Keyboard \n3. Mouse \n4. Printer");
						System.out.print("> ");

						// input: select3 => Which Component product?
						do {
							select3 = sc.nextInt(); // Peripheral product
							if (select3 != 1 && select3 != 2 && select3 != 3 && select3 != 4) {
								System.out.println("Invalid selection - Try again");
								System.out.print("> ");
							}
						} while (select3 != 1 && select3 != 2 && select3 != 3 && select3 != 4);

						// j = 1;

						for (Product p : stock) {
							if (select3 == 1 && p instanceof Monitor) {
								System.out.println(j + ". " + p.getModel());
								j += 1;
							} else if (select3 == 2 && p instanceof Keyboard) {
								System.out.println(j + ". " + p.getModel());
								j += 1;
							} else if (select3 == 3 && p instanceof Mouse) {
								System.out.println(j + ". " + p.getModel());
								j += 1;
							} else if (select3 == 4 && p instanceof Printer) {
								System.out.println(j + ". " + p.getModel());
								j += 1;
							}
						}
					}
					System.out.print("> ");
					// User inputs number of product
					select4 = sc.nextInt();
					j = 0;

					// Determining which specific product the user has selected
					for (Product p : stock) {
						if (select2 == 1) {
							if (select3 == 1 && p instanceof Mobo) {
								j += 1;
							} else if (select3 == 2 && p instanceof CPU) {
								j += 1;
							} else if (select3 == 3 && p instanceof GPU) {
								j += 1;
							} else if (select3 == 4 && p instanceof RAM) {
								j += 1;
							} else if (select3 == 5 && p instanceof Disk) {
								j += 1;
							}
						} else if (select2 == 2) {
							if (select3 == 1 && p instanceof Monitor) {
								j += 1;
							} else if (select3 == 2 && p instanceof Keyboard) {
								j += 1;
							} else if (select3 == 3 && p instanceof Mouse) {
								j += 1;
							} else if (select3 == 4 && p instanceof Printer) {
								j += 1;
							}
						}
						if (select4 == j) {
							obj = p;
							break;
						}
					}
					// Printing specifications of the product that the user
					// selected
					System.out.println(obj.toString());

					// If the specific product user selected exists and has more
					// than 0 stock, enter here
					// checking if the specific product exists on the stock catalog and if there pieces of it availble
					if (checkAvailability(stock, obj) && obj.getStock() > 0) { 
						System.out.println("Product is available. \n1. Buy it \n2. Exit");
						System.out.print("> ");

						// input: select5 => Buy it(1) or Exit(2)?
						do {
							select5 = sc.nextInt();
							if (select5 != 1 && select5 != 2) {
								System.out.println("Invalid selection - Try again");
								System.out.print("> ");
							}
						} while (select5 != 1 && select5 != 2);

						if (select5 == 1) {
							// making a new sale
							makeSaleOrOrder(1, sales, orders, date, ETA, obj); 
							// decreasing stock of this specific product by one
							obj.updateStock(); 
						}
						// If the specific product user selected has 0 stock,
						// enter here
					} else {
						System.out.println("Product is not available");
						System.out.println("1. Order it \n2. Exit");
						System.out.print("> ");

						// input: select5 => Order or Exit?
						do {
							select5 = sc.nextInt();
							if (select5 != 1 && select5 != 2) {
								System.out.println("Invalid selection - Try again");
								System.out.print("> ");
							}
						} while (select5 != 1 && select5 != 2);

						// If user selected to order the product, enter here
						if (select5 == 1) {
							// making a new order
							makeSaleOrOrder(2, sales, orders, date, ETA, obj); 
						}
					}
					/* Entering view orders - user input in main menu = 2 */
					// checking ifuser's inputand if catalog of orders contains info
				} else if (select == 2 && !orders.isEmpty()) {

					// Printing orders
					for (int i = 1; i - 1 < orders.size(); i++)
						System.out.println(i + ". Order number " + i);
					System.out.print("> ");

					// input: select2 => Which order?
					do {
						select2 = sc.nextInt();
						if (select2 >= orders.size()) {
							System.out.println("Invalid selection - Try again");
							System.out.print("> ");
						}
					} while (select2 > orders.size());

					System.out.println(orders.get(select2 - 1).toString() + "\n1. Change status \n2. Purchase");
					System.out.print("> ");

					// input: select3 => Change status or Purchase?
					do {
						select3 = sc.nextInt();
						if (select3 != 1 && select3 != 2) {
							System.out.println("Invalid selection - Try again");
							System.out.print("> ");
						}
					} while (select3 != 1 && select3 != 2);
					// Changing order's status to complete if pending - user
					// selected to change the status of the selected order
					if (select3 == 1 && orders.get(select2 - 1).getOrderStatus() == "Pending") {
						orders.get(select2 - 1).setOrderStatus("Completed");
						System.out.println("Order's status changed to: Completed");
						// Changing order's status to pending if completed -
						// user selected to change the status of the selected
						// order
					} else if (select3 == 1 && orders.get(select2 - 1).getOrderStatus() == "Completed") {
						orders.get(select2 - 1).setOrderStatus("Pending");
						System.out.println("Order's status changed to: Pending");
						// Making a new sale - user selected to purchase the
						// order
					} else if (select3 == 2) {
						orders.get(select2 - 1);
						obj = orders.get(select2 - 1).getProduct();
						makeSaleOrOrder(1, sales, orders, date, ETA, obj);
					}
					/*
					 * Entering View Orders but there are no orders to display -
					 * user input in main menu = 2
					 */
				} else if (select == 2 && orders.isEmpty()) {
					System.out.println("No orders");
					/* Exiting program - user input in main menu = 3 */
				} else if (select == 3) {
					done = true;
				}
			}
		}
		System.out.println("Terminated");
	}
}