package com.eatza.order.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;


@Entity
@Table(name = "ordered_items")
@Getter
@Setter
@NoArgsConstructor
public class OrderedItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int quantity;
    private double price;
    private Long itemId;
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Order order;


    public OrderedItem(String name, int quantity, double price, Order order, Long itemId) {
        super();
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.order = order;
        this.itemId = itemId;
    }


}
