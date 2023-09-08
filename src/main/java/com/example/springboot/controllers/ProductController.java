package com.example.springboot.controllers;

import com.example.springboot.dtos.ProductRecordDTO;
import com.example.springboot.models.ProductModel;
import com.example.springboot.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    ProductRepository productRepository; //ponto de injeção para ter acesso aos métodos  JPA

    @PostMapping()
    public ResponseEntity<ProductModel> saveProduct(
            @RequestBody @Valid ProductRecordDTO productRecordDTO) {

        var productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordDTO, productModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
    }

    @GetMapping()
    public ResponseEntity<List<ProductModel>> getAllProduct() {
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ProductModel>> getProductById(@PathVariable(value = "id") UUID idProduct) {
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.findById(idProduct));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProductById(@PathVariable(value = "id") UUID idProduct,
                                                    @RequestBody @Valid ProductRecordDTO productRecordDTO) {

        Optional<ProductModel> productModel = productRepository.findById(idProduct);

        if(productModel.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        var product = productModel.get();
        BeanUtils.copyProperties(productRecordDTO, product);

        return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(product));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProductById(@PathVariable(value = "id") UUID idProduct) {

        Optional<ProductModel> productModel = productRepository.findById(idProduct);

        if(productModel.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        productRepository.delete(productModel.get());

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

