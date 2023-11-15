package com.icebear2n2.techheaven.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "product_detail")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetail {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productDetailId;
    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;
    @ElementCollection
    @CollectionTable(name = "product_colors", joinColumns = @JoinColumn(name = "product_detail_id"))
    @Column(name = "color")
    private List<String> productColors;

    @ElementCollection
    @CollectionTable(name = "product_sizes", joinColumns = @JoinColumn(name = "product_detail_id"))
    @Column(name = "size")
    private List<String> productSizes;

    @ElementCollection
    @CollectionTable(name = "stock_quantities", joinColumns = @JoinColumn(name = "product_detail_id"))
    @Column(name = "stock_quantity")
    private List<Integer> stockQuantity;
    @CreationTimestamp
    private Timestamp createdAt;
    @UpdateTimestamp
    private Timestamp updatedAt;

    public void setProduct(Product product) {
        this.product = product;
    }

}
