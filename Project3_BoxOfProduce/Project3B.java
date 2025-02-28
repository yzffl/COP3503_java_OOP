/*
 * Your Community Supported Agriculture (CSA) farm delivers a box of fresh fruits 
 * and vegetables to your house once a week. For this Programming Project, 
 * define the class BoxOfProduce that contains exactly three bundles of fruits
 * or vegetables. You can represent the fruits or vegetables as three instance variables
 * of type String. Add appropriate constructor, accessor, and mutator methods. 
 * Also write a toString() method that returns as a String the complete contents of the
 * box.
 *  
 * Next, write a main method that creates a BoxOfProduce with three items randomly
 * selected from this list:
 * Broccoli Tomato Kiwi Kale Tomatillo 
 * This list should be stored in a text file 
 * that is read in by your program. For now you can assume that the list contains 
 * exactly five types of fruits or vegetables. Do not worry if your program randomly 
 * selects duplicate produce for the three items. Next, the main method should display 
 * the contents of the box and allow the user to substitute any one of the five possible
 * fruits or vegetables for any of the fruits or vegetables selected for the box.
 * After the user is done with substitutions, output the final contents of the box to be
 * delivered. If you create additional methods to select the random items and to select
 * valid substitutions, then your main method will be simpler to write.
 * 
 * Modify the main method with a loop so that an arbitrary number of BoxOfProduce objects 
 * are created and substitutions are allowed for each box. Add a menu so the user can 
 * decide when to stop creating boxes.
 * 
 * You would like to throw in a free recipe flyer for salsa verde if the box contains
 * tomatillos. However, there are only five recipe flyers. Add a static variable to the 
 * BoxOfProduce class that counts the number of recipe flyers remaining and initialize it
 * to 5. Also add an instance variable that indicates whether or not the box contains 
 * a recipe flyer and modify the toString() method to also output ¡°salsa verde recipe¡± 
 * if the box contains a recipe flyer. Finally, add logic inside the class so that if 
 * the box contains at least one order of tomatillos then it automatically gets a 
 * recipe flyer until all of the recipe flyers are gone. Note that a box should only 
 * get one recipe flyer even if there are multiple orders of tomatillos.
 * 
 * Test your class by creating boxes with tomatillos from your menu until 
 * all of the flyers are gone.
 * 
 */

/* 
 * @Author: Y.Z.
 * Date: 6/14/2017
 * 
 */
import java.util.Random;
import java.util.Scanner;


/**define a class BoxOfProduce*/

class BoxOfProduce {

	private String fruit1;
	private String fruit2;
	private String fruit3;
	private static int numRecipeRemaining=5;
	private boolean hasRecipe=false;

	public BoxOfProduce(){
		fruit1="";
		fruit2="";
		fruit3="";
	}

	public BoxOfProduce(String fruit1, String fruit2, String fruit3) {

		this.fruit1=fruit1;
		this.fruit2=fruit2;
		this.fruit3=fruit3;

		//setFruit1(fruit1);
		//setFruit2(fruit2);
		//setFruit3(fruit3);

	}


	/**accessors*/
	public String getFruit1()
	{
		return fruit1;
	}

	public String getFruit2()
	{
		return fruit2;
	}

	public String getFruit3() {
		return fruit3;
	}

	/**mutators*/
	public void setFruit1(String fruit1)
	{
		this.fruit1 = fruit1;
	}

	public void setFruit2(String fruit2)
	{
		this.fruit2 = fruit2;
	}

	public void setFruit3(String fruit3)
	{
		this.fruit3 = fruit3;
	}

	/** return a String of the complete contents of the box*/
	public String toString()
	{

		// if there are some Recipe flyer left and the box contains tomatillos, throw a flyer in the box.
		if (numRecipeRemaining!=0 && !hasRecipe && (fruit1.contains("Tomatillo")||fruit2.contains("Tomatillo")||fruit3.contains("Tomatillo"))){
			hasRecipe=true;
			numRecipeRemaining--;
		}

		// if the user substitute all tomatillos by other product, take the flyer out of the box.
		if (hasRecipe && (!fruit1.contains("Tomatillo")&&!fruit2.contains("Tomatillo")&&!fruit3.contains("Tomatillo"))){
			hasRecipe=false;
			numRecipeRemaining++;
		}

		// return a String according to if the box has a recipe flyer or not
		if (hasRecipe){			
			return "1 : " + fruit1 + "\n" + "2 : " + fruit2 + "\n" + "3 : " + fruit3+ "\n" 
					+ "Salsa verde recipe in the box: 1\n" + "Reciept remaining: "+ numRecipeRemaining + "\n";
		}else
			return "1 : " + fruit1 + "\n" + "2 : " + fruit2 + "\n" + "3 : " + fruit3+ "\n" 
			+ "Salsa verde recipe in the box: 0\n" + "Reciept remaining: "+ numRecipeRemaining + "\n";
	}

}


