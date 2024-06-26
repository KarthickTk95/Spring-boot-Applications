package com.newReports.service;

import com.newReports.dto.Product;
import com.newReports.entity.Member;
import com.newReports.repository.UserInfoRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ProductService {

    List<Product> productList = null;

    @Autowired
    private UserInfoRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void loadProductsFromDB() {
        productList = IntStream.rangeClosed(1, 100)
                .mapToObj(i -> Product.builder()
                        .productId(i)
                        .name("product " + i)
                        .qty(new Random().nextInt(10))
                        .price(new Random().nextInt(5000)).build()
                ).collect(Collectors.toList());
    }


    public List<Product> getProducts() {
        return productList;
    }

    public Product getProduct(int id) {
        return productList.stream()
                .filter(product -> product.getProductId() == id)
                .findAny()
                .orElseThrow(() -> new RuntimeException("product " + id + " not found"));
    }


    public String addUser(Member member) {
        System.out.println("Password before base64 encrypt -> "+ member.getPassword());

        String encodedString = Base64.getEncoder().encodeToString(member.getPassword().getBytes(StandardCharsets.UTF_8));
       // reportsMember.setPassword(passwordEncoder.encode(reportsMember.getPassword()));
        System.out.println("Password after base64 encrypt -> "+encodedString);
        member.setPassword(encodedString);
        repository.save(member);
        return "user added to system ";
    }
}
