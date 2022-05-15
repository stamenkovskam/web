package mk.ukim.finki.service.impl;

import mk.ukim.finki.model.Category;
import mk.ukim.finki.model.WomenProduct;
import mk.ukim.finki.model.dto.WomenProductDto;
import mk.ukim.finki.model.events.WomenProductCreatedEvent;
import mk.ukim.finki.model.exceptions.CategoryNotFoundException;
import mk.ukim.finki.model.exceptions.ProductNotFoundException;
import mk.ukim.finki.repository.CategoryRepository;
import mk.ukim.finki.repository.WomenProductRepository;
import mk.ukim.finki.service.WomenProductService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WomenProductServiceImpl implements WomenProductService {

    private final WomenProductRepository womenProductRepository;
    private final CategoryRepository categoryRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public WomenProductServiceImpl(
            WomenProductRepository womenProductRepository, CategoryRepository categoryRepository,
            ApplicationEventPublisher applicationEventPublisher) {
        this.womenProductRepository = womenProductRepository;

        this.categoryRepository = categoryRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public List<WomenProduct> findAll() {
        return this.womenProductRepository.findAll();
    }

    @Override
    public Optional<WomenProduct> findById(Long id) {
        return this.womenProductRepository.findById(id);
    }

    @Override
    public Optional<WomenProduct> findByName(String name) {
        return this.womenProductRepository.findByName(name);
    }

    @Override
    public Optional<WomenProduct> save(String name, Double price, Integer quantity, Long categoryId, String photo) {
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

        this.womenProductRepository.deleteByName(name);
        WomenProduct product = new WomenProduct(name, price, quantity, category, photo);
        this.womenProductRepository.save(product);
        //this.refreshMaterializedView();

        this.applicationEventPublisher.publishEvent(new WomenProductCreatedEvent(product));
        return Optional.of(product);
    }

    @Override
    public Optional<WomenProduct> save(WomenProductDto productDto) {
        return Optional.empty();
    }

    @Override
    public Optional<WomenProduct> edit(Long id, String name, Double price, Integer quantity, Long categoryId, String photo) {
        WomenProduct product = this.womenProductRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setPhoto(photo);
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
        product.setCategory(category);
        this.womenProductRepository.save(product);
        return Optional.of(product);
    }

    @Override
    public Optional<WomenProduct> edit(Long id, WomenProductDto productDto) {
        return Optional.empty();
    }


    @Override
    public void deleteById(Long id) {
        this.womenProductRepository.deleteById(id);
    }

    @Override
    public List<WomenProduct> listProductsByNameAndCategory(String name, Long categoryId) {
        Category category =categoryId!=null? this.categoryRepository.findById(categoryId).orElse((Category) null):null;
        if(name != null && category !=null)
            return this.womenProductRepository.findAllByNameLikeAndCategoryContaining("%"+name+"%",category);
        else if(name!=null)
            return this.womenProductRepository.findAllByNameLike("%"+name+"%");
        else if(category != null)
            return this.womenProductRepository.findAllByCategoryContaining(category);
        else
            return this.womenProductRepository.findAll();
    }


}
