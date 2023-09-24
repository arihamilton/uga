package uga.cs4370.mydb;

import java.util.List;

public class RelationBuilderImpl implements RelationBuilder {

    /**
     * Creates an empty relation with attribute names attrs and
     * attribute types types.
     * 
     * @throws IllegalArgumentException if attrs and types have different counts
     * or attrs have empty or non-alphanumeric attribute names.
     */
    public Relation newRelation(String name, List<String> attrs, List<Type> types) {

        // Check if attrs and types have different counts
        if (attrs.size() != types.size()) {
            throw new IllegalArgumentException("Attributes and types have different counts.");
        }
        // Check if attrs have empty or non-alphanumeric attribute names
        for (String attr : attrs ) {
            // Check if attrs have empty attribute names
            if (attr.length() == 0) {
                throw new IllegalArgumentException("Attribute name is empty.");
            }
            // Check if attrs have non-alphanumeric attribute names
            char[] charArray = attr.toCharArray();
                for(char c : charArray) {
                    if (!Character.isLetterOrDigit(c)){
                        throw new IllegalArgumentException("Attribute name contains non-alphanumeric characters.");
                    }
            }
        }

        // If relation is valid, continue
        RelationImpl rel = new RelationImpl();
        rel.setName(name);
        rel.setAttrs(attrs);
        rel.setTypes(types);
        return rel;
    }
    
}
