package com.ond.ond.repositories;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ond.ond.enums.ItemType;
import com.ond.ond.models.entities.Item;
import com.ond.ond.models.entities.User;

public interface ItemRepository extends JpaRepository<Item, Long>{
    List<Item> findAllByOwner(User owner);
    Optional<Item> findByIdAndOwner(Long id, User owner);
    Optional<Item> getItemByIdAndOwner(Long id, User owner);
    Optional<Item> findByNameAndOwner(String name, User owner);
    List<Item> findByNameContainsIgnoreCaseAndOwner(String name, User owner);
    Optional<Item> findByOwner(User owner);
    List<Item> findByKindAndOwner(ItemType kind, User owner);
    List<Item> findByKindAndParentIdAndOwner(ItemType kind, Integer parentId, User owner);
    List<Item> findByParentIdAndOwner(Long parent, User owner);

}
