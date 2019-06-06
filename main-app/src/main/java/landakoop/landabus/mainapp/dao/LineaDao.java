package landakoop.landabus.mainapp.dao;

import org.springframework.data.repository.CrudRepository;

import landakoop.landabus.mainapp.model.Linea;

public interface LineaDao extends CrudRepository<Linea,Long> {
	
}
