package landakoop.landabus.mainapp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import landakoop.landabus.mainapp.model.Ibilbidea;
import landakoop.landabus.mainapp.model.rest.ErabileraRest;
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

	@Query(value="SELECT i.ibilbideaID, o.irteeraOrdua, o.helmugaOrdua, count(lg.geltokiaID) as geldialdiak, o.finkoa "
			+ "from (((ibilbidea as i join ordutegia as o on i.ordutegiaID = o.ordutegiaID) "
			+ "join linea_geltokiak as lg on lg.lineaID = o.lineaID) "
			+ "join geltokia as g on g.geltokiaId = lg.geltokiaID)"
			+ "where ISNULL(o.data) OR o.data = CURDATE() "
			+ "group by i.ibilbideaID",nativeQuery=true)	
	Iterable<IbilbideaRest> getIbilbideakGaur();
	
	@Query(value="SELECT i.ibilbideaID, o.irteeraOrdua, o.helmugaOrdua, count(lg.geltokiaID) as geldialdiak, o.finkoa "
			+ "from (((ibilbidea as i join ordutegia as o on i.ordutegiaID = o.ordutegiaID) "
			+ "join linea_geltokiak as lg on lg.lineaID = o.lineaID) "
			+ "join geltokia as g on g.geltokiaId = lg.geltokiaID)"
			+ "where ISNULL(o.data) OR o.data = (CURDATE()+1) "
			+ "group by i.ibilbideaID",nativeQuery=true)	
	Iterable<IbilbideaRest> getIbilbideakBihar();
	
	@Query(value="SELECT count(igb.ekintza) as bidaiariak, count(CASE WHEN o.finkoa THEN 1 END) as finkoak,count(CASE WHEN NOT o.finkoa THEN 1 END) as malguak, DATE(igb.noiz) as data "
			+ "from ((ibilbidea_geltokia_bidaiaria as igb "
			+ " join ibilbidea as i on i.ibilbideaID = igb.ibilbideaID) "
			+ " join ordutegia as o on i.ordutegiaID = o.ordutegiaID) "
			+ "group by DATE(igb.noiz)",nativeQuery=true)	
	List<ErabileraRest> getErabilera();
}
