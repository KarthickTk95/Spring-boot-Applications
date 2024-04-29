package com.newReports.controller;

import com.newReports.config.JwtService;
import com.newReports.dto.AuthRequest;
import com.newReports.dto.Product;
import com.newReports.entity.Member;
import com.newReports.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/Newreports")
public class ProductController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ProductService service;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @PostMapping("/new")
    public String addNewUser(@RequestBody Member member){
        return service.addUser(member);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('AC_ACTIVE')")
    public List<Product> getAllTheProducts() {
        return service.getProducts();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('AC_INACTIVE')")
    public Product getProductById(@PathVariable int id) {
        return service.getProduct(id);
    }


  /*  @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestParam String username, @RequestParam String password) {
        System.out.println("Received request with username: " + username + ", password: " + password);

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(username);
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }
*/

    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        System.out.println("Received request with username: " + authRequest.getUsername() + ", password: " + authRequest.getPassword());

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        System.out.println(authRequest);
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
}
}
