package com.example.transportation;

import com.example.transportation.Repo.BlogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {

    @Autowired
    private BlogRepo repo;

    public void save(BlogORM blog) {repo.save(blog);}

    public BlogORM getById(Long id) {return repo.findById(id).get();}

    public List<BlogORM> listAll(String keyword) {
        if (keyword != null) {
            return repo.search(keyword);
        }
        return repo.findAll();
    }

    public void delete(Long id) {repo.deleteById(id);}
}
