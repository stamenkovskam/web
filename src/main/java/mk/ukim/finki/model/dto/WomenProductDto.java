package mk.ukim.finki.model.dto;

import lombok.Data;

@Data
public class WomenProductDto {

    private String name;

    private Double price;

    private Integer quantity;

    private Long category;

    private String photo;

    public WomenProductDto() {
    }

    public WomenProductDto(String name, Double price, Integer quantity, Long category, String photo) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.photo = photo;
    }
}
