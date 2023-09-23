package uga.cs4370;

import java.util.List;

import uga.cs4370.mydb.Relation;
import uga.cs4370.mydb.RelationImpl;
import uga.cs4370.mydb.RelationBuilder;
import uga.cs4370.mydb.Type;

public class RelationBuilderImpl implements RelationBuilder {

    /**
     * Creates an empty relation with attribute names attrs and
     * attribute types types.
     * 
     * @throws IllegalArgumentException if attrs and types have different counts
     * or attrs have empty or non-alphanumeric attribute names.
     */
    public Relation newRelation(String name, List<String> attrs, List<Type> types) {
        Relation rel = new RelationImpl();
        return rel;
    }
    
}
