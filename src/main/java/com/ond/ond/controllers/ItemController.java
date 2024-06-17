package com.ond.ond.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ond.ond.enums.ItemType;
import com.ond.ond.models.dto.ItemDTO;
import com.ond.ond.models.dto.UserDTO;
import com.ond.ond.models.entities.Item;
import com.ond.ond.models.entities.User;
import com.ond.ond.repositories.ItemRepository;
import com.ond.ond.repositories.UserRepository;
import com.ond.ond.services.ItemService;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.List;

import org.modelmapper.ModelMapper;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users/{userId}/items")
public class ItemController{
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserRepository userRepository;
    private ModelMapper modelMapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<ItemDTO>> getItems(@PathVariable Long userId) throws Exception {
        User user = findUser(userId);
        List<Item> items;
        items = itemRepository.findAllByOwner(user);
        // if (items.isEmpty()) throw new Exception("Nenhum encontrado");
        return ResponseEntity.status(HttpStatus.OK).body(items.stream().map(item -> modelMapper.map(item, ItemDTO.class)).toList());
    }

    @GetMapping("/{parent}/items")
    public ResponseEntity<List<ItemDTO>> getParents(@PathVariable Long userId, @PathVariable Long parent) throws Exception {
        User user = findUser(userId);
        List<Item> items;
        items = itemRepository.findByParentIdAndOwner(parent, user);
        // if (items.isEmpty()) throw new Exception("Nenhum encontrado");
        return ResponseEntity.status(HttpStatus.OK).body(items.stream().map(item -> modelMapper.map(item, ItemDTO.class)).toList());
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<ItemDTO>> getParents(@PathVariable Long userId) throws Exception {
        User user = findUser(userId);
        List<Item> items;
        items = itemRepository.findByKindAndOwner(ItemType.ROOM, user);
        // if (items.isEmpty()) throw new Exception("Nenhum encontrado");
        return ResponseEntity.status(HttpStatus.OK).body(items.stream().map(item -> modelMapper.map(item, ItemDTO.class)).toList());
    }
    @GetMapping("/rooms/bases")
    public ResponseEntity<List<ItemDTO>> getBaseParents(@PathVariable Long userId) throws Exception {
        User user = findUser(userId);
        List<Item> items;
        items = itemRepository.findByKindAndParentIdAndOwner(ItemType.ROOM, null, user);
        // if (items.isEmpty()) throw new Exception("Nenhum encontrado");
        return ResponseEntity.status(HttpStatus.OK).body(items.stream().map(item -> modelMapper.map(item, ItemDTO.class)).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> getItem(@PathVariable Long userId, @PathVariable Long id) throws Exception {
        User user = findUser(userId);
        Item item;
        item = itemRepository.findByIdAndOwner(id, user).orElseThrow(() -> new Exception("Nenhum item encontrado"));
        ItemDTO itemDTO = modelMapper.map(item, ItemDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(itemDTO);
    }

    @GetMapping("/search/{term}")
    public ResponseEntity<List<ItemDTO>> searchTerm(@PathVariable Long userId, @PathVariable String term) throws Exception {
        User user = findUser(userId);
        List<Item> items;
        items = itemRepository.findByNameContainsIgnoreCaseAndOwner(term, user);
        // if (items.isEmpty()) throw new Exception("Nenhum encontrado");
        return ResponseEntity.status(HttpStatus.OK).body(items.stream().map(item -> modelMapper.map(item, ItemDTO.class)).toList());
    }

    @CrossOrigin(origins = "*")
    @PostMapping
    public ResponseEntity<ItemDTO> registerItem(@PathVariable Long userId, @RequestBody Item item) throws Exception {
        User user = findUser(userId);
        item.setOwner(user);

        if(item.getParent() == null){
            item.setKind(ItemType.ROOM);
        }else{
            item.getParent().setOwner(user);
        }
        
        if(item.getId() != null && itemRepository.findByParentIdAndOwner(item.getId(), user).size() > 0){
            item.setKind(ItemType.ROOM);
        }

        itemRepository.save(item);
        ItemDTO itemDTO = modelMapper.map(item, ItemDTO.class);

        return ResponseEntity.status(HttpStatus.OK).body(itemDTO);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteItem(@PathVariable Long userId, @RequestBody Item item) throws Exception {
        User user = findUser(userId);
        item.setOwner(user);

        if(item.getParent() != null){
            item.getParent().setOwner(user);
        }

        boolean result = false;

        itemRepository.delete(item);
        result = true;

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    public User findUser(Long userId) throws Exception{
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("Não permitido"));

        return user;
    }
    
}
