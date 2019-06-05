package landakoop.landabus.mainapp.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import landakoop.landabus.mainapp.model.Ibilbidea;
import landakoop.landabus.mainapp.model.rest.IbilbideaRest;

@Service
public interface IbilbideaDao extends CrudRepository<Ibilbidea,Long> {
	Iterable<Ibilbidea> findAll();
	
	@Query(value="SELECT i.ibilbideaID, o.irteeraOrdua, o.helmugaOrdua, count(lg.geltokiaID) as geldialdiak, o.finkoa "
			+ "from (((ibilbidea as i join ordutegia as o on i.ordutegiaID = o.ordutegiaID) "
			+ "join linea_geltokiak as lg on lg.lineaID = o.lineaID) "
			+ "join geltokia as g on g.geltokiaId = lg.geltokiaID)"
			+ "group by i.ibilbideaID",nativeQuery=true)	
	Iterable<IbilbideaRest> getIbilbideak();
}
