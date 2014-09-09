/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package recicalc;


public class Ingredient {
    public String name;
    public double cost;
    public String unit;
    public boolean is_produce; // only produce escapes taxation
    public boolean is_organic; // gets wellness discount
    
    public Ingredient(String name, double cost, String unit, boolean is_produce, 
            boolean is_organic) {
        this.name = name;
        this.cost = cost;
        this.unit = unit;
        this.is_produce = is_produce;
        this.is_organic = is_organic;
    }
    
    public String print_ingredient() {
        return ("- 1" + " " + unit + " " + name + " - " + cost + " "
                + is_produce + " " + is_organic);
    }
    
    
}
