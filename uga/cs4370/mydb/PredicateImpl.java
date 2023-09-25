package uga.cs4370.mydb;

import java.util.List;

public class PredicateImpl implements Predicate {

    public enum ComparisonOperator {
        EQUALS,
        DOES_NOT_EQUALS,
        GREATER_THAN,
        LESS_THAN,
        GREATER_THAN_OR_EQUAL_TO,
        LESS_THAN_OR_EQUAL_TO
    }

    private int index;
    private Object target;
    private ComparisonOperator operator;

    PredicateImpl(int index, Object target, ComparisonOperator operator) {
        this.index = index;
        this.target = target;
        this.operator = operator;
    }

    @Override
    public boolean check(List<Cell> row) {
        int numRows = row.size();

        if (index < 0 || index >= numRows) {
            throw new IllegalArgumentException("Invalid index");
        }

        Cell cell = row.get(index);

        if (cell.getType() == Type.STRING) {
            switch (operator) {
                case EQUALS:
                    return cell.toString().equals(target.toString());
                case DOES_NOT_EQUALS:
                    return !cell.toString().equals(target.toString());
            }
        } else if (cell.getType() == Type.INTEGER || cell.getType() == Type.DOUBLE) {
            double cellNumber = cell.getAsDouble();
            double targetNumber = Double.parseDouble(target.toString());
            switch (operator) {
                case EQUALS:
                    return cellNumber == targetNumber;
                case DOES_NOT_EQUALS:
                    return cellNumber != targetNumber;
                case GREATER_THAN:
                    return cellNumber > targetNumber;
                case LESS_THAN:
                    return cellNumber < targetNumber;
                case GREATER_THAN_OR_EQUAL_TO:
                    return cellNumber >= targetNumber;
                case LESS_THAN_OR_EQUAL_TO:
                    return cellNumber <= targetNumber;
            }
        }
        throw new IllegalArgumentException("Unsupported data type in the cell.");
    }
}
