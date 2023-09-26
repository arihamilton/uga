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
        List<String> attrList = rel.getAttrs();

        for (int c = 0; c < origAttr.size(); c++) {
            for (int c2 = 0; c2 < attrList.size(); c2++) {
                if (origAttr.get(c).equals(attrList.get(c2))) {
                    attrList.set(c2, renamedAttr.get(c));
                }
            }
        }

        //create and return new relation
        Relation newRel = rb.newRelation(rel.getName(), attrList, rel.getTypes());
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
 
    }

    /**
     * Peforms natural join on relations rel1 and rel2.
     * 
     * @return The resulting relation after applying natural join.
     */
    public Relation join(Relation rel1, Relation rel2) {

    }

    /**
     * Performs theta join on relations rel1 and rel2 with predicate p.
     * 
     * @return The resulting relation after applying theta join.
     */
    public Relation join(Relation rel1, Relation rel2, Predicate p) {

    }
}