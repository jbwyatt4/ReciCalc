/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package recicalc;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {

    public final double salesTax = 0.076;
    public final double wellnessDiscount = 0.05;
    
    public InputStreamReader inputReader = new InputStreamReader(System.in);
    public BufferedReader cin;
    
    private static final Scanner scanner = new Scanner( System.in );
    ArrayList<Recipe> recipeList;
    ArrayList<Ingredient> ingredientList;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Main main = new Main();

        main.mainMenu();
    }

    public void mainMenu() {
        int temp;
        while(true) {
            promptChoice();
            temp = readNumber();
            switch(temp) {
                case 1:  newIngredient();
                         break;
                case 2:  newRecipe();
                         break;
                case 3:  listIngredientFormal();
                         break;
                case 4:  listRecipes();
                         break;
                case 5:  promptRecipe();
                         break;
                case 9:  return;
                default: printText("Not a valid option");
                         break;
            }
        }
    }
    
    public Main() {
        this.cin = new BufferedReader(inputReader);
        this.recipeList = new ArrayList<>();
        this.ingredientList = new ArrayList<>();
    }
    
    public String readLine() {
        try {
             String line = cin.readLine();
             return line;
        }
        catch (IOException err) {
             printText("Error reading line");
             return "NULL";
        }
    }
    
    public int readNumber() {
        int number = scanner.nextInt();
        return number;
    }
    
    public double readDouble() {
       double number = scanner.nextDouble();
       return number;
    }
    private boolean readBoolean() {
       boolean number = scanner.nextBoolean();
       return number;
    }

    public void promptChoice() {
        printText("Please type in a number for a corresponding entry"
                + "\n1) Add New Ingredient"
                + "\n2) Add New Recipe"
                + "\n3) List Ingredients"
                + "\n4) List Recipes"
                + "\n5) Print Recipe"
                + "\n9) Quit"
                );
    }
    
    public void newRecipe() {
        // Must have at least one ingredient
        if(ingredientList.isEmpty()) {
             this.printText("No ingredients found!\nYou must have at least"
                     + " one ingredient.");
             return;
        }
        
        Recipe r = new Recipe();
        
        printText("What is the recipe's name?");
        r.name = readLine();
        
        int tempInt;
        double tempAmount;
        Ingredient tempIng;
        while(true) {
            printText("Add an ingredient.");
            listIngredient();
            
            tempInt = readNumber();
            tempInt--; // we add 1 to the print ingredient list
            if(tempInt < 0 || tempInt >= ingredientList.size()) {
                printText("Please choose a proper number");
                return;
            }
            tempIng = ingredientList.get(tempInt);
            
            printText("Put in amount of ingredient. 3/4 would be 0.75");
            tempAmount = readDouble();

            r.add_ingredient(tempIng, tempAmount);
            
            printText("Add another ingredient? (true/false) Please do not repeat ingredients");
            if (!readBoolean()) {
                break;
            }  
        }
        printText("Put in recipe description. Hit enter to finish.");
        r.recipe_desc = readLine();
        
        recipeList.add(r);
        
        return;
    }

    public void newIngredient() {        
        printText("What is the ingredient's name?");
        String name = readLine();
        
        printText("What is the ingredient's cost ($1.23 is 1.23) ?");
        double cost = readDouble();
        
        printText("What is the ingredient's unit (cups of) ?");
        String unit = readLine();
        
        printText("Is the ingredient produce? (Enter true or false)");
        boolean is_produce = readBoolean();
        
        printText("Is the ingredient organic? (Enter true or false)");
        boolean is_organic = readBoolean();
        
        Ingredient i = new Ingredient(name, cost, unit, is_produce, is_organic);
        
        ingredientList.add(i);
        
        return;
    }
    
    /**
     * Lists all recipes to be choose
     * by number
     * adds one to the index for the user
     */
    public void listRecipes() {
        
        if(recipeList.isEmpty()) {
             printText("No recipes found!\n");
             return;
        }
        
        printText("\n\n");
        for(int i = 0; i < recipeList.size(); i++) {
            printText(i+1 + ") " + recipeList.get(i).name);
        }
        printText("\n\n");
        return;
    }
    
    public void listIngredient() {
        
        if(ingredientList.isEmpty()) {
             printText("No ingredients found!");
             return;
        }
        
        for(int i = 0; i < ingredientList.size(); i++) {
            printText(i+1 + ") " + ingredientList.get(i).name);
        }
        return;
    }
    
    /**
     * Prompt for recipe is just a menu wrapper
     * for printRecipe, fetches the number
     */
    public void promptRecipe() {
        listRecipes();
        
        printText("Put in the recipe you want to view. "
                + "\nPress 0 to go back to main menu.");
        int tempInt = readNumber() - 1; // the menu adjusts adds one
        
        if(tempInt == -1) {
            return;
        } else {
            printRecipe(recipeList.get(tempInt));
        }
    }
    
    /**
     * Prints out a single Recipe with cost.
     * @param r 
     */
    public void printRecipe(Recipe r) {
        printText(r.print_recipe());
        printText(r.print_costs());
    }
    
    
    /**
     * A simple print encapsulation
     * @param text 
     */
    public void printText(String text) {
        System.out.println(text);
    }    

    private void listIngredientFormal() {
        if(ingredientList.isEmpty()) {
             printText("No ingredients found!");
             return;
        }
        
        printText("Produce");
        for(int i = 0; i < ingredientList.size(); i++) {
            if(ingredientList.get(i).is_produce) {
                printText("1 " + ingredientList.get(i).unit + " "
                        + ingredientList.get(i).name + " - $" + 
                        + ingredientList.get(i).cost);
            }
            
        }
        
        printText("\nOrganic");
        for(int i = 0; i < ingredientList.size(); i++) {
            if(ingredientList.get(i).is_organic) {
                printText("1 " + ingredientList.get(i).unit + " "
                        + ingredientList.get(i).name + " - $" + 
                        + ingredientList.get(i).cost);
            }
            
        }
        
        printText("\nOther");
        for(int i = 0; i < ingredientList.size(); i++) {
            if(!ingredientList.get(i).is_produce && 
                    !ingredientList.get(i).is_organic) {
                printText("1 " + ingredientList.get(i).unit + " "
                        + ingredientList.get(i).name + " - $" + 
                        + ingredientList.get(i).cost);
            }
            
        }
        return;
    }
}
