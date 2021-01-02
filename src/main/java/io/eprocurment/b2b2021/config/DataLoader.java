package io.eprocurment.b2b2021.config;


import com.github.javafaker.Faker;
import io.eprocurment.b2b2021.exceptions.ResourceNotFoundException;
import io.eprocurment.b2b2021.models.corporate.Company;
import io.eprocurment.b2b2021.models.users.Role;
import io.eprocurment.b2b2021.models.users.User;
import io.eprocurment.b2b2021.models.enums.RoleName;
import io.eprocurment.b2b2021.models.enums.StatesName;
import io.eprocurment.b2b2021.models.generic.Contact;
import io.eprocurment.b2b2021.models.generic.ExtraName;
import io.eprocurment.b2b2021.models.orders.Order;
import io.eprocurment.b2b2021.models.orders.OrderLine;
import io.eprocurment.b2b2021.models.products.Category;
import io.eprocurment.b2b2021.models.products.Product;
import io.eprocurment.b2b2021.models.products.ProductSku;
import io.eprocurment.b2b2021.models.rules.State;
import io.eprocurment.b2b2021.repository.*;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@Transactional(isolation = Isolation.SERIALIZABLE)
public class DataLoader implements ApplicationRunner {

    @Value("${maxDataInitially}")
    private Integer maxDataInitially;

    @Value("${isEnableDummyDataForTest}")
    private Boolean isEnableDummyDataForTest;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private StateRepository stateRepository;


    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderLineRepository orderLineRepository;


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductSkuRepository productSkuRepository;


    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CompanyRepository companyRepository;
    private EasyRandom factory;


    @Autowired
    private CategoryRepository categoryRepository;

    private Category category;


    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void run(ApplicationArguments args) {
        this.createAdmin();
        this.createStates();

        if (this.isEnableDummyDataForTest) {
            this.createFactory();
            this.createUsersDemo();
            this.createCategory();
            this.CreateCompanies();

            this.createProducts();
            this.createProductsSku();
            Company company = this.createCompany();
            this.createOrder(company);


        }


    }

    private void createOrder(Company company) {
        State state = this.stateRepository.findByName(StatesName.OPEN.name()).orElseThrow(() -> new ResourceNotFoundException("Order State No found "));
        Order order = Order.builder()
                .company(company)
                .description("Esta empresa es la principal")
                .orderStatus(state)
                .build();
        order.clearOrderLines();
        this.orderRepository.save(order);

        List<ProductSku> productSkus = (List<ProductSku>) this.productSkuRepository.findAll();


        this.orderLineRepository.saveAll(
                productSkus.stream().skip(22).map(productSku -> {

                    double start = 0;
                    double end = 100;
                    double random = new Random().nextDouble();
                    double quantity = start + (random * (end - start));

                    OrderLine orderLine = OrderLine

                            .builder()
                            .product(productSku)
                            .tax(16.0)

                            .price(productSku.getPrice())
                            .quantity(quantity)
                            .orderHeader(order)
                            .build();
                    return orderLine;
                }).collect(Collectors.toSet()));


        order.clearComments();
        order.addComment(company, "My firts comment");
        order.addComment(company, "My second comment");


        this.orderRepository.save(order);

    }


    private Company createCompany() {

        Company company = factory.nextObject(Company.class);
        User user = this.createManagerCompany();
        Faker faker = new Faker(new Random(20 * 1000));
        company.setName(faker.company().name());


        company.setMainOffice(null);
        company.setEmail("ulu@compudoblblblas.com");
        company.setLastModifiedBy(null);
        company.setManager(user);
        company.clearContacts();


        company.setContacts(Arrays.asList(factory.nextObject(Contact.class), factory.nextObject(Contact.class)));


        return this.companyRepository.findById(1L).orElseThrow(() -> new ResourceNotFoundException("Company doest no exist"));

    }

    private void createStates() {

        State reject = State.builder()
                .name(StatesName.OPEN.name())
                .sequence(0)
                .sendEmilToAdmin(true)
                .sendEmilToStaff(true)
                .sendEmilToClient(true)
                .build();
        this.stateRepository.save(reject);
        State cancel = State.builder()
                .name(StatesName.REJECT.name())
                .sequence(2)
                .sendEmilToAdmin(true)
                .sendEmilToStaff(true)
                .sendEmilToClient(true)
                .build();
        this.stateRepository.save(cancel);
        State pending = State.builder()
                .name(StatesName.PENDING_BY_APPROVAL.name())
                .sequence(1)
                .sendEmilToAdmin(true)
                .sendEmilToStaff(true)
                .sendEmilToClient(true)
                .build();
        this.stateRepository.save(pending);

        pending.addState(reject);
        pending.addState(cancel);

        State approved = State.builder()
                .name(StatesName.APPROVED.name())
                .sequence(2)
                .sendEmilToAdmin(true)
                .sendEmilToStaff(true)
                .sendEmilToClient(true)
                .build();

        pending.addState(approved);

        this.stateRepository.save(approved);

        State dispatched = State.builder()
                .name(StatesName.DISPATCHED.name())
                .sequence(3)
                .sendEmilToAdmin(true)
                .sendEmilToStaff(true)
                .sendEmilToClient(true)
                .build();
        approved.addState(dispatched);

        this.stateRepository.save(dispatched);

        State delivered = State.builder()
                .name(StatesName.DELIVERED.name())
                .sequence(4)
                .sendEmilToAdmin(true)
                .sendEmilToStaff(true)
                .sendEmilToClient(true)
                .build();
        dispatched.addState(delivered);

        this.stateRepository.save(delivered);
        ////////////////////////////////////////////////////////////////////////
        this.stateRepository.save(dispatched);
        this.stateRepository.save(approved);
        this.stateRepository.save(pending);
        this.stateRepository.save(cancel);
        this.stateRepository.save(reject);

        //reject.getNextStates().stream().sorted(Comparator.comparing(State::getSequence));


    }

