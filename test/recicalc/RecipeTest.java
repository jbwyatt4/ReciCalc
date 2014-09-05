/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package recicalc;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class RecipeTest {
    
    public Recipe test_recipe;
    
    public RecipeTest() {
        test_recipe = new Recipe();
        
        test_recipe.name = "Spaghetti Sauce";
        test_recipe.recipe_desc = "...";
        test_recipe.add_ingredient(new Ingredient("Tomatos", 10.66, "single ", true, false), 6);
        test_recipe.add_ingredient(new Ingredient("Seasonings", 2.00, "bottle of ", false, false), 0.20);
        test_recipe.add_ingredient(new Ingredient("Expensive Organic Sea Salt", 10.10, "teaspoon of", false, true), 1.00);

        
        
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void test_debug() {
        //double a = 0.66 * 6 * 0.05;
        //double a = 2 * 0.2 * 0.05;
        //assertEquals(0.02,this.test_recipe.nearestCentRoundUp(a), Recipe.margin.doubleValue());
    }
    
    @Test
    public void test_single_cent() {
        double result;
        result = test_recipe.nearestCentRoundUp(0.00);
        System.out.println("Test that $0.00 rounds up.");
        assertEquals(0.00, result, Recipe.margin.doubleValue());
        
        result = test_recipe.nearestCentRoundUp(0.234);
        System.out.println("Test that $0.234 rounds up.");
        assertEquals(0.24, result, Recipe.margin.doubleValue());
        
        result = test_recipe.nearestCentRoundUp(0.21);
        System.out.println("Test that $0.21 rounds up.");
        assertEquals(0.21, result, Recipe.margin.doubleValue());
        
        result = test_recipe.nearestCentRoundUp(0.9901);
        System.out.println("Test that $0.9901 rounds up.");
        assertEquals(1.00, result, Recipe.margin.doubleValue());
    }
            
    @Test
    public void test_seven_cents_rounding() {
        double result;
        
        result = test_recipe.nearestSevenCentsRoundUp(0.00);
        System.out.println("Test that $0.00 stays the same.");
        assertEquals(0.00, result, Recipe.margin.doubleValue());
        
        result = test_recipe.nearestSevenCentsRoundUp(0.77);
        System.out.println("Test that $0.77 stays the same.");
        assertEquals(0.77, result, Recipe.margin.doubleValue());
        
        result = test_recipe.nearestSevenCentsRoundUp(0.981);
        System.out.println("Test that $0.981 rounds up to $1.00.");
        assertEquals(1.00, result, Recipe.margin.doubleValue());
        
        result = test_recipe.nearestSevenCentsRoundUp(0.234);
        System.out.println("Test that $0.234 rounds up.");
        assertEquals(0.28, result, Recipe.margin.doubleValue());
        
        result = test_recipe.nearestSevenCentsRoundUp(0.51);
        System.out.println("Test that $0.51 rounds up.");
        assertEquals(0.56, result, Recipe.margin.doubleValue());
        
        result = test_recipe.nearestSevenCentsRoundUp(0.22001);
        System.out.println("Test that $0.22001 is detected and rounds up.");
        assertEquals(0.28, result, Recipe.margin.doubleValue());
        
        result = test_recipe.nearestSevenCentsRoundUp(66.66);
        System.out.println("Test that $66.66 is rounded up.");
        assertEquals(66.70, result, Recipe.margin.doubleValue());
                
    }
    
    @Test
    public void test_total_price() {
        double result = test_recipe.calc_total_price();
        double answer = 74.46;
        System.out.println("$" + answer+" Total price should be $" + result);
        assertEquals(answer, result, Recipe.margin.doubleValue());
    }
    
    @Test
    public void test_sales_tax() {
        double result = test_recipe.calc_sales_tax();
        double answer = 0.91;
        System.out.println("$" + answer+" Sales tax should be $" + result);
        assertEquals(answer, result, Recipe.margin.doubleValue());
    }
    
    @Test
    public void test_wellness_discount() {
        double result = test_recipe.calc_wellness_discount();
        double answer = 0.51;
        System.out.println("$" + answer+" Wellness discount should be $" + result);
        assertEquals(answer, result, Recipe.margin.doubleValue());        
    }
    
    @Test
    public void test_total_cost() {
        double result = test_recipe.calc_total_cost();
        double answer = 74.86;
        System.out.println("$" + answer+" Total cost should be $" + result);
        assertEquals(answer, result, Recipe.margin.doubleValue());        
    }

}
