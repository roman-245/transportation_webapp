package com.example.transportation;

import java.util.List;

import com.example.transportation.Repo.AutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransporationService {
    @Autowired
    private AutoRepository repo;

    public void save(TransporationORM book) {
        repo.save(book);
    }

    public TransporationORM get(Long id) {
        return repo.findById(id).get();
    }

    public List<TransporationORM> listAll(String keyword) {
        if (keyword != null) {
            return repo.search(keyword);
        }
        return repo.findAll();
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
