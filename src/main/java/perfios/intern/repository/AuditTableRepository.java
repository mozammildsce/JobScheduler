package perfios.intern.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import perfios.intern.model.AuditTable;


@Repository
public interface AuditTableRepository extends JpaRepository<AuditTable, Long> {

	List<AuditTable> findByJobName(String jobName);
	List<AuditTable> findByexecutionDateBetween(LocalDate fromDate,LocalDate endDate);
			
	
	@Query(value="select *from audit_table a where a.job_name like %:keyword%",nativeQuery=true)
	List<AuditTable> findByKeyword(@Param("keyword") String keyword);
}