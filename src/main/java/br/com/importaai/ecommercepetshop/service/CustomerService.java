package br.com.importaai.ecommercepetshop.service;

import br.com.importaai.ecommercepetshop.dto.request.CustomerRequest;
import br.com.importaai.ecommercepetshop.dto.response.CustomerResponse;
import br.com.importaai.ecommercepetshop.exception.BusinessException;
import br.com.importaai.ecommercepetshop.exception.ResourceNotFoundException;
import br.com.importaai.ecommercepetshop.mapper.CustomerMapper;
import br.com.importaai.ecommercepetshop.model.Customer;
import br.com.importaai.ecommercepetshop.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper mapper;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper mapper) {
        this.customerRepository = customerRepository;
        this.mapper = mapper;
    }

    @Transactional
    public CustomerResponse create(CustomerRequest request) {
        if (customerRepository.existsByEmail(request.email())) {
            throw new BusinessException("Ja existe um cliente com o e-mail '" + request.email() + "'");
        }
        if (customerRepository.existsByCpf(request.cpf())) {
            throw new BusinessException("Ja existe um cliente com o CPF '" + request.cpf() + "'");
        }
        Customer saved = customerRepository.save(mapper.toEntity(request));
        return mapper.toResponse(saved);
    }

    public Page<CustomerResponse> findAll(Pageable pageable) {
        return customerRepository.findAll(pageable).map(mapper::toResponse);
    }

    public CustomerResponse findById(Long id) {
        return mapper.toResponse(getEntity(id));
    }

    @Transactional
    public CustomerResponse update(Long id, CustomerRequest request) {
        Customer customer = getEntity(id);
        if (customerRepository.existsByEmailAndIdNot(request.email(), id)) {
            throw new BusinessException("Ja existe um cliente com o e-mail '" + request.email() + "'");
        }
        if (customerRepository.existsByCpfAndIdNot(request.cpf(), id)) {
            throw new BusinessException("Ja existe um cliente com o CPF '" + request.cpf() + "'");
        }
        mapper.updateEntity(customer, request);
        return mapper.toResponse(customerRepository.save(customer));
    }

    @Transactional
    public void delete(Long id) {
        Customer customer = getEntity(id);
        customerRepository.delete(customer);
    }

    private Customer getEntity(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.of("Cliente", id));
    }
}
