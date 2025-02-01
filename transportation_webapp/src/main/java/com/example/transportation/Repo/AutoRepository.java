package com.example.transportation.Repo;

import com.example.transportation.TransporationORM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface AutoRepository extends JpaRepository<TransporationORM, Long> {
    @Query("SELECT p FROM TransporationORM p WHERE CONCAT(p.name, ' ', p.content, ' ', p.dispatch_city, ' ', p.dispatch_date, ' ', p.arrival_city, ' ', p.arrival_date) LIKE %:keyword%")
    List<TransporationORM> search(String keyword);
}
