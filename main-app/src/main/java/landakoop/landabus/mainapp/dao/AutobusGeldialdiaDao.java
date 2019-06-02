package landakoop.landabus.mainapp.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import landakoop.landabus.mainapp.model.AutobusGeldialdia;

@Service
public interface AutobusGeldialdiaDao extends CrudRepository<AutobusGeldialdia,Long> {
	 
}
