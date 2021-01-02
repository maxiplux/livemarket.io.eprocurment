package io.eprocurment.b2b2021.services.impl;


import io.eprocurment.b2b2021.models.products.Category;
import io.eprocurment.b2b2021.repository.CategoryRepository;
import io.eprocurment.b2b2021.services.CategoryServices;
import io.eprocurment.b2b2021.services.impl.generics.CrudServicesImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServicesImpl extends CrudServicesImpl<Category> implements CategoryServices<Category> {

    @Autowired
    EntityManager entityManager;
    @Autowired
    private CategoryRepository categoryRepository;

    @PostConstruct
    public void posContructor() {
        this.setRepository(categoryRepository);
        this.entityManagerBase = entityManager;
    }


    public Optional<Category> UpdateById(long id, Category element) {
        Optional<Category> optionalCurrentCompany = this.repository.findById(id);
        if (optionalCurrentCompany.isPresent()) {
            Category currentProduct = optionalCurrentCompany.get();

            if (element.getName() != null) {
                currentProduct.setName(element.getName());
            }


            return Optional.of((Category) this.repository.save(currentProduct));
        }
        return Optional.empty();

    }


    @Override
    public Page<Category> findAll(Pageable page, Category filter) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Category> criteriaQuery = criteriaBuilder.createQuery(Category.class);
        Root<Category> categoryRoot = criteriaQuery.from(Category.class);
        List<Predicate> predicates = new ArrayList<Predicate>();

        if (StringUtils.isNotEmpty(filter.getName())) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(categoryRoot.get("name")), "%" + filter.getName().toLowerCase() + "%"));
        }

        Predicate[] predArray = new Predicate[predicates.size()];
        predicates.toArray(predArray);

        criteriaQuery.where(predArray);

        TypedQuery<Category> query = entityManager.createQuery(criteriaQuery);

        int totalRows = query.getResultList().size();

        query.setFirstResult(page.getPageNumber() * page.getPageSize());
        query.setMaxResults(page.getPageSize());

        Page<Category> result = new PageImpl<Category>(query.getResultList(), page, totalRows);

        return result;


    }


}
