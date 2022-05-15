package mk.ukim.finki.service;

import mk.ukim.finki.model.Category;

import java.util.List;

public interface CategoryService {

    Category create(String name, String description);

    Category update(String name, String description);

    void delete(String name);

    List<Category> listCategories();

    List<Category> searchCategories(String searchText);

    void deleteById(Long id);

}
