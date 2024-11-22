package com.projectgym.service.Impl;

import com.projectgym.Entity.Product;
import com.projectgym.dto.ProductDTO;
import com.projectgym.repository.ProductRepository;
import com.projectgym.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository repository;

    @Override
    public List<ProductDTO> getAllProduct() {
        List<Product> products = repository.findAll();
        return products.stream()
                .map(product -> new ProductDTO(
                        product.getProductID(),
                        product.getProductName(),
                        product.getDescriptions(),
                        product.getPrice(),
                        product.getQuantity()))
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        return new ProductDTO(
                product.getProductID(),
                product.getProductName(),
                product.getDescriptions(),
                product.getPrice(),
                product.getQuantity());
    }

    @Override
    public ProductDTO createProduct(Product product) {
        Product savedProduct = repository.save(product);
        return new ProductDTO(
                savedProduct.getProductID(),
                savedProduct.getProductName(),
                savedProduct.getDescriptions(),
                savedProduct.getPrice(),
                savedProduct.getQuantity());
    }

    @Override
    public ProductDTO updateProduct(Long id, Product product) {
        Product existingProduct = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        existingProduct.setProductName(product.getProductName());
        existingProduct.setDescriptions(product.getDescriptions());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setQuantity(product.getQuantity());

        Product updatedProduct = repository.save(existingProduct);

        return new ProductDTO(
                updatedProduct.getProductID(),
                updatedProduct.getProductName(),
                updatedProduct.getDescriptions(),
                updatedProduct.getPrice(),
                updatedProduct.getQuantity());
    }

    @Override
    public void deleteProduct(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<ProductDTO> getLowStockProducts(int threshold) {
        return repository.findAll().stream()
                .filter(product -> product.getQuantity() <= threshold)
                .map(product -> new ProductDTO(
                        product.getProductID(),
                        product.getProductName(),
                        product.getDescriptions(),
                        product.getPrice(),
                        product.getQuantity()
                ))
                .collect(Collectors.toList());
    }
}
