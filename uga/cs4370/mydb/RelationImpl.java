package uga.cs4370.mydb;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements the "Relation" interface.
 */
public class RelationImpl implements Relation {

    private String name;
    private List<String> attrs = new ArrayList<String>();
    private List<Type> types = new ArrayList<Type>();

    private List<List<Cell>> rows = new ArrayList<List<Cell>>();


  /**
     * Returns the name of the relation.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the relation.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the row count of the relation.
     */
    public int getSize(){
        return rows.size();
    }

    /**
     * Get the rows of the relation.
     * Return a deep copy of the rows to avoid 
     * modifications to the rows by the callers of this method.
     */
    public List<List<Cell>> getRows(){
        return rows;
    }

    /**
     * Return the type of each column in a list.
     */
    public List<Type> getTypes(){
        return types;
    }

    /**
     * Sets the types of each column in a list.
     */
    public void setTypes(List<Type> types){
        this.types = types;
    }

    /**
     * Returns the list of attributes of the relation.
     */
    public List<String> getAttrs(){
        return attrs;   
    }

    /**
     * Sets the list of attributes of the relation.
     */
    public void setAttrs(List<String> attrs){
        this.attrs = attrs;
    }

    /**
     * Returns true only if attr exist in the relation.
     */
    public boolean hasAttr(String attr){
        if (attrs.contains(attr)) {
            return true;
        }
        return false;
    }

    /**
     * Returns the index of the attr.
     * 
     * @throws IllegalArgumentException if attr does not 
     * exist in the relation.
     */
    public int getAttrIndex(String attr){
        if (!attrs.contains(attr)) {
            throw new IllegalArgumentException("Attribute does not exist in the relation.");
        }
        return attrs.indexOf(attr);
    }

    /**
     * Inserts a row in the relation.
     * 
     * @throws IllegalArgumentException if the cell types do not correspond 
     * to the attribute types of the relation or if the row already exists.
     */
    public void insert(Cell... cells) {
        List<Cell> row = new ArrayList<Cell>();

        for (int i = 0; i < cells.length; i++) {

            // Check if cell type corresponds to the concurrent attribute type of the relation
            if (cells[i].getType() != types.get(i)) {
                throw new IllegalArgumentException("Cell types do not correspond to the attribute types of the relation.");
            }

            row.add(cells[i]);
        }

        // Check if the row already exists in the relation
        if (rows.contains(row)) {
                throw new IllegalArgumentException("Row already exists in relation.");
        }

        rows.add(row);
    }

    /**
     * Inserts a row in the relation.
     * 
     * @throws IllegalArgumentException if the cell types do not correspond 
     * to the attribute types of the relation or if the row already exists.
     */
    public void insert(List<Cell> cells) {

        for (int i = 0; i < cells.size(); i++) {
            // Check if cell type corresponds to the concurrent attribute type of the relation
            if (cells.get(i).getType() != types.get(i)) {
                throw new IllegalArgumentException("Cell types do not correspond to the attribute types of the relation.");
            }
        }

        // Check if the row already exists in the relation
        if (rows.contains(cells)) {
                throw new IllegalArgumentException("Row already exists in relation.");
        }

        rows.add(cells);
    }

    /**
     * Print the relation properly formatted as a table 
     * to the standard ouput.
     * The result should look similar to MySql table outputs.
     */
    public void print() {

        List<Integer> dashLengths = new ArrayList<Integer>();

        // Set dash length to be length of attribute name
        for (String attr : attrs ) {
            dashLengths.add(attr.length()+2);
        }

        int len = 0;

        // If cell value is longer than its attribute's name, set it to be dash length
        for (List<Cell> row : rows ) {
            for (int i = 0; i < row.size(); i++) {
                len = row.get(i).toString().length();
                if (len > dashLengths.get(i)-2) {
                    dashLengths.set(i, len+2);
                }
            }
        }

        ///////////////////////////
        // Generate row separator
        String rowSeparator = "";
        for (int dashLength : dashLengths ) {
            rowSeparator = rowSeparator + "+";
            for (int i = 0; i < dashLength; i++){
                rowSeparator = rowSeparator + "-";
            }
        }
        rowSeparator = rowSeparator + "+";

        // Print row separator
        System.out.println(rowSeparator);

        ///////////////////////////
        // Print attribute names
        for (int i = 0; i < dashLengths.size(); i++){
            System.out.print("| ");
            // Pad right string with (# dashlength) spaces
            System.out.print(String.format("%1$-" + (dashLengths.get(i)-1) + "s", attrs.get(i)));
        }
        System.out.print("|\n");

        // Print row separator
        System.out.println(rowSeparator);

        ///////////////////////////
        // Print cells
        for (List<Cell> row : rows ) {
            // Print each cell in row
            for (int i = 0; i < row.size(); i++) {
                System.out.print("| ");
                // Pad right string with (# dashlength) spaces
                System.out.print(String.format("%1$-" + (dashLengths.get(i)-1) + "s", row.get(i).toString()));
            }
            System.out.print("|\n");
        }

        // Print row separator
        System.out.println(rowSeparator);


    }


}