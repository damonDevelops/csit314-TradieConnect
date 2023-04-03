package CSIT3214.GroupProject.Service;

import CSIT3214.GroupProject.DataAccessLayer.AcceptServiceRequestDTO;
import CSIT3214.GroupProject.DataAccessLayer.CustomerRepository;
import CSIT3214.GroupProject.DataAccessLayer.ServiceProviderRepository;
import CSIT3214.GroupProject.DataAccessLayer.ServiceRequestRepository;
import CSIT3214.GroupProject.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class ServiceRequestService {

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ServiceProviderRepository serviceProviderRepository;

    public List<ServiceRequest> findAllServiceRequests() {
        return serviceRequestRepository.findAll();
    }

    public ServiceRequest findServiceRequestById(Long id) {
        return serviceRequestRepository.findById(id).orElse(null);
    }

    public ServiceRequest createServiceRequest(Long customerId, Skill serviceType, ServiceRequest serviceRequest) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isPresent()) {
            serviceRequest.setCustomer(customer.get());
            serviceRequest.setServiceType(serviceType);
            serviceRequest.setStatus(OrderStatus.CREATED);
            serviceRequest.setRequestedTime(LocalDateTime.now());
            return serviceRequestRepository.save(serviceRequest);
        } else {
            return null;
        }
    }

    public ServiceRequest acceptServiceRequest(Long serviceProviderId, Long serviceRequestId, AcceptServiceRequestDTO dto) {
        Optional<ServiceProvider> serviceProvider = serviceProviderRepository.findById(serviceProviderId);
        Optional<ServiceRequest> serviceRequest = serviceRequestRepository.findById(serviceRequestId);

        if (serviceProvider.isPresent() && serviceRequest.isPresent()) {
            ServiceRequest requestToUpdate = serviceRequest.get();
            requestToUpdate.setServiceProvider(serviceProvider.get());

            // Get the customer from the service request
            Customer customer = requestToUpdate.getCustomer();

            // Check if the customer has a CLIENT_SUBSCRIPTION membership type
            if (customer.getMembership() != null &&
                    customer.getMembership().getMembershipType() == MembershipType.CLIENT_SUBSCRIPTION) {
                requestToUpdate.setCost(0.0);
            } else {
                requestToUpdate.setCost(dto.getCost());
            }

            requestToUpdate.setStatus(OrderStatus.QUOTED_AWAITING_CUSTOMER_APPROVAL);

            LocalDateTime scheduledTime = dto.getDate().atTime(dto.getStartTime());
            requestToUpdate.setScheduledTime(scheduledTime);

            return serviceRequestRepository.save(requestToUpdate);
        } else {
            return null;
        }
    }

    public ServiceRequest customerAcceptsRequest(Long serviceRequestId) {
        Optional<ServiceRequest> serviceRequest = serviceRequestRepository.findById(serviceRequestId);

        if (serviceRequest.isPresent()) {
            ServiceRequest requestToUpdate = serviceRequest.get();
            if (requestToUpdate.getStatus() == OrderStatus.QUOTED_AWAITING_CUSTOMER_APPROVAL) {
                requestToUpdate.setStatus(OrderStatus.ACCEPTED);
                return serviceRequestRepository.save(requestToUpdate);
            } else {
                throw new IllegalStateException("Service request must be in the QUOTED status to be accepted.");
            }
        } else {
            return null;
        }
    }

}