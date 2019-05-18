package landakoop.landabus.mainapp.dao;

import org.springframework.data.repository.CrudRepository;

import landakoop.landabus.mainapp.model.Distantzia;

public interface DistantziaDao extends CrudRepository<Distantzia,Long> {
	
	Iterable<Distantzia> findAll();
}
