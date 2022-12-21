package com.codecool.shop.service;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.util.List;
import java.util.Optional;

public class ProductService{
    private final ProductDao productDao;


    public ProductService(ProductDao productDao) {
        this.productDao = productDao;

    }

    public Optional<ProductCategory> getProductCategory(int categoryId){
        return productDao.getAll().stream()
                .filter(product -> product.getProductCategory().getId() == categoryId)
                .map(Product::getProductCategory)
                .findFirst();

    }

    public List<Product> getProductsForCategory(int categoryId){
        return productDao.getAll().stream()
                .filter(product -> product.getProductCategory().getId() == categoryId)
                .toList();
    }

    public  List<ProductCategory> getAllCategories(){
        return productDao.getAll().stream()
                .map(Product::getProductCategory)
                .distinct()
                .toList();

    }

    public Optional<Supplier> getProductSupplier(int supplierId){
        return productDao.getAll().stream()
                .filter(product -> product.getSupplier().getId() == supplierId)
                .map(Product::getSupplier)
                .findFirst();
    }

    public List<Product> getProductsForSupplier(int supplierId){
        return productDao.getAll().stream()
                .filter(product -> product.getSupplier().getId() == supplierId)
                .toList();
    }

    public  List<Supplier> getAllSupplier(){
        return productDao.getAll().stream()
                .map(Product::getSupplier)
                .distinct()
                .toList();
    }
}
