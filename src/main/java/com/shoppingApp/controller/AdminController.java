package com.shoppingApp.controller;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.shoppingApp.model.Category;
import com.shoppingApp.services.CategoryService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {


    private CategoryService categoryService;

    

    public AdminController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String index(){
        return "admin/index";
    }
    
    @GetMapping("/loadAddProduct")
    public String loadAddProduct(){
        return "admin/add_product";
    }
    
    @GetMapping("/category")
    public String category(){
        return "admin/category";
    }

    @PostMapping("/saveCategory")
    public String saveCategory(@ModelAttribute Category category,@RequestParam("file") MultipartFile file, HttpSession session) throws Exception{

       String imageName = file != null ? file.getOriginalFilename() : "default.jpg";
       category.setImageName(imageName);

        Boolean existsCategory = categoryService.existsCategory(category.getName());

        if (existsCategory) {
            session.setAttribute("errorMessage", "Category name already exist !!");
           
        }else{
            Category saveCategory = categoryService.saveCategory(category);

            if (ObjectUtils.isEmpty(saveCategory)) {
                session.setAttribute("errorMessage", "Not saved ! Internal server error");
              
            }else{
                 File saveFile;
                    saveFile = new ClassPathResource("static/img").getFile();
                    Path path = Paths.get(saveFile.getAbsolutePath()+File.separator+"category_img"+File.separator+file.getOriginalFilename());
                    System.out.println(path);
    
                    Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);


                session.setAttribute("successMessage", "Save Record Successfully !!");
            }
        }

        return "redirect:/admin/category";
    }
}
