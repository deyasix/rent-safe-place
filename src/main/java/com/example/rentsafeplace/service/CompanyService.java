package com.example.rentsafeplace.service;

import com.example.rentsafeplace.api.model.*;
import com.example.rentsafeplace.api.repository.CompanyRepository;
import com.example.rentsafeplace.api.security.SecurityConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private RealtorService realtorService;

    @Autowired
    private TenantService tenantService;

    @Autowired
    private SubscriptionService subscriptionService;

    public Company getCompany(Long id) {
        return companyRepository.findById(id).orElse(null);
    }

    public ArrayList<Company> getAll() {
        return new ArrayList<>(companyRepository.findAll());
    }

    public Company addCompany(Company company) {
        try {
            List<Realtor> realtors = realtorService.getAll();
            List<Tenant> tenants = tenantService.getAll();
            boolean isNameNotAvailable = realtors.stream().anyMatch(r -> r.getEmail().equals(company.getEmail()))
                    || tenants.stream().anyMatch(t -> t.getEmail().equals(company.getEmail()) || getAll().stream()
                            .anyMatch(c -> c.getEmail().equals(company.getEmail())));
            if (!isNameNotAvailable) {
                SecurityConfiguration.addCompanyUser(company);
                return companyRepository.save(company);
            } else return null;

        } catch (Exception exception) {
            return null;
        }
    }

    public Company updateCompany(Long id, Company company) {
        Optional<Company> companyData = companyRepository.findById(id);
        if (companyData.isPresent()) {
            Company _company = companyData.get();
            if (company.getName() != null) {
                _company.setName(company.getName());
            }
            if (company.getPassword() != null) {
                _company.setPassword((company.getPassword()));
            }
            if (company.getEmail() != null) {
                _company.setEmail(company.getEmail());
            }
            return companyRepository.save(_company);
        } else {
            return null;
        }
    }

    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }

    public Company login(Company company) throws Exception {
        Company com = getByEmail(company.getEmail());
        if (com == null) throw new Exception("No such user!");
        if (com.getPassword().equals(company.getPassword())) {
            return com;
        } else throw new Exception("Wrong password!");
    }

    public Company getByEmail(String email) {
        List<Company> companies = getAll();
        for (Company company : companies) {
            if (company.getEmail().equals(email)) {
                return company;
            }
        }
        return null;
    }

    public Subscription getSubscription(Company company) {
        List<Subscription> subscriptions = subscriptionService.getAll();
        if (subscriptions != null) {
            for (Subscription subscription : subscriptions) {
                if (subscription.getCompany().equals(company)) {
                    return subscription;
                }
            }
        } return null;
    }

    public Subscription subscribe(Subscription subscription) {
        return subscriptionService.addSubscription(subscription);
    }

    public Subscription editSubscription(Subscription subscription) {
        List<Subscription> subscriptions = subscriptionService.getAll();
        for(Subscription sub : subscriptions) {
            if (sub.getCompany().equals(subscription.getCompany())) {
                return subscriptionService.updateSubscription(sub.getId(), subscription);
            }
        }
        return null;
    }

    public void cancelSubscription(Company company) {
        List<Subscription> subscriptions = subscriptionService.getAll();
        for(Subscription subscription : subscriptions) {
            if (subscription.getCompany().equals(company)) {
                subscriptionService.deleteSubscription(subscription.getId());
            }
        }
    }

    public Realtor createRealtor(Realtor realtor) {
        return realtorService.addRealtor(realtor);
    }

    public Realtor editRealtor(Long id, Realtor realtor, Company company) {
        Realtor real = realtorService.getRealtor(id);
        if (real.getCompany().equals(company)) {
            return realtorService.updateRealtor(id, realtor);
        } else return null;
    }

    public List<Realtor> getRealtors(Company company) {
        List<Realtor> companyRealtors = new ArrayList<>();
        List<Realtor> realtors = realtorService.getAll();
        for (Realtor real : realtors) {
            if (real.getCompany().equals(company)) {
                companyRealtors.add(real);
            }
        }
        return companyRealtors;
    }

    public Realtor getRealtor(Long id, Company company) {
        Realtor realtor = realtorService.getRealtor(id);
        if (realtor.getCompany().equals(company)) {
            return realtorService.getRealtor(id);
        }
        else return null;
    }

    public void deleteRealtor(Long id, Company company) {
        Realtor realtor = realtorService.getRealtor(id);
        if (realtor.getCompany().equals(company)) {
            realtorService.deleteRealtor(id);
        }
    }
}
