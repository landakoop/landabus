package landakoop.landabus.mainapp.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import landakoop.landabus.mainapp.model.Ibilbidea;

@Service
public interface IbilbideaDao extends CrudRepository<Ibilbidea,Long> {
	Iterable<Ibilbidea> findAll();
}
