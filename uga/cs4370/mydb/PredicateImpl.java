package uga.cs4370.mydb;

import uga.cs4370.mydb.Cell;
import uga.cs4370.mydb.Predicate;

import java.util.List;

public class PredicateImpl implements Predicate {

	private int index; 
	private Object target;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Object getTarget() {
		return target;
	}

	public void setTarget(Object target) {
		this.target = target;
	}
 
	@Override
	public boolean check(List<Cell> row) {

		int numberRows = row.size();
		
		if(index < 0 || index >= numberRows) {
			throw new IllegalArgumentException("Invalid index");
		}

		Cell cell = row.get(index);
		
        return cell.getAsString().equals(target.toString());
	}
	
}
