package com.eatza.order.repository;

import com.eatza.order.model.OrderedItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface OrderedItemRepository extends JpaRepository<OrderedItem, Long> {

    @Query("FROM OrderedItem WHERE order_id = ?1")
    List<OrderedItem> findbyOrderId(Long id);

    @Transactional
    void deleteByOrder_id(Long orderId);

}
