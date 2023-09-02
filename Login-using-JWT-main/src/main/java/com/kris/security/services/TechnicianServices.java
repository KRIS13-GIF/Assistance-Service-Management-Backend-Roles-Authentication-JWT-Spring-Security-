package com.kris.security.services;

import com.kris.security.entities.Finish;
import com.kris.security.entities.Ordering;
import com.kris.security.entities.Product;
import com.kris.security.models.FinishRequest;
import com.kris.security.models.FinishResponse;
import com.kris.security.models.OrderResponse;
import com.kris.security.repositories.FinishRepository;
import com.kris.security.repositories.OrderRepository;
import com.kris.security.repositories.ProductRepository;
import com.kris.security.repositories.UserRepository;
import com.kris.security.user.Role;
import com.kris.security.user.User;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class TechnicianServices {

    private final UserRepository userRepository;

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    private final FinishRepository finishRepository;


    public TechnicianServices(UserRepository userRepository, OrderRepository orderRepository, ProductRepository productRepository, FinishRepository finishRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.finishRepository = finishRepository;
    }

    public OrderResponse createOrder(int technicId, int id) throws Exception {

        Optional<User> user=userRepository.findById(technicId);
        if (user.isEmpty()){
            throw new Exception("This is not an Id for technici");
        }
        User user1=user.get();
        if (user1.getRole()== Role.TECHNICIAN) {

            Optional<Product> product = productRepository.findById(id);
            if (product.isEmpty()) {
                throw new Exception("Not a valid ID");
            }
            Product product1 = product.get();
            if (!product1.isAccept()) {
                throw new Exception("Can not put to order if a product is not accepted by acceptance phase");
            }
            product1.setProcess(true);
            productRepository.save(product1);
            
            Ordering order = new Ordering();
            order.setUser(user1); // associate the technic with a specified order
            order.setProduct(product1);
            order.setFileNumber(product1.getFileNum());
            Ordering savedOrder = orderRepository.save(order);
            OrderResponse response = new OrderResponse(savedOrder.getId());
            return response;
        }
        else {
            throw new Exception("Not good personnel type");
        }
    }



    public void repairOrder(String idOrder) throws Exception {
        Optional<Ordering> ordering=orderRepository.findById(Integer.valueOf(idOrder));
        if (ordering.isEmpty()){
            throw new Exception("This orderId does not exist");
        }
        Ordering ordering1=ordering.get();
        // check if the technician is attached to the order
        if (ordering1.getUser()!=null){

            LocalDate startLocalDate = (Date.valueOf(ordering1.getProduct().getDatePurchase().toLocalDate())).toLocalDate();
            Date startDate = Date.valueOf(startLocalDate);
            LocalDate endLocalDate= (Date.valueOf(ordering1.getProduct().getExpiryDate().toLocalDate()).toLocalDate());
            Date endDate = Date.valueOf(endLocalDate); // Convert Timestamp to Date
            LocalDate date =(Date.valueOf(LocalDate.now())).toLocalDate();// The date you want to check
            DateTimeFormatter formatter1=DateTimeFormatter.ofPattern("yyyy-MM-dd");
            Date target= Date.valueOf(date.format(formatter1));

            if (target.compareTo(startDate)>=0 && target.compareTo(endDate)<=0){
                ordering1.setRepaired(true);
                ordering1.setRepNonRepDate(target);
                orderRepository.save(ordering1);
                System.out.println("Task Repaired");
            }
            else {
                throw new Exception("The Data of for this repairing is not between the desired values");
            }
        }
        else {
            throw new Exception("You need a technician to work on this task");
        }

    }

    public void doNotRepair(String id) throws Exception {
        Optional<Ordering> ordering=orderRepository.findById(Integer.valueOf(id));
        if (ordering.isEmpty()){
            throw new Exception("The order id is not good");
        }
        Ordering ordering1=ordering.get();
        // check if the technician is attached to the order
        if (ordering1.getUser()!=null){

            LocalDate startLocalDate = (Date.valueOf(ordering1.getProduct().getDatePurchase().toLocalDate())).toLocalDate();
            Date startDate = Date.valueOf(startLocalDate);

            LocalDate endLocalDate= (Date.valueOf(ordering1.getProduct().getExpiryDate().toLocalDate()).toLocalDate());
            Date endDate = Date.valueOf(endLocalDate);


            LocalDate date =(Date.valueOf(LocalDate.now())).toLocalDate();// The date you want to check
            DateTimeFormatter formatter1=DateTimeFormatter.ofPattern("yyyy-MM-dd");
            Date target= Date.valueOf(date.format(formatter1));

            if (target.compareTo(startDate)>=0 && target.compareTo(endDate)<=0){
                ordering1.setRepNonRepDate(target);
                orderRepository.save(ordering1);
            }
            else {
                throw new Exception("The Data of for this repairing is not between the desired values");
            }
        }
        else {
            throw new Exception("You need a technician to work on this task");
        }

    }


    public FinishResponse putToFinish(String id, FinishRequest finishRequest) throws Exception {
        Optional<Ordering> ordering=orderRepository.findById(Integer.valueOf(id));
        if (ordering.isEmpty()){
            throw new Exception("The order id is not good");
        }
        Ordering ordering1=ordering.get();

        if (ordering1.isCompleted()){
            throw new Exception("You can not put to finish this order ! It is already been put");
        }
        Finish finish=new Finish();
        finish.setOrdering(ordering1);
        finish.setMoney(finishRequest.getMoney());
        finish.setDescription(finishRequest.getDescription());
        finish.setCollect(false); // you need to be an acceptance to inform for collection
        finish.setNrFile(ordering1.getFileNumber());
        finish.setRepaired(ordering1.isRepaired());
        finish.setUser(ordering1.getUser());

        LocalDate date =(Date.valueOf(LocalDate.now())).toLocalDate();// The date you want to check
        DateTimeFormatter formatter1=DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Date finishDate= Date.valueOf(date.format(formatter1));
        finish.setFinishDate(finishDate);

        ordering1.setCompleted(true);
        orderRepository.save(ordering1);

        FinishResponse finishResponse=new FinishResponse(finishRepository.save(finish).getId());
        return finishResponse;

    }










}
