package com.kris.security.services;

import com.kris.security.entities.Finish;
import com.kris.security.entities.Product;
import com.kris.security.models.FileName;
import com.kris.security.models.ProductResponse;
import com.kris.security.repositories.FinishRepository;
import com.kris.security.repositories.ProductRepository;
import com.kris.security.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

@Service
public class AcceptanceServices {

    private final UserRepository userRepository;

    private CustomerServices customerServices;
    private final ProductRepository productRepository;
    private final ProductServices productServices;

    private final FinishRepository finishRepository;

    public AcceptanceServices(UserRepository userRepository, CustomerServices customerServices, ProductRepository productRepository, ProductServices productServices, FinishRepository finishRepository) {
        this.userRepository = userRepository;
        this.customerServices = customerServices;
        this.productRepository = productRepository;
        this.productServices = productServices;

        this.finishRepository = finishRepository;
    }

    public ProductResponse acceptProduct(int id, FileName fileName) throws Exception {
        Product product=productServices.findProduct(String.valueOf(id));

        // Retrieve the latest file number
        Integer latestFileNum = productRepository.findLatestFileNum();

        // Increment the file number for the new product
        Integer newFileNum = latestFileNum + 1;
        if (product.isAccept()){
            //System.out.println("The product is already been accepted");
            throw new Exception("The product is accepted !");
        }
        product.setAccept(true);
        product.setFileNum(newFileNum);
        product.setFilename(fileName.getName());
        productRepository.save(product);
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

        return new ProductResponse(product.getId());

    }









}
