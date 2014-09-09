/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package recicalc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;


public class Recipe {
    public String name;
    /**
     * Margin represents the allowed error for cents.
     * notice I wrote cents so it's used on numbers that have already been
     * shifted by two
     */
    public static final BigDecimal margin = new BigDecimal(0.00001);
    public ArrayList<Ingredient> list;
    public ArrayList<Double> list_amount;
                                 // corresponds to the Ingredient list,
                                 // list the amount of units
                                 // One limitation of this method is that units
                                 // may not be converted i.e. no 
                                 // 16oz = 1 gallon
    public String recipe_desc;
    
    public String print_recipe() {
        String tempStr = "Recipe " + name + ":\n";
        
        for(int i = 0; i < list.size(); i++) {
            tempStr += "- " + list_amount.get(i) + " " + list.get(i).unit 
                    + " " +
                    list.get(i).name + "\n";
        }
        tempStr += recipe_desc;
        return tempStr;
    }
    
    public String print_costs() {
        String tempStr = "Costs of " + name + ":\n";
        
         
        tempStr += "- Sales Tax: $" + calc_sales_tax() + "\n";
        tempStr += "- Wellness Discount: $(" + calc_wellness_discount() + ")\n";
        tempStr += "- Total Cost: $" + calc_total_cost() + "\n";
        
        return tempStr;
    }
    
    /**
     * If not produce, we tax it!
     * Rounded up to the nearest 7 seven cents in the cents range 0-99
     * @return 
     */
    public double calc_sales_tax() {
        double tempNum = 0;
        
        for(int i = 0; i < list.size(); i++) {
            if(!list.get(i).is_produce) {
                tempNum += list.get(i).cost * list_amount.get(i) * 0.086;
            }
        }
        
        // round up to the nearest 7 cents in the cents range
        return nearestSevenCentsRoundUp(tempNum);
    }
    
    /**
     * The docs imply that wellness discount is calculated seperately from tax
     * if this is incorrect I believe this discount should be applied first.
     * Organic also does not imply produce as foods such as meat and salt
     * are called organic.
     * Rounded up to the nearest cent in the cents range 0-99
     * @return 
     */
    public double calc_wellness_discount() {
        double tempNum = 0;
        
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).is_organic) {
                tempNum += list.get(i).cost * list_amount.get(i) * 0.05;
            }
        }
        
        // round up here since it's a % of total price
        return nearestCentRoundUp(tempNum);
    }
    
    /**
     * we just add them together
     * @return 
     */
    public double calc_total_cost() {
        double tempNum = 0;
        tempNum += calc_total_price();
        tempNum += calc_sales_tax();
        tempNum -= calc_wellness_discount();
        
        // The 'easy' way to handle formatting numbers.
        DecimalFormat df = new DecimalFormat("#.##"); // takes care of some trailing digits
        // far less than the margin
        String format = df.format(tempNum);
        
        return Double.valueOf(format);//tempNum;
    }
    
    /**
     * calculates total price
     * @return 
     */
    public double calc_total_price() {
        double tempNum = 0;
        
        for(int i = 0; i < list.size(); i++) {
            tempNum += list.get(i).cost * list_amount.get(i);
        }
        
        // to avoid polluting the total cost
        return nearestCentRoundUp(tempNum);
    }
    
    public void add_ingredient(Ingredient i, double amount) {
        list.add(i);
        list_amount.add( amount);
    }
    
    public Recipe() {
        list = new ArrayList<>();
        list_amount = new ArrayList<>();
    }
    
    /**
     * Rounds the nearest cent up.
     * @param value
     * @return 
     */
    public double nearestCentRoundUp(double value) {
        BigDecimal bigValue = new BigDecimal(value);
        bigValue = bigValue.movePointRight(2);

        BigDecimal[] bigArray;
        bigArray = bigValue.divideAndRemainder(new BigDecimal(1));
        // if the remander is bigger than our margin
        if(bigArray[1].abs().compareTo(margin) == 1) {

            bigValue = new BigDecimal(Math.ceil(bigValue.doubleValue()));
            
        }
        bigValue = bigValue.setScale(0, RoundingMode.DOWN);
        bigValue = bigValue.setScale(2, RoundingMode.DOWN);
        bigValue = bigValue.movePointLeft(2);
        
        return bigValue.doubleValue();
    }
    
    /**
     * Plot out the 3 cases
     * 
     * if 0, exactly a mutiple of 7 between 0-99
     * 
     * if greater than 98, set to a next dollar
     * 
     * if between 0-98 set as a mutiple
     * 
     * @param value
     * @return 
     */
    public double nearestSevenCentsRoundUp(double value) {
        
        BigDecimal bigValue = new BigDecimal(value);
        bigValue = bigValue.movePointRight(2);

        BigDecimal[] bigArray = bigValue.divideAndRemainder(new BigDecimal(100));

        BigDecimal bigPartial = bigArray[1];
        BigDecimal bigLarge = bigValue.subtract(bigArray[1]);
        
        BigDecimal[] bigArray2 = bigPartial.divideAndRemainder(new BigDecimal(7));
        
        // if less than margin
        if(bigArray2[1].abs().compareTo(margin) == -1) {
            // Checks if a mutiple of 7 within cents range
            return value;
        } else if(bigPartial.compareTo(new BigDecimal(98.00)) == 1
                ) {
            // Checks if greater than 98, rounds to nearest dollar if so
            // we do not need to check the margin
            return Math.ceil(value);
        } else {
            //tempNum = tempValue;
            //BigDecimal tempArray3[] = tempNum.divideAndRemainder(new BigDecimal(7));
            BigDecimal seven = (new BigDecimal(7));
            BigDecimal remander = seven.subtract(bigArray2[1]);
            BigDecimal newBigValue = bigPartial.add(remander);
            newBigValue = new BigDecimal(Math.floor(newBigValue.doubleValue()));
            // clear out any round errors from the double conversion
            newBigValue = newBigValue.setScale(0, RoundingMode.DOWN);
            newBigValue = newBigValue.setScale(2, RoundingMode.DOWN);
            newBigValue = newBigValue.add(bigLarge).movePointLeft(2);
            
            return newBigValue.doubleValue();
        }
        
    }
    
}
