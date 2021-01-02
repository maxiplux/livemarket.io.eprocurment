package io.eprocurment.b2b2021.services.impl;

import io.eprocurment.b2b2021.exceptions.ResourceNotFoundException;
import io.eprocurment.b2b2021.models.corporate.Company;
import io.eprocurment.b2b2021.models.dtos.OrderDto;
import io.eprocurment.b2b2021.models.dtos.OrderDtoQuery;
import io.eprocurment.b2b2021.models.dtos.OrderLineDto;
import io.eprocurment.b2b2021.models.users.User;
import io.eprocurment.b2b2021.models.enums.StatesName;
import io.eprocurment.b2b2021.models.orders.Order;
import io.eprocurment.b2b2021.models.orders.OrderLine;
import io.eprocurment.b2b2021.models.products.ProductSku;
import io.eprocurment.b2b2021.models.rules.State;

import io.eprocurment.b2b2021.repository.*;
import io.eprocurment.b2b2021.services.OrderServices;
import io.eprocurment.b2b2021.util.AuthExtraTools;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.security.Principal;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Log4j2
@Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
public class OrderServicesImpl implements OrderServices {

    private static final Double TAX_VALUE = 16D;
    @Autowired
    EntityManager entityManager;


    @Autowired
    private OrderRepository orderRepository;




    @Autowired
    private OrderLineRepository orderLineRepository;

    @Autowired
    private ProductSkuRepository productSkuRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private CompanyRepository companyRepository;


    @Override
    public Page<Order> findAll(Company company, OrderDtoQuery filter, Pageable page) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = criteriaQuery.from(Order.class);
        List<Predicate> predicates = new ArrayList<Predicate>();

        if (Objects.nonNull(filter.getCompany())) {
            log.info("filter by company",filter);
            predicates.add(criteriaBuilder.equal(orderRoot.<Company>get("id"), filter.getOrderId()));
        }

        if (Objects.nonNull(filter.getOrderStatus())) {
            log.info("filter by status",filter);
            predicates.add(criteriaBuilder.equal(orderRoot.<State>get("name"), filter.getOrderStatus().getName()));
        }


        if (Objects.nonNull(filter.getCreatedAt())) {
            log.info("filter by createAt",filter);
            predicates.add(criteriaBuilder.equal(orderRoot.<Date>get("createdAt"), filter.getCreatedAt()));
        }


        if (filter.isByRange()) {
            log.info("filter by Range",filter);
            predicates.add(criteriaBuilder.between(orderRoot.get("createdAt"), filter.getCreatedAtStart(), filter.getCreatedAtEnd()));
        }

        Predicate[] predArray = new Predicate[predicates.size()];
        predicates.toArray(predArray);

        criteriaQuery.where(predArray);

        TypedQuery<Order> query = entityManager.createQuery(criteriaQuery);

        int totalRows = query.getResultList().size();

        log.info("filter Criteria order : Total Orders",totalRows);

        query.setFirstResult(page.getPageNumber() * page.getPageSize());
        query.setMaxResults(page.getPageSize());

        Page<Order> result = new PageImpl<Order>(query.getResultList(), page, totalRows);

