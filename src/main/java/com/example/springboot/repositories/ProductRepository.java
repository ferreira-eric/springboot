package com.example.springboot.repositories;

import com.example.springboot.models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository; //Habilitar metodos prontos, salvar no banco etc..
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, UUID> { //Passar a entidade (ProductModel) e o indentificador UUID

}
