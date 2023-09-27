package uga.cs4370.mydb;

import java.util.ArrayList;
import java.util.List;

public class RAImpl implements RA {
    RelationBuilder rb = new RelationBuilderImpl();

    /**
     * Performs the select operation on the relation rel
     * by applying the predicate p.
     * 
     * @return The resulting relation after applying the select operation.
     */
    public Relation select(Relation rel, Predicate p) {
        //create new relation with needed conditions
        Relation newRel = rb.newRelation(rel.getName(),rel.getAttrs(), rel.getTypes());
        List<List<Cell>> rows = rel.getRows();

        //add the rows that pass the predicate to the new relation
        for (int c = 0; c < rel.getSize(); c++) {
            if (p.check(rows.get(c))) {
                newRel.insert(rows.get(c));
            }
        }
        return newRel;
        //this seems too simple to be the correct implementation double check this if you want
    }

    /**
     * Performs the project operation on the relation rel
     * given the attributes list attrs.
     * 
     * @return The resulting relation after applying the project operation.
     * 
     * @throws IllegalArgumentException If attributes in attrs are not 
     * present in rel.
     */
    public Relation project(Relation rel, List<String> attrs) throws IllegalArgumentException { 
        
        List<Integer> indices = new ArrayList<Integer>();
        List<Type> types = new ArrayList<Type>();
        
        for (int c = 0; c < attrs.size(); c++) {
            if (rel.hasAttr(attrs.get(c))) {
                indices.add(rel.getAttrIndex(attrs.get(c)));
                types.add(rel.getTypes().get(c));
            }
            else throw new IllegalArgumentException("Relation does not contain input attributes");
        }

        Relation newRel = rb.newRelation(rel.getName(), attrs, types);

        // Add rows only containing projected attributes to new relation
        for (List<Cell> row : rel.getRows()) {
            List<Cell> newRow = new ArrayList<Cell>();
            for (Integer index : indices) {
                newRow.add(row.get(index));
            }
            newRel.insert(newRow);
        }

        return newRel;
    }

    /**
     * Performs the union operation on the relations rel1 and rel2.
     * 
     * @return The resulting relation after applying the union operation.
     * 
     * @throws IllegalArgumentException If rel1 and rel2 are not compatible.
     */
    public Relation union(Relation rel1, Relation rel2) {
        //check for compatible attributes
        if (!rel1.getAttrs().equals(rel2.getAttrs())) throw new IllegalArgumentException("The relations do not have compatible attributes.");

        //create list of rows
        List<List<Cell>> rowlist = rel1.getRows();
        List<List<Cell>> rel2list = rel2.getRows();
        for (int c = 0; c < rel2list.size(); c++) {
            boolean dupe = false;
            for (int c2 = 0; c2 < rowlist.size(); c2++) {
                if (rowlist.get(c2).equals(rel2list.get(c))){
                    dupe = true;
                }
            }
            if (!dupe) {
                rowlist.add(rel2list.get(c));
            }
        }

        //create new relation
        Relation newRel = rb.newRelation(rel1.getName(), rel1.getAttrs(), rel2.getTypes());
        for (int c = 0; c < rowlist.size(); c++) {
            newRel.insert(rowlist.get(c));
        }
        return newRel;
    }

    /**
     * Performs the set difference operaion on the relations rel1 and rel2.
     * 
     * @return The resulting relation after applying the set difference operation.
     * 
     * @throws IllegalArgumentException If rel1 and rel2 are not compatible.
     */
    public Relation diff(Relation rel1, Relation rel2) {
        //check for compatible attributes
        if (!rel1.getAttrs().equals(rel2.getAttrs())) throw new IllegalArgumentException("The relations do not have compatible attributes.");

        List<List<Cell>> rel1Rows = rel1.getRows();
        List<List<Cell>> rel2Rows = rel2.getRows();

        Relation newRel = rb.newRelation(rel1.getName(), rel1.getAttrs(), rel2.getTypes());

        // If row exists in rel1 but not rel2, add it to the new relation
        for (int i = 0; i < rel1Rows.size(); i++) {
            if (!(rel2Rows.contains(rel1Rows.get(i)))) {
                newRel.insert(rel1Rows.get(i));
            }
        }

        return newRel;

    }

    /**
     * Renames the attributes in origAttr of relation rel to corresponding 
     * names in renamedAttr.
     * 
     * @return The resulting relation after renaming the attributes.
     * 
     * @throws IllegalArgumentException If attributes in origAttr are not present in 
     * rel or origAttr and renamedAttr do not have matching argument counts.
     */
    public Relation rename(Relation rel, List<String> origAttr, List<String> renamedAttr) throws IllegalArgumentException {
        //check to see if attribute list is valid
        for (int c = 0; c < origAttr.size(); c++) {
            if (!rel.hasAttr(origAttr.get(c))) throw new IllegalArgumentException("Attributes to be renamed do not exist");
        }

        if (origAttr.size() != renamedAttr.size()) throw new IllegalArgumentException("Renamed and original attribute lists are not equal size.");

        //alter attribute list
        // List<String> attrList = rel.getAttrs();

        // for (int c = 0; c < origAttr.size(); c++) {
        //     for (int c2 = 0; c2 < attrList.size(); c2++) {
        //         if (origAttr.get(c).equals(attrList.get(c2))) {
        //             attrList.set(c2, renamedAttr.get(c));
        //         }
        //     }
        // }

        //create and return new relation
        Relation newRel = rb.newRelation(rel.getName(), renamedAttr, rel.getTypes());
        for (List<Cell> row : rel.getRows()) {
            newRel.insert(row);
        }
        return newRel;
    }

