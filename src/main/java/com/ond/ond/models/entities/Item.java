package com.ond.ond.models.entities;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.ond.ond.enums.ItemType;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "items")

public class Item {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = false)
    private User owner;

    @Enumerated(EnumType.STRING)
    @Column(name = "kind", nullable = true)
    private ItemType kind;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@ItemRoom")
    @JoinColumn(name = "parent_id", nullable = true)
    private Item parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Item> items;
}
