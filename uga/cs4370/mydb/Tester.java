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

        // Diff

        System.out.println("Diff: ");

        Relation rel3 = rb.newRelation("relation3", Arrays.asList("id", "name", "gender", "dept"), Arrays.asList(Type.INTEGER, Type.STRING, Type.STRING, Type.STRING));
        rel3.insert(Arrays.asList(new Cell(1), new Cell("Ram"), new Cell("m"), new Cell("Information Technology") ));
        rel.print();
        rel3.print();
        Relation relDiff = ra.diff(rel, rel3);
        relDiff.print();

        // Cartesian Product

        System.out.println("Cartesian Product: ");
        Relation relInstructor = rb.newRelation("Instructor", Arrays.asList("id", "name", "deptName"), Arrays.asList(Type.INTEGER, Type.STRING, Type.STRING));
        relInstructor.insert(Arrays.asList(new Cell(22222), new Cell("Einstein"), new Cell("Physics")));
        relInstructor.insert(Arrays.asList(new Cell(12121), new Cell("Wu"), new Cell("Finance")));

        Relation relTeaches = rb.newRelation("Teaches", Arrays.asList("id", "courseId", "semester"), Arrays.asList(Type.INTEGER, Type.STRING, Type.STRING));
        relTeaches.insert(Arrays.asList(new Cell(22222), new Cell("PHY-101"), new Cell("Fall")));
        relTeaches.insert(Arrays.asList(new Cell(12121), new Cell("FIN-201"), new Cell("Spring")));

        Relation relCarP = ra.cartesianProduct(relInstructor, relTeaches);
        System.out.println(relCarP.getName());
        relCarP.print();

        ///////////////////////////
        

        RelationBuilder professors = new RelationBuilderImpl();
        Relation professorsRel = professors.newRelation("Professors", Arrays.asList("ProfessorID", "FName", "LName", "Department"), Arrays.asList(Type.INTEGER, Type.STRING, Type.STRING, Type.STRING));


        List<Cell> professorsRow = new ArrayList<Cell>();
        professorsRow.add(new Cell(4321)); 
        professorsRow.add(new Cell("Robert"));
        professorsRow.add(new Cell("Jones"));
        professorsRow.add(new Cell("Computer Science")); 
        professorsRel.insert(professorsRow);

        List<Cell> professorsRow2 = new ArrayList<Cell>();
        professorsRow2.add(new Cell(4322)); 
        professorsRow2.add(new Cell("Samantha"));
        professorsRow2.add(new Cell("Hall"));
        professorsRow2.add(new Cell("Mathematics")); 
        professorsRel.insert(professorsRow2);

        List<Cell> professorsRow3 = new ArrayList<Cell>();
        professorsRow3.add(new Cell(4323)); 
        professorsRow3.add(new Cell("Vietmy"));
        professorsRow3.add(new Cell("Vo"));
        professorsRow3.add(new Cell("Computer Science")); 
        professorsRel.insert(professorsRow3);

        professorsRel.print();

        PredicateImpl predicate;
        predicate = new PredicateImpl(0, 4321, PredicateImpl.ComparisonOperator.EQUALS);
        System.out.println(predicate.check(professorsRow));

        RelationBuilder teaches = new RelationBuilderImpl();
        Relation teachesRel = teaches.newRelation("Teaches", Arrays.asList("TeachID", "ProfessorID", "CourseID"), Arrays.asList(Type.INTEGER, Type.INTEGER, Type.INTEGER));

        List<Cell> teachesRow = new ArrayList<Cell>();
        teachesRow.add(new Cell(1)); 
        teachesRow.add(new Cell(4321));
        teachesRow.add(new Cell(1000));
        teachesRel.insert(teachesRow);

        List<Cell> teachesRow2 = new ArrayList<Cell>();
        teachesRow2.add(new Cell(2)); 
        teachesRow2.add(new Cell(4323));
        teachesRow2.add(new Cell(3000));
        teachesRel.insert(teachesRow2);

        List<Cell> teachesRow3 = new ArrayList<Cell>();
        teachesRow3.add(new Cell(3)); 
        teachesRow3.add(new Cell(4322));
        teachesRow3.add(new Cell(4000));
        teachesRel.insert(teachesRow3);

        teachesRel.print();


    }
    
}
