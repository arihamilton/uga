package uga.cs4370.mydb;

import java.util.ArrayList;
import java.util.List;

import uga.cs4370.mydb.Relation;
import uga.cs4370.mydb.RelationImpl;
import uga.cs4370.mydb.Type;

public class Tester {

    public static void main(String[] args) {
        
        RelationImpl rel = new RelationImpl();

        // RelationBuilderImpl is not implemented yet, so that is why manual setters are used
        // Do not use manual setters in actual project

        rel.setName("relation1");
        List<String> attrs = new ArrayList<String>();
        attrs.add("id");
        attrs.add("name");
        attrs.add("gender");
        attrs.add("dept");
        rel.setAttrs(attrs);

        List<Type> types = new ArrayList<Type>();
        types.add(Type.INTEGER);
        types.add(Type.STRING);
        types.add(Type.STRING);
        types.add(Type.STRING);
        rel.setTypes(types);

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


    }
    
}