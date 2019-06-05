package landakoop.landabus.mainapp.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import landakoop.landabus.mainapp.model.Geltokia;
import landakoop.landabus.mainapp.model.rest.GeltokiaRest;

@Transactional
public interface GeltokiaDao extends CrudRepository<Geltokia,Long>{
	List<Geltokia> findAll();
	long count();
	
	@Query(value="SELECT g.geltokiaID, g.izena, count(e.eskaeraID) as eskaerak "
			+ "from ((((ibilbidea as i join ordutegia o on i.ordutegiaID = o.ordutegiaID) "
			+ " join linea_geltokiak as lg on lg.lineaID = o.lineaID)"
			+ " join geltokia as g on lg.geltokiaID = g.geltokiaID)"
			+ " left join eskaera as e on e.ibilbideaID = i.ibilbideaID) "
			+ "where i.ibilbideaID=?1 "
			+ "group by g.geltokiaID" ,nativeQuery=true)	
	List<GeltokiaRest> getGeltokiak(long ibilbideaID);
}