        return result;
    }


    @Override

    public Page<Order> findAll(Company company, Pageable pageable) {
        return this.orderRepository.findAllByCompany(company, pageable);
    }

    @Override
    public Optional<Company> findCompanyByPrincipal(Principal principal) {

        log.info("findCompanyByPrincipal ", principal.getName());

        Optional<Map<String, String>> optionalClaimsMap = AuthExtraTools.getClaimsFromPrincipal(principal);
        log.info("optionalClaimsMap ", optionalClaimsMap);


        AtomicReference<Long> userId = new AtomicReference<>(0L);
        AtomicReference<Long> companyId = new AtomicReference<>(0L);
        AtomicReference<Boolean> isCompanyManager = new AtomicReference<>(false);

        optionalClaimsMap.ifPresent(stringStringMap -> {
            userId.set(Long.valueOf(stringStringMap.get("userId")));
            companyId.set(Long.valueOf(stringStringMap.get("companyId")));
            isCompanyManager.set(Boolean.valueOf(stringStringMap.get("isCompanyManager")));


        });
        if (isCompanyManager.get()) {
            return Optional.of(this.companyRepository.findByManager_IdAndId(userId.get(), companyId.get()).orElseThrow(() -> new ResourceNotFoundException("User And Company not found with ")));
        }


        return this.companyRepository.findById(companyId.get());


    }

    @Override
    public Optional<User> findCompanyByApproval(Principal principal) {

        log.info("findCompanyByPrincipal ", principal.getName());

        Optional<Map<String, String>> optionalClaimsMap = AuthExtraTools.getClaimsFromPrincipal(principal);
        log.info("optionalClaimsMap ", optionalClaimsMap);


        AtomicReference<Long> userId = new AtomicReference<>(0L);
        AtomicReference<Long> companyId = new AtomicReference<>(0L);
        AtomicReference<Boolean> isCompanyManager = new AtomicReference<>(false);

        optionalClaimsMap.ifPresent(stringStringMap -> {
            userId.set(Long.valueOf(stringStringMap.get("userId")));
            companyId.set(Long.valueOf(stringStringMap.get("companyId")));
            isCompanyManager.set(Boolean.valueOf(stringStringMap.get("isCompanyManager")));


        });

        Company company = this.companyRepository.findByManager_IdAndId(userId.get(), companyId.get()).orElseThrow(() -> new ResourceNotFoundException("User And Company not found with "));

        if (Objects.nonNull(company.getMainOffice()))
        {
            return Optional.of(company.getMainOffice().getManager() );
        }
        return Optional.of( company.getManager() );


    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Order createOrder(Company company, OrderDto orderDto) {


        State state = this.stateRepository.findByName(StatesName.OPEN.name()).orElseThrow(() -> new ResourceNotFoundException("Order State No found "));
        log.info("createOrder ", company, orderDto);
        var order = Order.builder()
                .company(company)
                .orderStatus(state)
                .description(orderDto.getDescription()).build();
        return this.orderRepository.save(order);

    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Iterable<OrderLine> addItemToOrder(Company company, long orderId, OrderLineDto orderLineDto,Boolean isForUpdate) {
        //todo remove extra data thro
        Order order=this.orderRepository.findByIdAndAndCompany(orderId,company).orElseThrow(() -> new ResourceNotFoundException("Relation Order and Company doest not match"));
        ProductSku productSku=this.productSkuRepository.findById(orderLineDto.getSku()).orElseThrow(() -> new ResourceNotFoundException("Relation Order and Company doest not match"));


        cleanPreviousLineFromOrder(orderLineDto, order);
        OrderLine orderLine=OrderLine
                .builder()
                .orderHeader(order)
                .quantity(orderLineDto.getQuantity())
                .product(productSku)
                .build();
        this.orderLineRepository.save(orderLine);
        return this.orderLineRepository.findByOrderHeader(order).stream().collect(Collectors.toList());
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Iterable<OrderLine> addItemToOrder(Company company, long orderId, List<OrderLineDto> orderLineDtos,Boolean isForUpdate) {

        Order order=this.orderRepository.findByIdAndAndCompany(orderId,company).orElseThrow(() -> new ResourceNotFoundException("Relation Order and Company doest not match"));
        cleanPreviousLineFromOrder(orderLineDtos, order);


        this.orderLineRepository.saveAll(
        orderLineDtos.stream().filter(orderLineDto -> orderLineDto.getQuantity()>0).map(orderLineDto -> {
            ProductSku productSku=this.productSkuRepository.findById(orderLineDto.getSku()).orElseThrow(() -> new ResourceNotFoundException("Product In line No found"));
            return OrderLine.
                    builder()
                    .quantity(orderLineDto.getQuantity())
                    .product(productSku)
                    .tax(TAX_VALUE)
                    .orderHeader(order)
                    .build();

        }).collect(Collectors.toList()));
        return this.orderLineRepository.findByOrderHeader(order).stream().collect(Collectors.toList());

    }

    private void cleanPreviousLineFromOrder(List<OrderLineDto> orderLineDtos, Order order) {
        this.orderLineRepository.findByOrderHeader(order)
                .stream()
                .filter(currentOrderLine-> orderLineDtos.contains(OrderLineDto.builder().sku(currentOrderLine.getProduct().getSku()).build()))
                .parallel()
                .forEach(orderLine -> this.orderLineRepository.delete(orderLine));
    }


    private void cleanPreviousLineFromOrder(OrderLineDto orderLineDto, Order order) {
        this.orderLineRepository.findByOrderHeader(order)
                .stream()
                .filter(currentOrderLine-> orderLineDto.getSku().equals(currentOrderLine.getProduct().getSku()))
                .parallel()
                .forEach(orderLine -> this.orderLineRepository.delete(orderLine));
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public StatesName changeOrderStatus(Company company, long orderId, StatesName statesName) {

       Order order=  this.orderRepository.findByIdAndAndCompany(orderId, company).orElseThrow(() -> new ResourceNotFoundException("Relation Order and Company doest not match"));
       State state=this.stateRepository.findByName(statesName.name()).orElseThrow(() -> new ResourceNotFoundException("Role  doest not match"));
       order.setOrderStatus(state);
       this.orderRepository.save(order);
       return statesName;
    }
}
