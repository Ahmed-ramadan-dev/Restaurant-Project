
package com.spring.boot.Service.Impl;


import com.spring.boot.Controller.Vm.ProductOrderItemVm;
import com.spring.boot.Controller.Vm.RequestOrderVm;
import com.spring.boot.Controller.Vm.security.UserVm;
import com.spring.boot.Exception.SystemException;
import com.spring.boot.Model.Order;
import com.spring.boot.Model.Product;
import com.spring.boot.Model.Security.User;
import com.spring.boot.Repo.OrderRepo;
import com.spring.boot.Repo.ProductRepo;
import com.spring.boot.Repo.Security.UserRepo;
import com.spring.boot.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private OrderRepo orderRepo;
  @Autowired
  private ProductRepo productRepo;


    @Override
    public void createOrder(RequestOrderVm requestOrderVm) {// product ids / total prise/ total number
        if (Objects.isNull(requestOrderVm) ||
                Objects.isNull(requestOrderVm.getProductOrderItemVmList()) ||
                requestOrderVm.getProductOrderItemVmList().isEmpty()) {

            throw new SystemException("order.request.invalid", HttpStatus.BAD_REQUEST);
        }

        List<Long> Ids = requestOrderVm.getProductOrderItemVmList().stream().map(vm -> vm.getProductId()).distinct().collect(Collectors.toList());

        List<Product> productsList = productRepo.findByIdIn(Ids);
        if (productsList.isEmpty()) {
            throw new SystemException("product.invalid", HttpStatus.NOT_FOUND);

        }
        if(productsList.stream().map(Product::getId).collect(Collectors.toList()).size() != Ids.size()){
            throw new SystemException("one.or.more.product.invalid", HttpStatus.BAD_REQUEST);
        }

        double totalPriceDb = 0;
        int totalNumberDb = 0;

        // احسب السعر الإجمالي
        for (ProductOrderItemVm item : requestOrderVm.getProductOrderItemVmList()) {
            for (Product product : productsList) {
                if (product.getId().equals(item.getProductId())) {
                    totalPriceDb += product.getPrice() * item.getQuantity();
                    totalNumberDb += item.getQuantity();
                }
            }
        }
        if (totalPriceDb!= requestOrderVm.getTotalPrice()){
            throw new SystemException("total.price.is.wrong", HttpStatus.BAD_REQUEST);
        }
        if (totalNumberDb!= requestOrderVm.getTotalNumber()){
            throw new SystemException("total.number.is.wrong", HttpStatus.BAD_REQUEST);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserVm userVm = (UserVm)authentication.getPrincipal(); // دي هتجيب اليوزرنيم بس
        String username = userVm.getUsername();
        Optional<User> user = userRepo.findByUsername(username);
        if (!user.isPresent()) {
            throw new SystemException("user.not.found", HttpStatus.BAD_REQUEST);
        }
        long userId = user.get().getId();

        Order order = new Order();
        String orderCode = "ORD-" + Year.now().getValue() + "-" + userId + "-" + UUID.randomUUID().toString();
        order.setCode(orderCode);
        order.setTotalPrice(totalPriceDb);
        order.setTotalNumber(totalNumberDb);
        order.setProducts(productsList);
        User user1 = user.get();
        order.setUser(user1);
        orderRepo.save(order);


    }
}