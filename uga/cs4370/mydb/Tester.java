package uga.cs4370.mydb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.lang.model.util.Types;

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

        System.out.println("Join: ");
        // Create two relations
        RelationBuilder rb10 = new RelationBuilderImpl();
        Relation rel1 = rb10.newRelation("Students", Arrays.asList("id", "name", "age"), Arrays.asList(Type.INTEGER, Type.STRING, Type.INTEGER));
        Relation rel2 = rb10.newRelation("Courses", Arrays.asList("id", "courseName"), Arrays.asList(Type.INTEGER, Type.STRING));

        // Insert some data into rel1 and rel2 with a common attribute
        List<Cell> row1a = Arrays.asList(new Cell(1), new Cell("Alice"), new Cell(20));
        List<Cell> row1b = Arrays.asList(new Cell(2), new Cell("Bob"), new Cell(21));
        List<Cell> row2a = Arrays.asList(new Cell(1), new Cell("Math"));
        List<Cell> row2b = Arrays.asList(new Cell(2), new Cell("History"));

        rel1.insert(row1a);
        rel1.insert(row1b);
        rel2.insert(row2a);
        rel2.insert(row2b);

        // Perform a natural join and print the result
        RAImpl rab = new RAImpl();
        Relation naturalJoined = rab.join(rel1, rel2);
        naturalJoined.print();

        // Theta Join

        System.out.println("Theta Join: ");

        // Create two relations
        RelationBuilder rbStudents = new RelationBuilderImpl();
        Relation relStudents = rbStudents.newRelation("Students", Arrays.asList("id", "name", "age"), Arrays.asList(Type.INTEGER, Type.STRING, Type.INTEGER));

        RelationBuilder rbCourses = new RelationBuilderImpl();
        Relation relCourses = rbCourses.newRelation("Courses", Arrays.asList("id", "courseName"), Arrays.asList(Type.INTEGER, Type.STRING));

        // Insert some data into relStudents and relCourses with a common attribute
        List<Cell> studentsR1 = Arrays.asList(new Cell(1), new Cell("Alice"), new Cell(20));
        List<Cell> studentsR2 = Arrays.asList(new Cell(3), new Cell("Bob"), new Cell(21)); 
        List<Cell> coursesR1 = Arrays.asList(new Cell(1), new Cell("Math"));
        List<Cell> coursesR2 = Arrays.asList(new Cell(2), new Cell("History")); 
        List<Cell> coursesR3 = Arrays.asList(new Cell(3), new Cell("Science")); 
        
        relStudents.insert(studentsR1);
        relStudents.insert(studentsR2);
        relCourses.insert(coursesR1);
        relCourses.insert(coursesR2);
        relCourses.insert(coursesR3);

        // Print data in relStudents and relCourses
        System.out.println("Data in relStudents:");
        relStudents.print();

        System.out.println("Data in relCourses:");
        relCourses.print();

        // Verify the predicate
        PredicateImpl predicateForThetaJoin = new PredicateImpl(0, 1, PredicateImpl.ComparisonOperator.EQUALS);

        // Perform a theta join using RAImpl and print the result
        RAImpl raThetaJoin = new RAImpl();
        Relation thetaJoined = raThetaJoin.join(relStudents, relCourses, predicateForThetaJoin);
        thetaJoined.print();

        
        RelationBuilder enrollment = new RelationBuilderImpl();
        Relation enrollmentRel = enrollment.newRelation("Enrollment", Arrays.asList("EnrollmentID", "StudentID", "CourseID", "Grade"), Arrays.asList(Type.INTEGER, Type.INTEGER, Type.INTEGER, Type.STRING));
        
        List<Cell> enrollmentRow = new ArrayList<Cell>();
        enrollmentRow.add(new Cell(1)); 
        enrollmentRow.add(new Cell(1234));
        enrollmentRow.add(new Cell(1000));
        enrollmentRow.add(new Cell("A")); 
        enrollmentRel.insert(enrollmentRow);
    
        List<Cell> enrollmentRow2 = new ArrayList<Cell>();
        enrollmentRow2.add(new Cell(2)); 
        enrollmentRow2.add(new Cell(1234));
        enrollmentRow2.add(new Cell(3000));
        enrollmentRow2.add(new Cell("F")); 
        enrollmentRel.insert(enrollmentRow2);
    
        List<Cell> enrollmentRow3 = new ArrayList<Cell>();
        enrollmentRow3.add(new Cell(3)); 
        enrollmentRow3.add(new Cell(1235));
        enrollmentRow3.add(new Cell(1000));
        enrollmentRow3.add(new Cell("C")); 
        enrollmentRel.insert(enrollmentRow3);
    
        /*
         * Courses Table
         */
        RelationBuilder courses = new RelationBuilderImpl();
        Relation coursesRel = courses.newRelation("Courses", Arrays.asList("CourseID", "CName", "Credits"), Arrays.asList(Type.INTEGER, Type.STRING, Type.INTEGER));

        List<Cell> coursesRow = new ArrayList<Cell>();
        coursesRow.add(new Cell(1000)); 
        coursesRow.add(new Cell("Programming Principles"));
        coursesRow.add(new Cell(4));
        coursesRel.insert(coursesRow);

        List<Cell> coursesRow2 = new ArrayList<Cell>();
        coursesRow2.add(new Cell(2000)); 
        coursesRow2.add(new Cell("Intermediate Programming"));
        coursesRow2.add(new Cell(4));
        coursesRel.insert(coursesRow2);

        List<Cell> coursesRow3 = new ArrayList<Cell>();
        coursesRow3.add(new Cell(3000)); 
        coursesRow3.add(new Cell("Data Structures"));
        coursesRow3.add(new Cell(4));
        coursesRel.insert(coursesRow3);

        List<Cell> coursesRow4 = new ArrayList<Cell>();
        coursesRow4.add(new Cell(4000)); 
        coursesRow4.add(new Cell("Algorithms"));
        coursesRow4.add(new Cell(4));
        coursesRel.insert(coursesRow4);

        RelationBuilder students = new RelationBuilderImpl();
        Relation studentsRel = students.newRelation("Students", Arrays.asList("StudentID", "FName", "LName", "DoB", "Major"), Arrays.asList(Type.INTEGER, Type.STRING, Type.STRING, Type.STRING, Type.STRING));

        List<Cell> studentsRow = new ArrayList<Cell>();
        studentsRow.add(new Cell(1234)); 
        studentsRow.add(new Cell("Lebron"));
        studentsRow.add(new Cell("James"));
        studentsRow.add(new Cell("2000-03-22"));
        studentsRow.add(new Cell("Computer Science"));
        studentsRel.insert(studentsRow);

        List<Cell> studentsRow2 = new ArrayList<Cell>();
        studentsRow2.add(new Cell(1235)); 
        studentsRow2.add(new Cell("Michael"));
        studentsRow2.add(new Cell("Jordan"));
        studentsRow2.add(new Cell("1998-06-15"));
        studentsRow2.add(new Cell("Computer Science"));
        studentsRel.insert(studentsRow2);

        List<Cell> studentsRow3 = new ArrayList<Cell>();
        studentsRow3.add(new Cell(1236)); 
        studentsRow3.add(new Cell("Chris"));
        studentsRow3.add(new Cell("Paul"));
        studentsRow3.add(new Cell("1999-01-29"));
        studentsRow3.add(new Cell("Finance"));
        studentsRel.insert(studentsRow3);

        /*
         * Query for question one
         */
        RAImpl queryOne = new RAImpl();
        Predicate queryOnePredicate = new PredicateImpl(1, 1234, PredicateImpl.ComparisonOperator.EQUALS);
    
        Relation queryOneResult = queryOne.select(enrollmentRel, queryOnePredicate);
        List<String> queryOneAttributes = Arrays.asList("CourseID");
        Relation queryOneFinalResult = queryOne.project(queryOneResult, queryOneAttributes);   
        System.out.println("Query 1: ");
        queryOneFinalResult.print();

        /*
         * Query for question two
         */
        Predicate queryTwoPredicate = new PredicateImpl(4, "Computer Science", PredicateImpl.ComparisonOperator.EQUALS);    
        Relation queryTwoResult = queryOne.select(studentsRel, queryTwoPredicate);
        List<String> queryTwoAttributes = Arrays.asList("StudentID", "FName", "LName");
        Relation queryTwoFinalResult = queryOne.project(queryTwoResult, queryTwoAttributes);
        System.out.println("Query 2: ");
        queryTwoFinalResult.print();

        /*
         * Query for question three
         */
        RAImpl ras = new RAImpl();
        Relation joinedTable = ras.join(enrollmentRel, coursesRel);
        PredicateImpl predicate2 = new PredicateImpl(joinedTable.getAttrIndex("StudentID"), 1234, PredicateImpl.ComparisonOperator.EQUALS);
        Relation filterForStudent = ras.select(joinedTable, predicate2);

        List<String> attrsToInclude = Arrays.asList("CName");
        Relation filteredResult = ra.project(filterForStudent, attrsToInclude);

        System.out.println("Query 3: ");
        filteredResult.print();

        /*
         * Query for question four
         */
        Predicate predicateFour = new PredicateImpl(2, 2, PredicateImpl.ComparisonOperator.GREATER_THAN);
        Relation filteredCourses = ra.select(coursesRel, predicateFour);

        Relation professorsTeachesJoin = ra.join(professorsRel, teachesRel);

        Relation queryFourJoin = ra.join(professorsTeachesJoin, filteredCourses);

        List<String> projectAttrs = Arrays.asList("ProfessorID", "FName", "LName");
        Relation queryFourResult = ra.project(queryFourJoin, projectAttrs);
        System.out.println("Query 4: ");
        queryFourResult.print();

        /*
         * Query for question five
         */

        // Project Student ID on Enrollment (Get student ids of students who are enrolled)
        System.out.println("Query 5: ");
        Relation enrollStudentIDrel = ra.project(enrollmentRel, Arrays.asList("StudentID"));

        // Project StudentID, FName, LName on Students (Get all student names and ids)
        Relation studentsNameRel = ra.project(studentsRel, Arrays.asList("StudentID", "FName", "LName"));

        // Workaround for natural join (Get students names and ids of students who are enrolled using join)
        Relation studentsEnrolledRel = ra.join(enrollStudentIDrel, studentsNameRel);
        studentsEnrolledRel = ra.rename(studentsEnrolledRel, studentsEnrolledRel.getAttrs(), Arrays.asList("StudentID0", "StudentID", "FName", "LName"));
        studentsEnrolledRel = ra.project(studentsEnrolledRel, Arrays.asList("StudentID", "FName", "LName"));

        // Get students that are not enrolled in any courses ( get Students in studentsNameRel but not studentsEnrolledRel)
        Relation studentsUnenrolledRel = ra.diff(studentsNameRel, studentsEnrolledRel);
        studentsUnenrolledRel.print();


         /*
          * Query for question six
          */         
          List<String> attrsToInclude2 = Arrays.asList("CourseID", "CName");
          Relation projectedCourses = ra.project(coursesRel, attrsToInclude2);

          Relation courseAndTeaches = ra.join(coursesRel, teachesRel);
          Relation projectedJoin = ra.project(courseAndTeaches, attrsToInclude2);

          Relation difference = ra.diff(projectedCourses, projectedJoin);
          System.out.println("Query 6: ");
          difference.print();

        
        /*
         * Query for question seven
         */

        PredicateImpl predicateForMajor = new PredicateImpl(studentsRel.getAttrIndex("Major"), "Computer Science", PredicateImpl.ComparisonOperator.EQUALS);
        PredicateImpl predicateForGrade = new PredicateImpl(enrollmentRel.getAttrIndex("Grade"), "F", PredicateImpl.ComparisonOperator.EQUALS);

        Relation filteredStudents = ra.select(studentsRel, predicateForMajor);
        Relation filteredEnrollments = ra.select(enrollmentRel, predicateForGrade);

        Relation gradeAndMajor = ra.join(filteredStudents, filteredEnrollments);

        List<String> attrsToIncludeFiltered = Arrays.asList("FName", "LName", "StudentID");
        Relation projectedResult = ra.project(gradeAndMajor, attrsToIncludeFiltered);

        System.out.println("Query 7: ");

        if (projectedResult != null) {
            projectedResult.print(); 
        } else {
            System.out.println("Query returned no results.");
        }

        /*
         * Query for question eight
         */
        // Teaches join on Teaches.ProfessorID = Professors.ProfessorID(Professors) = professorsTeachesJoin
		
		// Enrollment join on Enrollment.CourseId = AboveJoin.CourseID
		Relation enrollmentPTJoin = ra.join(enrollmentRel, professorsTeachesJoin);

        // Project StudentID, FName, LName, ProfessorID and Rename Attributes
		Relation enrollmentPTJoin2 = ra.project(enrollmentPTJoin, Arrays.asList("StudentID", "FName", "LName", "ProfessorID"));
        Relation enrollmentPTJoin3 = ra.rename(enrollmentPTJoin2, enrollmentPTJoin2.getAttrs(), Arrays.asList("StudentID", "ProfessorFName", "ProfessorLName", "ProfessorID"));

		// Students join on Students.StudentId = AboveProjection.StudentID
       
        Relation studentsEPTJoin = ra.join(studentsRel, enrollmentPTJoin3);
		
		// Major = Computer Science
        Predicate predMajorCS = new PredicateImpl(studentsEPTJoin.getAttrIndex("Major"), "Computer Science", PredicateImpl.ComparisonOperator.EQUALS);
		Relation onlyCSRel = ra.select(studentsEPTJoin, predMajorCS);

		// Project FName, LName, ProfessorID
		Relation queryEightResult = ra.project(onlyCSRel, Arrays.asList("ProfessorFName", "ProfessorLName", "ProfessorID"));
        System.out.println("Query 8: ");
		queryEightResult.print();


        System.out.println("\n--------------------------------------------\n");

         /**
         * Meaningful Query Implementation
         */


        // SELECT students named "Lebron"
        PredicateImpl selectLebronPredicate = new PredicateImpl(1, "Lebron", PredicateImpl.ComparisonOperator.EQUALS);
        Relation selectedLebronStudents = ra.select(studentsRel, selectLebronPredicate);
        System.out.println("Selected Students named Lebron:");
        selectedLebronStudents.print();

        // PROJECT to show only student IDs and names
        List<String> projectionAttrs = Arrays.asList("StudentID", "FName");
        Relation projectedStudents = ra.project(studentsRel, projectionAttrs);
        System.out.println("Projected Students (StudentID, FName):");
        projectedStudents.print();

        // UNION of coursesRel and coursesRel2 (assuming coursesRel2 exists)
        RelationBuilder courses2 = new RelationBuilderImpl();
        Relation coursesRel2 = courses2.newRelation("Courses2", Arrays.asList("CourseID", "CName", "Credits"), Arrays.asList(Type.INTEGER, Type.STRING, Type.INTEGER));
        List<Cell> coursesRow5 = new ArrayList<>();
        coursesRow5.add(new Cell(5000));
        coursesRow5.add(new Cell("AdvancedAlgorithms"));
        coursesRow5.add(new Cell(4));
        coursesRel2.insert(coursesRow5);

        Relation unionCourses = ra.union(coursesRel, coursesRel2);
        System.out.println("Union of Courses:");
        unionCourses.print();

        // DIFFERENCE of coursesRel and coursesRel2
        Relation diffCourses = ra.diff(coursesRel, coursesRel2);
        System.out.println("Difference of Courses:");
        diffCourses.print();

        // RENAME attributes of studentsRel
        List<String> origAttr = Arrays.asList("StudentID", "FName", "LName", "DoB", "Major");
        List<String> renamedAttr = Arrays.asList("StudentID", "FirstName", "LastName", "DateOfBirth", "Major");
        Relation renamedStudents = ra.rename(studentsRel, origAttr, renamedAttr);
        System.out.println("Renamed Students Relation:");
        renamedStudents.print();

        // CARTESIAN PRODUCT of studentsRel and coursesRel
        Relation cartesianProductResult = ra.cartesianProduct(studentsRel, coursesRel);
        System.out.println("Cartesian Product of Students and Courses:");
        cartesianProductResult.print();

    }
}

