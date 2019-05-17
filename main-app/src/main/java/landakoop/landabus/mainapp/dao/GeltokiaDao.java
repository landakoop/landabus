package landakoop.landabus.mainapp.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import landakoop.landabus.mainapp.model.Geltokia;

@Transactional
public interface GeltokiaDao extends CrudRepository<Geltokia,Long>{
	List<Geltokia> findAll();
}
