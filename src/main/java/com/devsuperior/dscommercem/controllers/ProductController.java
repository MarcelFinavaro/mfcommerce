package com.devsuperior.dscommercem.controllers;

import com.devsuperior.dscommercem.dto.ProductDTO;
import com.devsuperior.dscommercem.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping (value = "/products")
public class ProductController {

   @Autowired
   private ProductService service;

    @GetMapping(value = "/{id}")
    public ProductDTO findById (@PathVariable Long id){
      return service.findById(id);
    }

    @GetMapping
    public Page<ProductDTO> findAll (Pageable pageable){
        return service.findALL(pageable);
    }

    @PostMapping
    public ProductDTO insert(@RequestBody ProductDTO dto) {
        return service.insert(dto);

    }

}
