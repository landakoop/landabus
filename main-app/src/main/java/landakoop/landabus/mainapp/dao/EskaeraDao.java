package landakoop.landabus.mainapp.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import landakoop.landabus.mainapp.model.Eskaera;

@Transactional
public interface EskaeraDao extends CrudRepository<Eskaera,Long>{
	List<Eskaera> findAll();

	@Query(value="SELECT (count(CASE WHEN onartua=1 THEN 1 END)/count(*))"
			+ "from eskaera "
			+ "where (date(data) = curdate())",nativeQuery=true)	
	Integer getMalgutasuna();
}
