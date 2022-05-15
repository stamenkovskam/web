package mk.ukim.finki.service.impl;

import mk.ukim.finki.model.events.MenProductCreatedEvent;
import mk.ukim.finki.service.MenProductService;
import mk.ukim.finki.model.Category;
import mk.ukim.finki.model.dto.MenProductDto;
import mk.ukim.finki.model.exceptions.CategoryNotFoundException;
import mk.ukim.finki.model.MenProduct;
import mk.ukim.finki.model.exceptions.ProductNotFoundException;
import mk.ukim.finki.repository.CategoryRepository;
import mk.ukim.finki.repository.MenProductRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MenProductServiceImpl implements MenProductService {

    private final MenProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public MenProductServiceImpl(MenProductRepository productRepository,
                                 CategoryRepository categoryRepository,
                                 ApplicationEventPublisher applicationEventPublisher) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public List<MenProduct> findAll() {
        return this.productRepository.findAll();
    }



    @Override
    public Optional<MenProduct> findById(Long id) {
        return this.productRepository.findById(id);
    }

    @Override
    public Optional<MenProduct> findByName(String name) {
        return this.productRepository.findByName(name);
    }

    @Override
    @Transactional
    public Optional<MenProduct> save(String name, Double price, Integer quantity, Long categoryId, String photo) {
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

        this.productRepository.deleteByName(name);
        MenProduct product = new MenProduct(name, price, quantity, category, photo);
        this.productRepository.save(product);
        //this.refreshMaterializedView();

        this.applicationEventPublisher.publishEvent(new MenProductCreatedEvent(product));
        return Optional.of(product);
    }

    @Override
    public Optional<MenProduct> save(MenProductDto productDto) {
        Category category = this.categoryRepository.findById(productDto.getCategory())
                .orElseThrow(() -> new CategoryNotFoundException(productDto.getCategory()));

        this.productRepository.deleteByName(productDto.getName());
        MenProduct product = new MenProduct(productDto.getName(), productDto.getPrice(), productDto.getQuantity(), category, productDto.getPhoto());
        this.productRepository.save(product);
        //this.refreshMaterializedView();

        this.applicationEventPublisher.publishEvent(new MenProductCreatedEvent(product));
        return Optional.of(product);
    }

    @Override
    @Transactional
    public Optional<MenProduct> edit(Long id, String name, Double price, Integer quantity, Long categoryId, String photo) {

        MenProduct product = this.productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setPhoto(photo);
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
        product.setCategory(category);
        this.productRepository.save(product);
        return Optional.of(product);
    }

    @Override
    public Optional<MenProduct> edit(Long id, MenProductDto productDto) {
        MenProduct product = this.productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());

        Category category = this.categoryRepository.findById(productDto.getCategory())
                .orElseThrow(() -> new CategoryNotFoundException(productDto.getCategory()));
        product.setCategory(category);


        this.productRepository.save(product);
        return Optional.of(product);
    }

    @Override
    public void deleteById(Long id) {
        this.productRepository.deleteById(id);
    }



    @Override
    public List<MenProduct> listProductsByNameAndCategory(String name, Long categoryId) {
        Category category =categoryId!=null? this.categoryRepository.findById(categoryId).orElse((Category) null):null;
        if(name != null && category !=null)
            return this.productRepository.findAllByNameLikeAndCategoryContaining("%"+name+"%",category);
        else if(name!=null)
            return this.productRepository.findAllByNameLike("%"+name+"%");
        else if(category != null)
            return this.productRepository.findAllByCategoryContaining(category);
        else
            return this.productRepository.findAll();
    }
}
