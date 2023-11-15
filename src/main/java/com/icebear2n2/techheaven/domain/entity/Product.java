package com.icebear2n2.techheaven.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "product")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    private String productName;
    private Integer productPrice;
    private Integer discountPrice;
    private Timestamp saleStartDate;
    private Timestamp saleEndDate;
    @CreationTimestamp
    private Timestamp createdAt;
    @UpdateTimestamp
    private Timestamp updatedAt;

    @OneToMany(mappedBy = "product")
    @JsonManagedReference
    private List<ProductDetail> productDetails;

//    @OneToMany(mappedBy = "product")
//    private List<CartItem> cartItems;


    public void setCategory(Category category) {
        this.category = category;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductPrice(Integer productPrice) {
        this.productPrice = productPrice;
    }

    public void setDiscountPrice(Integer discountPrice) {
        this.discountPrice = discountPrice;
    }

    public void setSaleStartDate(Timestamp saleStartDate) {
        this.saleStartDate = saleStartDate;
    }

    public void setSaleEndDate(Timestamp saleEndDate) {
        this.saleEndDate = saleEndDate;
    }
}
