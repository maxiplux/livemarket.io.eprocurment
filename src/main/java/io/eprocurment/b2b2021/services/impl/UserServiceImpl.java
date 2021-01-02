package io.eprocurment.b2b2021.services.impl;


import io.eprocurment.b2b2021.exceptions.ResourceNotFoundException;
import io.eprocurment.b2b2021.exceptions.ResourceUserOrEmailException;
import io.eprocurment.b2b2021.models.corporate.Company;
import io.eprocurment.b2b2021.models.users.Role;
import io.eprocurment.b2b2021.models.users.User;
import io.eprocurment.b2b2021.repository.CompanyRepository;
import io.eprocurment.b2b2021.repository.RoleRepository;
import io.eprocurment.b2b2021.repository.UserRepository;
import io.eprocurment.b2b2021.services.UserService;
import io.eprocurment.b2b2021.services.UserServiceCrud;
import lombok.extern.log4j.Log4j2;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional(rollbackFor = Exception.class)
@Log4j2
public class UserServiceImpl implements UserService, UserDetailsService, UserServiceCrud<User> {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;


    @Autowired
    @Qualifier(value = "dozer")
    private Mapper mapperDozer;

    @Autowired
    private RoleRepository roleRepository;


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            log.error("Error en el login: no existe el user '" + username + "' en el sistema!");
            throw new UsernameNotFoundException("Error en el login: no existe el user '" + username + "' en el sistema!");
        }

        List<GrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .peek(authority -> log.info("Role: " + authority.getAuthority()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getEnabled(), true, true, true, authorities);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {

        log.info("findByUsername", username);

        return userRepository.findByUsername(username);
    }

    @Override

    public User save(User user) {
        log.info("User", user); // this is only to test services I know that this is a  bad pratice, however I need to test all cloud services
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        List<Role> roles = this.roleRepository.findAll().stream().filter(role -> user.getRoles().contains(role)).collect(Collectors.toList());
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUsername(user.getUsername().toLowerCase());
        return this.userRepository.save(user);
    }

    @Override
    public Boolean existsByUsername(String username) {

        log.info("User", username); // this is only to test services I know that this is a  bad pratice, however I need to test all cloud services
        return this.userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String username) {
        log.info("User", username); // this is only to test services I know that this is a  bad pratice, however I need to test all cloud services
        return this.userRepository.existsByEmail(username);
    }

    @Override
    public Optional<Company> findCompanyByUserId(Long id) {
        return this.companyRepository.findByManager_Id(id);
    }

    @Override
    public void areOkayUsernameOrEmail(User user) {

        if (this.existsByUsername(user.getUsername()) || this.existsByEmail(user.getEmail())) {

            throw new ResourceUserOrEmailException("User or email invalid ");
        }


    }

    @Override
    public Page<User> findAll(Pageable pageable, User filter) {
        return null;
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return this.userRepository.findAll(pageable);
    }

    @Override
    public User create(User elememnt) {
        return this.userRepository.save(elememnt);
    }

    @Override
    public Optional<User> UpdateById(long id, User source) {
        User currentUser = this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        mapperDozer.map(source, currentUser);
        this.userRepository.save(currentUser);
        return Optional.of(currentUser);


    }


    @Override
    public Boolean deleteById(long id) {
        User currentUser = this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        currentUser.setRecordActive(false);
        currentUser.setEnabled(false);
        this.userRepository.save(currentUser);
        return true;
    }

    @Override
    public Optional<User> findById(long id) {
        User currentUser = this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        return Optional.of(currentUser);
    }


    @Override
    public User show(long id) {

        return this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }
}
