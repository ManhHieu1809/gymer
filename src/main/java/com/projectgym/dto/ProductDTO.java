package com.projectgym.dto;

import com.projectgym.Entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ProductDTO {
    private int productID;
    private String productName;
    private String descriptions;
    private double price;
    private int quantity;
    private String imageUrl;

    public ProductDTO(Product product) {
        this.productID = product.getProductID();
        this.productName = product.getProductName();
        this.descriptions = product.getDescriptions();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
        this.imageUrl = product.getImageUrl();
    }


}