    private void createProductsSku() {
        this.productRepository.findAll().forEach(product -> {

            ProductSku productsku = factory.nextObject(ProductSku.class);

            productsku.setProduct(product);


            productsku.setExtras(Arrays.asList(factory.nextObject(ExtraName.class), factory.nextObject(ExtraName.class)));
            this.productSkuRepository.save(productsku);

        });
    }

    private void createProducts() {

        this.productRepository.saveAll(
                IntStream.range(1, maxDataInitially).mapToObj(e ->
                        {
                            Faker faker = new Faker(new Random(1000));

                            Product product = factory.nextObject(Product.class);
                            product.setName(faker.esports().game());
                            product.setCategory(this.category);
                            return product;

                        }

                ).map(item -> {

                    return item;
                }).collect(Collectors.toSet())
        );

    }

    private User createManagerCompany() {
        List<Role> roleList = this.roleRepository.findAll().stream().filter(currentRol -> !currentRol.getName().equals(RoleName.ROLE_ADMIN.name()) ).collect(Collectors.toList());


        User user = factory.nextObject(User.class);


        Faker faker = new Faker(new Random(20 * 1000));
        String username = faker.name().username() + user.getUsername();
        user.setId(1050L);
        user.setUsername(username.substring(0, 19));

        user.setPassword(passwordEncoder().encode("12345"));
        user.setEmail(user.getUsername() + "@supercompany.com");
        user.setRecordActive(true);
        user.setEnabled(true);
        user.clearRoles();

        roleList.forEach(role -> {
            user.addRole(role);
        });

        return this.userRepository.save(user);
    }

    private void createUsersDemo() {
        List<Role> roleList = this.roleRepository.findAll().stream().filter(currentRol -> !currentRol.getName().equals(RoleName.ROLE_ADMIN.name())).collect(Collectors.toList());
        this.userRepository.saveAll(
                IntStream.range(1, maxDataInitially).mapToObj(e ->
                        {
                            User user = factory.nextObject(User.class);
                            Faker faker = new Faker(new Random(20 * 1000));
                            String username = faker.name().username() + user.getUsername();
                            user.setUsername(username.substring(0, 19));

                            user.setPassword(passwordEncoder().encode("12345"));
                            user.setEmail(user.getUsername() + "@admin.com");
                            user.setRecordActive(true);

                            user.setEnabled(true);

                            user.clearRoles();
                            roleList.forEach(role -> {
                                user.addRole(role);
                            });

                            return user;

                        }

                ).map(item -> {

                    return item;
                }).collect(Collectors.toSet())
        );


    }

    private void createFactory() {
        EasyRandomParameters parameters = new EasyRandomParameters()
                .seed(123L)
                .objectPoolSize(maxDataInitially)
                .randomizationDepth(2)
                .charset(StandardCharsets.UTF_8)
                .stringLengthRange(5, 20)
                .collectionSizeRange(1, 20)
                .scanClasspathForConcreteTypes(true)
                .overrideDefaultInitialization(false)
                .ignoreRandomizationErrors(true);
        factory = new EasyRandom(parameters);
    }

    private void CreateCompanies() {


        Set<User> dataSet = new HashSet<>();

        this.companyRepository.saveAll(
                IntStream.range(1, maxDataInitially).mapToObj(e ->
                {
                    Company company = factory.nextObject(Company.class);
                    User user = this.userRepository.findAll()
                            .stream().filter(user1 -> {

                                return !dataSet.contains(user1);
                            })
                            .findAny().orElse(null);
                    dataSet.add(user);
                    Faker faker = new Faker(new Random(20 * 1000));
                    company.setName(faker.company().name());


                    company.setMainOffice(null);
                    company.setEmail("email@email.com");
                    company.setLastModifiedBy(null);
                    company.setManager(user);

                    company.setContacts(Arrays.asList(factory.nextObject(Contact.class), factory.nextObject(Contact.class)));


                    return company;

                }).map(item -> {

                    return item;
                }).collect(Collectors.toSet()));


    }

    private void createAdmin() {

        User user = new User();
        if (!this.userRepository.existsByUsername("admin@admin.com")) {
            roleRepository.save(Role.builder().name(RoleName.ROLE_USER.name()).build());
            roleRepository.save(Role.builder().name(RoleName.ROLE_ADMIN.name()).build());

            roleRepository.save(Role.builder().name(RoleName.ROLE_COMPANY_BRANCH_ASSISTANCE.name()).build());
            roleRepository.save(Role.builder().name(RoleName.ROLE_COMPANY_MANAGER.name()).build());


            user.setPassword(passwordEncoder().encode("12345"));
            user.setUsername("admin@admin.com");
            user.setFirtsName("Jhon");
            user.setLastName("Man");
            user.setRecordActive(true);
            user.setEmail("jhon.man@domain.com");
            user.setEnabled(true);
            this.roleRepository.findAll().forEach(role -> {
                user.addRole(role);
            });

            this.userRepository.save(user);

        }


    }

    private void createCategory() {
        Category category = new Category();
        category.setName("General");
        this.category = this.categoryRepository.save(category);

    }

}