public class Project3B {
	public static void main (String[] args) throws Exception{
		final int fullLength=5;
		final int boxLength=3;
		boolean isInput1Valid=false;
		boolean isInput2Valid=false;
		boolean createBox=true;
		boolean ifContinue=true;
		int boxCount=0;

		// Create a full fruit list
		String[] fruitList=new String[fullLength];

		// Create a box fruit index and the item list 
		int[] index=new int[boxLength];
		String[] boxFruit=new String[boxLength];

		Scanner input=new Scanner(System.in);

		readFile(fruitList);
		displayWholeList(fruitList);

		while (createBox){
			boxCount++;
			ifContinue=true;
			System.out.println("\nNumber of boxes: "+boxCount);
			selectBoxFruit(index, boxFruit, fruitList);

			//Create a BoxOFProduce object
			BoxOfProduce box=new BoxOfProduce(boxFruit[0], boxFruit[1], boxFruit[2]);

			//display the box content

			System.out.println(box.toString());


			while (ifContinue){
				System.out.print("Enter the fruit item from the whole list that you want to put in the box(1-5): ");
				int input1=input.nextInt();
				input1=getValidInput1(isInput1Valid, input1);


				System.out.print("Enter the fruit item in the box that you want to be substituted(1-3): ");
				int input2=input.nextInt();
				input2=getValidInput2(isInput2Valid, input2);

				// do the substitution
				if (input2==1){
					box.setFruit1(fruitList[input1-1]);
				}
				else if (input2==2){
					box.setFruit2(fruitList[input1-1]);
				}
				else{
					box.setFruit3(fruitList[input1-1]);
				}
				System.out.println("After the substition, the contents of the box is: ");
				System.out.println(box.toString());

				// if the user want to do one more substitution
				ifContinue=isDoneSubstitution(ifContinue);
			}

			createBox=isCreateOneBox(createBox, ifContinue);
		}
		System.out.println("\nStop creating boxes, the total number of boxes: "+boxCount);
	}




	/** read the whole list from a text file*/
	public static void readFile(String[] list )throws Exception{

		//Create a file instance
		java.io.File file = new java.io.File("list.txt");

		//Create a Scanner for the file
		java.util.Scanner input =new java.util.Scanner(file);

		//Read data from a file
		int i=0;
		while(input.hasNext()){
			String content=input.next();
			list[i]=content;
			i++;
		}

		input.close();
	}


	/** display the whole list in the text file*/
	public static void displayWholeList(String[] list){
		System.out.println("The whole fruit list is:");
		for (int i=0; i<list.length; i++){
			System.out.println(i+1 + ": " + list[i]);
		}

	}

	/** randomly select three fruit from the whole list in the box, then display the box list*/
	public static void selectBoxFruit(int[] index, String[] box, String[] list){
		System.out.println("The three fruits in the box is: ");
		for (int i=0; i<3; i++){
			Random rdm=new Random();
			index[i]=rdm.nextInt(5);
			box[i]=list[index[i]];	
			//BoxOfProduce box=new BoxOfProduce(boxFruit[0], boxFruit[1], boxFruit[2]);
		}
	}

	/** get the valid input from the user for the fruit item in the whole list, between 1-5, inclusive*/
	public static int getValidInput1(boolean isValid, int input1){
		Scanner input=new Scanner(System.in);	
		while (!isValid){

			if (input1<1||input1>5){
				System.out.println("Invalid fruit item number.\n");
				System.out.print("Enter the fruit item that you want to put in the box(1-5): ");
				input1=input.nextInt();
			}
			else{
				isValid=true;
			}
		}
		return input1;
	}

	/** get the valid input from the user for the fruit item in the box, between 1-3, inclusive*/
	public static int getValidInput2(boolean isValid, int input2){
		Scanner input=new Scanner(System.in);
		while (!isValid){

			if (input2<1||input2>3){
				System.out.println("Invalid fruit intem number.\n");
				System.out.print("Enter the fruit item in the box that you want to replace(1-3): ");
				input2=input.nextInt();
			}
			else{
				isValid=true;
				System.out.println();
			}
		}
		return input2;
	}

	/** prompt to ask the user if continue substitution*/
	public static boolean isDoneSubstitution(boolean substitution){
		Scanner scan=new Scanner(System.in);
		System.out.print("Press Y to continue subsitutions, others to exit substitution: ");
		String input=scan.nextLine();
		char result=input.charAt(0);

		substitution=(result=='Y'||result=='y')? true:false;
		return substitution;
	}


	public static boolean isCreateOneBox(boolean create,boolean substitution){
		Scanner scan=new Scanner(System.in);

		System.out.print("\nPress Y to create one more box, others to stop creating: ");

		String input=scan.nextLine();
		char result=input.charAt(0);

		create= (result=='Y'||result=='y')? true:false;

		return create;
	}
}




