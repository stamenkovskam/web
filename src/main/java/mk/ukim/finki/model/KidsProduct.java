package mk.ukim.finki.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class KidsProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double price;

    private Integer quantity;

    @ManyToOne
    private Category category;

    private String photo;

    public KidsProduct() {
    }

    public KidsProduct(String name, Double price, Integer quantity, Category category, String photo) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.photo = photo;
    }
}
