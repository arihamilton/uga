package uga.cs4370.mydb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uga.cs4370.mydb.Relation;
import uga.cs4370.mydb.RelationImpl;
import uga.cs4370.mydb.Type;
import uga.cs4370.mydb.PredicateImpl.ComparisonOperator;

public class Tester {

    public static void main(String[] args) {
        
        RelationBuilder rb = new RelationBuilderImpl();
        Relation rel = rb.newRelation("relation1", Arrays.asList("id", "name", "gender", "dept"), Arrays.asList(Type.INTEGER, Type.STRING, Type.STRING, Type.STRING));

        List<Cell> row = new ArrayList<Cell>();
        row.add(new Cell(1));
        row.add(new Cell("Ram"));
        row.add(new Cell("m"));
        row.add(new Cell("Information Technology"));
        rel.insert(row);

        List<Cell> row2 = new ArrayList<Cell>();
        row2.add(new Cell(2));
        row2.add(new Cell("Sita"));
        row2.add(new Cell("f"));
        row2.add(new Cell("Computer Science"));
        rel.insert(row2);

        rel.print();

        System.out.println("id is at index " + rel.getAttrIndex("id"));
        System.out.println("name is at index " + rel.getAttrIndex("name"));
        System.out.println("gender is at index " + rel.getAttrIndex("gender"));
        System.out.println("dept is at index " + rel.getAttrIndex("dept"));

        System.out.println("\nrelation is of size " + rel.getSize());

        // Testing RA /////////////////

        // Select

        RA ra = new RAImpl();

        System.out.println("\ngender = f: ");
        Predicate predGenderF = new PredicateImpl(2, "f", ComparisonOperator.EQUALS);
        Relation onlyGenderF = ra.select(rel, predGenderF);
        onlyGenderF.print();

        // Project

        System.out.println("Project id, name, and dept: ");
        Relation onlyIdNameDept = ra.project(rel, Arrays.asList("id", "name", "dept"));
        onlyIdNameDept.print();

        // Union


        System.out.println("Union: ");
        Relation relNew = rb.newRelation("relation2", Arrays.asList("id", "name", "gender", "dept"), Arrays.asList(Type.INTEGER, Type.STRING, Type.STRING, Type.STRING));
        relNew.insert(Arrays.asList(new Cell(3), new Cell("Ravaan"), new Cell("m"), new Cell("Mechanical") ));
        Relation relUnion = ra.union(rel, relNew);
        relNew.print();
        relUnion.print();

        // Rename

        System.out.println("Rename dept to department: ");
        Relation relRename = ra.rename(rel, rel.getAttrs(),  Arrays.asList("id", "name", "gender", "department"));
        relRename.print();

    }
    
}
