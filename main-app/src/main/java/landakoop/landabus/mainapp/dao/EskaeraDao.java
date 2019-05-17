package landakoop.landabus.mainapp.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import landakoop.landabus.mainapp.model.Eskaera;

@Transactional
public interface EskaeraDao extends CrudRepository<Eskaera,Long>{
	List<Eskaera> findAll();
}
