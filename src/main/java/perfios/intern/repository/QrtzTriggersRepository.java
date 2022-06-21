package perfios.intern.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import perfios.intern.model.QrtzTriggers;
import perfios.intern.model.SchedulerJobInfo;


//@Repository
//public interface QrtzTriggersRepository  extends JpaRepository<QrtzTriggers, Long> {

	//@Query(value = "SELECT * FROM qrtz_triggers WHERE JOB_NAME=?1")
//	List<Municipalperson> findByMunicipal_idOrderByLastnameDesc(int municipal_id);
	//Optional<QrtzTriggers> findByJOB_NAME(String id);
//	QrtzTriggers findByJOBNAME(String jobName);
//}
@Repository
public interface QrtzTriggersRepository extends CrudRepository<QrtzTriggers, Long>, PagingAndSortingRepository<QrtzTriggers, Long> {
 
    @Query(value="select * from qrtz_triggers t where t.JOB_NAME= :id", nativeQuery=true)
    //List<Author> getAuthorsByFirstName(String firstName);
    Optional<QrtzTriggers> getTriggersByJobName(String id);
}