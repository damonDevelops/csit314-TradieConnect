package CSIT3214.GroupProject.DataAccessLayer;

import CSIT3214.GroupProject.Model.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {
}