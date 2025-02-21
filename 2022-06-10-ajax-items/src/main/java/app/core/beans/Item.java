package app.core.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Item {
	
	private int id;
	private String name;
	private double price;

}
