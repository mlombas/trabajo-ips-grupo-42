package util.csv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CSVTable<Head, Value> implements Iterable<List<Value>> {
	private Map<Head, List<Value>> table;
	private List<Head> heads;
	
	public CSVTable(List<Head> heads) {
		this.heads = heads;
		
		table = new HashMap<>();
		for(Head head : heads) {
			table.put(head, new ArrayList<>());
		}
	}
	
	public void addHead(Head head) {
		if(table.containsKey(head))
			throw new IllegalArgumentException("Head already exists");
		
		table.put(head, new ArrayList<>());
	}
	
	public void addValue(Head head, Value value) {
		if(!table.containsKey(head))
			throw new IllegalArgumentException("Head does not exists");

		table.get(head).add(value);
	}
	
	public void addRow(List<Value> values) {
		if(values.size() != heads.size()) 
			throw new IllegalArgumentException("Number of values doesnt match number of heads");
		
		Iterator<Head> hit = heads.iterator();
		Iterator<Value> vit = values.iterator();
		while(hit.hasNext() && vit.hasNext()) {
			table.get(hit.next()).add(vit.next());
		}
	}
	
	public Set<Head> getHeads() {
		return table.keySet();
	}
	
	public List<Value> getColumn(Head head) {
		return table.get(head);
	}
	
	public int nRows() {
		return table.get(heads.get(0)).size();
	}

	@Override
	public Iterator<List<Value>> iterator() {
		return new CSVIterator(this);
	}
	
	private class CSVIterator implements Iterator<List<Value>> {
		private int index = 0;
		private List<List<Value>> lists;
		private int max;

		private CSVIterator(CSVTable<Head, Value> table) {
			this.max = table.nRows();
			
			this.lists = table.heads.stream()
				.map(head -> table.table.get(head))
				.collect(Collectors.toList());
		}
		
		@Override
		public boolean hasNext() {
			return index < max;
		}

		@Override
		public List<Value> next() {
			List<Value> res = new ArrayList<>();
			
			for(List<Value> list : lists)
				res.add(list.get(index));
			
			index++;
			
			return res;
		}
		
	}

	public int getColumnIndex(Head head) {
		return heads.indexOf(head);
	}

}
