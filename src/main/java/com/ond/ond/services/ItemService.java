package com.ond.ond.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import com.ond.ond.models.dto.ItemDTO;
import com.ond.ond.models.dto.UserDTO;
import com.ond.ond.models.entities.Item;
import com.ond.ond.models.entities.User;
import com.ond.ond.repositories.ItemRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ItemService {

    @Autowired
	private ItemRepository itemRepository;

    @Transactional(readOnly = true)
	public List<ItemDTO> findAll() {
		List<Item> result = itemRepository.findAll();
		return result.stream().map(ItemDTO::new).toList();
	}

    @Transactional(readOnly = true)
	public ItemDTO findById(@PathVariable Long id) {
		Item result = itemRepository.findById(id).get();
		return new ItemDTO(result);
	}
}
