package com.ond.ond.models.dto;

import java.util.List;

import com.ond.ond.enums.ItemType;
import com.ond.ond.models.entities.Item;
import com.ond.ond.models.entities.User;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {

    private Long id;
	private String name;
	private User owner;
	private ItemType kind;
	private Item parent;
	// private List<Item> items;

	public ItemDTO(Item entity) {
		id = entity.getId();
		name = entity.getName();
		owner = entity.getOwner();
		kind = entity.getKind();
		parent = entity.getParent();
		// items = entity.getItems();
	}
    
}
