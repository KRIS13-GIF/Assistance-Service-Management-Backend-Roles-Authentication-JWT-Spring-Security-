package com.kris.security.services;

import com.kris.security.entities.Product;
import com.kris.security.models.ProductRequest;
import com.kris.security.models.ProductResponse;
import com.kris.security.models.UpdateRequestProduct;
import com.kris.security.repositories.ProductRepository;
import com.kris.security.repositories.UserRepository;
import com.kris.security.user.Role;
import com.kris.security.user.User;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
public class CustomerServices {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CustomerServices(UserRepository userRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }


    public User findCustomer(String id){
        return userRepository.findById(Integer.valueOf(id)).get();
    }


    public ProductResponse createProduct(Integer id, ProductRequest productRequest) throws Exception {
        User customer=findCustomer(String.valueOf(id));
        if (customer.getRole()!= Role.USER){
            throw new Exception("You can not add products to a non-customer/user");
        }
        Product product=new Product();
        product.setSerialNo(productRequest.getSerialNo());
        product.setBrand(productRequest.getBrand());
        product.setTemplate(productRequest.getTemplate());
        product.setDescription(productRequest.getDescription());

        LocalDate datExp= (Date.valueOf(LocalDate.now())).toLocalDate();
        DateTimeFormatter formatter1=DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Date date2= Date.valueOf(datExp.format(formatter1));
        product.setDatePurchase(date2);

        LocalDate date= (Date.valueOf(LocalDate.now().plusDays(3))).toLocalDate();
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Date date1= Date.valueOf(date.format(formatter));
        product.setExpiryDate(date1);

        product.setNotes(productRequest.getNotes());
        product.setPassword(BCrypt.hashpw(productRequest.getPassword(), BCrypt.gensalt()));
        product.setCustomerName(customer.getFirstname());
        product.setFullAddress(productRequest.getFullAddress());
        product.setTelephoneNumber(productRequest.getTelephoneNumber());
        product.setEmail(customer.getEmail());
        product.setFiscalCode(productRequest.getFiscalCode());
        product.setVatNumber(productRequest.getVatNumber());
        product.setPec(productRequest.getPec());
        product.setFileNum(0); // if we have many files , then it takes the file nr of the previous one. Initiliaze all with zeros than
        // in the acceptance phase you can modify the fileNumber.
        product.setCustomer(customer);
        product.setAccept(false);



        return new ProductResponse(productRepository.save(product).getId());
    }


    public ProductResponse consult(Integer id, UpdateRequestProduct updateRequestProduct) throws Exception {
        if (updateRequestProduct.getNr() != 0 && !Objects.equals(updateRequestProduct.getSerialNo(), "")){
            List<Integer> fileNrList = productRepository.findFileNumByCustomerId(String.valueOf(id));

            if (fileNrList.contains(updateRequestProduct.getNr())){
                Product product=productRepository.findProductByFileNumAndCustomerId(updateRequestProduct.getNr(), id);
                if (!Objects.equals(product.getSerialNo(), updateRequestProduct.getSerialNo())) {
                    System.out.println("The current serial number for this is  " + product.getSerialNo());
                    throw new Exception("Please enter a valid serial number");
                }

                System.out.println(product.getSerialNo());

                if (!product.isAccept()) {
                    throw new Exception("The product is not yet accepted !");
                }
                if (product.isProcess()) {
                    throw new Exception("The product can not be updated because is in process phase");
                }
                if (updateRequestProduct.getBrand() != null) {
                    product.setBrand(updateRequestProduct.getBrand());
                }
                if (updateRequestProduct.getTemplate() != null) {
                    product.setTemplate(updateRequestProduct.getTemplate());
                }
                if (updateRequestProduct.getDescription() != null) {
                    product.setDescription(updateRequestProduct.getDescription());
                }
                if (updateRequestProduct.getNotes() != null) {
                    product.setNotes(updateRequestProduct.getNotes());
                }
                if (updateRequestProduct.getPassword() != null) {
                    product.setPassword(updateRequestProduct.getPassword());
                }
                if (updateRequestProduct.getFullAddress() != null) {
                    product.setFullAddress(updateRequestProduct.getFullAddress());
                }

                String productInfo = "Product Information:\n" +
                        "Serial Number: " + product.getSerialNo() + "\n" +
                        "Brand: " + product.getBrand() + "\n" +
                        "Template: " + product.getTemplate() + "\n" +
                        "Description: " + product.getDescription() + "\n" +
                        "Date Purchase: " + product.getDatePurchase() + "\n" +
                        "Expiry Date: " + product.getExpiryDate() + "\n" +
                        "Notes: " + product.getNotes() + "\n" +
                        "Customer Name: " + product.getCustomerName() + "\n" +
                        "Full Address: " + product.getFullAddress() + "\n" +
                        "Telephone Number: " + product.getTelephoneNumber() + "\n" +
                        "Email: " + product.getEmail() + "\n" +
                        "Fiscal Code: " + product.getFiscalCode() + "\n" +
                        "VAT Number: " + product.getVatNumber() + "\n" +
                        "PEC: " + product.getPec() + "\n" +
                        "Accept: " + product.isAccept() + "\n" +
                        "File NO: " + product.getFileNum();
                try (FileWriter fileWriter = new FileWriter(product.getFilename())) {
                    fileWriter.write(productInfo);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Product savedProduct = productRepository.save(product);
                return new ProductResponse(savedProduct.getId());
            } else {
                throw new Exception("Invalid number inserted ! This customer does not have this number");
            }
        }
        else {
            throw new Exception("Please enter values for the number case and the serial number");
        }
    }

}
