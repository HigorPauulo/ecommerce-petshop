package br.com.importaai.ecommercepetshop.mapper;

import br.com.importaai.ecommercepetshop.dto.request.AddressRequest;
import br.com.importaai.ecommercepetshop.dto.request.CustomerRequest;
import br.com.importaai.ecommercepetshop.dto.response.AddressResponse;
import br.com.importaai.ecommercepetshop.dto.response.CustomerResponse;
import br.com.importaai.ecommercepetshop.model.Address;
import br.com.importaai.ecommercepetshop.model.Customer;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public Customer toEntity(CustomerRequest request) {
        Customer customer = new Customer();
        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setCpf(request.cpf());
        customer.setPhone(request.phone());
        if (request.addresses() != null) {
            request.addresses().forEach(address -> customer.addAddress(toAddressEntity(address)));
        }
        return customer;
    }

    public void updateEntity(Customer customer, CustomerRequest request) {
        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setCpf(request.cpf());
        customer.setPhone(request.phone());
        // orphanRemoval cuida de apagar os enderecos antigos removidos da colecao
        customer.getAddresses().clear();
        if (request.addresses() != null) {
            request.addresses().forEach(address -> customer.addAddress(toAddressEntity(address)));
        }
    }

    public CustomerResponse toResponse(Customer customer) {
        List<AddressResponse> addresses = customer.getAddresses().stream()
                .map(this::toAddressResponse)
                .toList();
        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getCpf(),
                customer.getPhone(),
                customer.getCreatedAt(),
                addresses);
    }

    private Address toAddressEntity(AddressRequest request) {
        Address address = new Address();
        address.setStreet(request.street());
        address.setNumber(request.number());
        address.setComplement(request.complement());
        address.setDistrict(request.district());
        address.setCity(request.city());
        address.setState(request.state());
        address.setZipCode(request.zipCode());
        return address;
    }

    private AddressResponse toAddressResponse(Address address) {
        return new AddressResponse(
                address.getId(),
                address.getStreet(),
                address.getNumber(),
                address.getComplement(),
                address.getDistrict(),
                address.getCity(),
                address.getState(),
                address.getZipCode());
    }
}