    /**
     * Performs cartisian product on relations rel1 and rel2.
     * 
     * @return The resulting relation after applying cartisian product.
     * 
     * @throws IllegalArgumentException if rel1 and rel2 have common attibutes.
     */
    public Relation cartesianProduct(Relation rel1, Relation rel2) {

        //check for compatible attributes
        if (rel1.getAttrs().equals(rel2.getAttrs())) throw new IllegalArgumentException("The relations have common attributes.");

        List<String> newAttrs = new ArrayList<String>();
        List<Type> newTypes = new ArrayList<Type>();

        // Add attributes from rel1 to new attribute list
        // If attr in rel1 exists in rel2, change its name
        for (String attr : rel1.getAttrs()) {
            if (rel2.getAttrs().contains(attr)) {
                newAttrs.add(rel1.getName() + attr);
            }
            else {
                newAttrs.add(attr);
            }
        }
        // Add attributes from rel2 to new attribute list
        // If attr in rel2 exists in rel1, change its name
        for (String attr : rel2.getAttrs()) {
            if (rel1.getAttrs().contains(attr)) {
                newAttrs.add(rel2.getName() + attr);
            }
            else {
                newAttrs.add(attr);
            }
        }
        // Add types from rel1 and rel2 to new type list
        for (Type type : rel1.getTypes()) {
            newTypes.add(type);
        }
        for (Type type : rel2.getTypes()) {
            newTypes.add(type);
        }

        Relation newRel = rb.newRelation(rel1.getName() + "x" + rel2.getName(), newAttrs, newTypes);

        // For each row in rel1, add (row * all rows in rel2) to new relation
        for (List<Cell> row : rel1.getRows()) {

            for (List<Cell> row2 : rel2.getRows()) {
                List<Cell> newRow = new ArrayList<Cell>();
                for (Cell cell : row) {
                    newRow.add(cell);
                }
                for (Cell cell2 : row2) {
                    newRow.add(cell2);
                }
                newRel.insert(newRow);
            }

        }
        return newRel;
 
    }

    /**
     * Performs natural join on relations rel1 and rel2.
     * 
     * @return The resulting relation after applying natural join.
     */
    
     public Relation join(Relation rel1, Relation rel2) {
        // Check for compatible attributes
        List<String> commonAttrs = new ArrayList<>();
        for (String attribute : rel1.getAttrs()) {
            if (rel2.hasAttr(attribute)) {
                commonAttrs.add(attribute);
            }
        }
    
        // If there are no common attributes, return an empty relation
        if (commonAttrs.isEmpty()) {
            System.out.println("it didn't work");
            return rb.newRelation("Join Result", new ArrayList<>(), new ArrayList<>());
        }
    
        // New RelationImpl to store the result
        List<String> joinAttrs = new ArrayList<>(rel1.getAttrs());
        joinAttrs.addAll(rel2.getAttrs());
    
        List<Type> joinTypes = new ArrayList<>(rel1.getTypes());
        joinTypes.addAll(rel2.getTypes());
    
        List<List<Cell>> joinRows = new ArrayList<>();
    
        for (List<Cell> row1 : rel1.getRows()) {
            for (List<Cell> row2 : rel2.getRows()) {
                boolean allAttributesMatch = true;
                for (String commonAttribute : commonAttrs) {
                    int index1 = rel1.getAttrs().indexOf(commonAttribute);
                    int index2 = rel2.getAttrs().indexOf(commonAttribute);
                    if (!row1.get(index1).equals(row2.get(index2))) {
                        allAttributesMatch = false;
                        break;
                    }
                }
    
                if (allAttributesMatch) {
                    List<Cell> joinRow = new ArrayList<>(row1);
                    joinRow.addAll(row2);
                    joinRows.add(joinRow);
                }
            }
        }
        Relation joinResult = rb.newRelation("Join Result", joinAttrs, joinTypes);
        for (List<Cell> joinRow : joinRows) {
            joinResult.insert(joinRow);
        }     
        return joinResult;
    }
    
            

    /**
     * Performs theta join on relations rel1 and rel2 with predicate p.
     * 
     * @return The resulting relation after applying theta join.
     */

    public Relation join(Relation rel1, Relation rel2, Predicate p) {
        // Check for compatible attributes
        // Assume that the common attributes are determined based on the predicate p

        List<String> newAttrs = new ArrayList<>();
        List<Type> newTypes = new ArrayList<>();

        // Add attributes from rel1 to new attribute list
        for (String attr : rel1.getAttrs()) {
            newAttrs.add(rel1.getName() + attr);
            newTypes.add(rel1.getTypes().get(rel1.getAttrs().indexOf(attr)));
        }

        // Add attributes from rel2 to new attribute list
        for (String attr : rel2.getAttrs()) {
            newAttrs.add(rel2.getName() + attr);
            newTypes.add(rel2.getTypes().get(rel2.getAttrs().indexOf(attr)));
        }

        Relation newRel = rb.newRelation(rel1.getName() + "Join" + rel2.getName(), newAttrs, newTypes);

        // For each combination of rows from rel1 and rel2, apply the predicate
        for (List<Cell> row1 : rel1.getRows()) {
            for (List<Cell> row2 : rel2.getRows()) {
                List<Cell> combinedRow = new ArrayList<>(row1);
                combinedRow.addAll(row2);

                if (p.check(combinedRow)) {
                    newRel.insert(combinedRow);
                }
            }
        }

        return newRel;
    }
}