package perfios.intern.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import perfios.intern.model.Tenant;

public interface TenantRepository extends JpaRepository<Tenant, String> {
	
}
