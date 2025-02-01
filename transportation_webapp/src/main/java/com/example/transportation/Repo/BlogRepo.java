package com.example.transportation.Repo;

import com.example.transportation.BlogORM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepo extends JpaRepository<BlogORM, Long> {

    @Query("SELECT p FROM BlogORM p WHERE CONCAT(p.id, ' ', p.title, ' ', p.maintext, ' ', p.author, ' ', p.publishDate, ' ', p.image) LIKE %:keyword% ")
    List<BlogORM> search(String keyword);
}
