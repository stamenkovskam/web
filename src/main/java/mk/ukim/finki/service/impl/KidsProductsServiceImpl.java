package mk.ukim.finki.service.impl;

import mk.ukim.finki.model.Category;
import mk.ukim.finki.model.KidsProduct;
import mk.ukim.finki.model.dto.KidProductDto;
import mk.ukim.finki.model.events.KidsProductCreatedEvent;
import mk.ukim.finki.model.exceptions.CategoryNotFoundException;
import mk.ukim.finki.model.exceptions.ProductNotFoundException;
import mk.ukim.finki.repository.CategoryRepository;
import mk.ukim.finki.repository.KidsProductRepository;
import mk.ukim.finki.service.KidsProductService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class KidsProductsServiceImpl implements KidsProductService {
    private final KidsProductRepository kidsProductsRepository;
    private final CategoryRepository categoryRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public KidsProductsServiceImpl(KidsProductRepository kidsProductsRepository, CategoryRepository categoryRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.kidsProductsRepository = kidsProductsRepository;
        this.categoryRepository = categoryRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }


    @Override
    public List<KidsProduct> findAll() {
        return this.kidsProductsRepository.findAll();
    }

    @Override
    public Optional<KidsProduct> findById(Long id) {
        return this.kidsProductsRepository.findById(id);
    }

    @Override
    public Optional<KidsProduct> findByName(String name) {
        return this.kidsProductsRepository.findByName(name);
    }

    @Override
    @Transactional
    public Optional<KidsProduct> save(String name, Double price, Integer quantity, Long categoryId, String photo) {
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

        this.kidsProductsRepository.deleteByName(name);
        KidsProduct product = new KidsProduct(name, price, quantity, category, photo);
        this.kidsProductsRepository.save(product);
        //this.refreshMaterializedView();

        this.applicationEventPublisher.publishEvent(new KidsProductCreatedEvent(product));
        return Optional.of(product);
    }



    @Override
    public Optional<KidsProduct> save(KidProductDto kidProductDto) {
        Category category = this.categoryRepository.findById(kidProductDto.getCategory())
                .orElseThrow(() -> new CategoryNotFoundException(kidProductDto.getCategory()));

        this.kidsProductsRepository.deleteByName(kidProductDto.getName());
        KidsProduct product = new KidsProduct(kidProductDto.getName(), kidProductDto.getPrice(), kidProductDto.getQuantity(), category, kidProductDto.getPhoto());
        this.kidsProductsRepository.save(product);
        //this.refreshMaterializedView();

        this.applicationEventPublisher.publishEvent(new KidsProductCreatedEvent(product));
        return Optional.of(product);
    }

    @Override
    public Optional<KidsProduct> edit(Long id, String name, Double price, Integer quantity, Long categoryId, String photo) {
        KidsProduct product = this.kidsProductsRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setPhoto(photo);
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
        product.setCategory(category);
        this.kidsProductsRepository.save(product);
        return Optional.of(product);
    }

    @Override
    public Optional<KidsProduct> edit(Long id, KidProductDto productDto) {
        KidsProduct product = this.kidsProductsRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());

        Category category = this.categoryRepository.findById(productDto.getCategory())
                .orElseThrow(() -> new CategoryNotFoundException(productDto.getCategory()));
        product.setCategory(category);


        this.kidsProductsRepository.save(product);
        return Optional.of(product);
    }

    @Override
    public void deleteById(Long id) {
        this.kidsProductsRepository.deleteById(id);
    }

    @Override
    public List<KidsProduct> listKidsProductsByNameAndCategory(String name, Long categoryId) {
        Category category =categoryId!=null? this.categoryRepository.findById(categoryId).orElse((Category) null):null;
        if(name != null && category !=null)
            return this.kidsProductsRepository.findAllByNameLikeAndCategoryContaining("%"+name+"%",category);
        else if(name!=null)
            return this.kidsProductsRepository.findAllByNameLike("%"+name+"%");
        else if(category != null)
            return this.kidsProductsRepository.findAllByCategoryContaining(category);
        else
            return this.kidsProductsRepository.findAll();
    }
}
