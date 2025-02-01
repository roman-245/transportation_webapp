package com.example.transportation.Config;


import com.example.transportation.BlogORM;
import com.example.transportation.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
public class BlogController {

    @Autowired
    private BlogService blogService;

//    public BlogController(BlogService blogService) {
//        this.blogService = blogService;
//    }

    @RequestMapping("/blog_home")
    public String showBlogPage(Model model, @Param("keyword") String keyword) {
        List<BlogORM> listArticle = blogService.listAll(keyword);
        model.addAttribute("listArticle", listArticle);
        model.addAttribute("keyword", keyword);
        return "blog_home";
    }

    @RequestMapping("/blog_admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String showBlogAdminPage(Model model, @Param("keyword") String keyword) {
        List<BlogORM> listArticle = blogService.listAll(keyword);
        model.addAttribute("listArticle", listArticle);
        model.addAttribute("keyword", keyword);
        return "blog_admin";
    }

    @RequestMapping("/blog_admin/new")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String newArticlePage(Model model) {
        BlogORM article = new BlogORM();
        model.addAttribute("article", article);
        return "new_article";
    }

//    @RequestMapping(value = "/blog_admin/save", method = RequestMethod.POST)
//    @PreAuthorize("hasAuthority('ADMIN')")
//    public String saveArticle(@ModelAttribute("article") BlogORM article) {
//        blogService.save(article);
//        return "redirect:/blog_admin";
//    }

    @RequestMapping(value = "/blog_admin/save", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('ADMIN')")
    public String saveArticle(
            @ModelAttribute("article") BlogORM article,
            @RequestParam("imageFile") MultipartFile file
    ) {
        try {
            // Проверяем, был ли файл загружен
            if (!file.isEmpty()) {
                // Определяем директорию для загрузки (например, uploads)
                Path uploadPath = Path.of("src/main/resources/static/images/articles_images");
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath); // Создаём папку, если она отсутствует
                }

                // Генерируем уникальное имя для файла
                String uniqueFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

                // Сохраняем файл в файловую систему
                Path destination = uploadPath.resolve(uniqueFileName);
                Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

                // Устанавливаем имя файла в объект статьи
                article.setImage(uniqueFileName);
            } else {
                // Если фото не загружено, устанавливаем значение null (или оставляем старое значение)
                article.setImage(null);
            }

            // Сохраняем статью в базу
            blogService.save(article);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Ошибка сохранения изображения", e);
        }

        return "redirect:/blog_admin";
    }


    @RequestMapping("blog_admin/edit/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView showEditArticlePage(@PathVariable(name = "id") Long id) {
        ModelAndView mav = new ModelAndView("edit_article");
        BlogORM article = blogService.getById(id);
        mav.addObject("article", article);
        return mav;
    }

    @RequestMapping("blog_admin/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteArticle(@PathVariable(name = "id") Long id) {
        blogService.delete(id);
        return "redirect:/blog_admin";
    }

    @RequestMapping("/blog_full_article/show/{id}")
    public ModelAndView showFullArticle(@PathVariable(name = "id") Long id) {
        ModelAndView mav = new ModelAndView("blog_full_article");
        BlogORM article = blogService.getById(id);
        mav.addObject("article", article);
        return mav;
    }


}
