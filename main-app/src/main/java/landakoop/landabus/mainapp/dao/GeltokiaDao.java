package landakoop.landabus.mainapp.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import landakoop.landabus.mainapp.model.Geltokia;
import landakoop.landabus.mainapp.model.rest.GeltokiaRest;
import landakoop.landabus.mainapp.model.rest.GeltokiaRest2;

@Transactional
public interface GeltokiaDao extends CrudRepository<Geltokia,Long>{
	List<Geltokia> findAll();
	long count();
	
	@Query(value="SELECT g.geltokiaID, g.izena, g.x, g.y, lg.posizioa,"
			+ "          (select count(*) from eskaera where ibilbideaID=i.ibilbideaID) as eskaerak, "
			+ "          (select (ifnull(sum((select denbora from distantzia where geltokiaA=(select geltokiaID from linea_geltokiak where posizioa=lg2.posizioa-1 and lineaID=lg.lineaID) "
			+ "                                                            and geltokiaB=lg2.geltokiaID)),0)+o.irteeraOrdua) from linea_geltokiak lg2 where posizioa <= lg.posizioa and lineaID=lg.lineaID) as ordua "
			+ "from (((ibilbidea as i join ordutegia o on i.ordutegiaID = o.ordutegiaID) "
			+ " join linea_geltokiak as lg on lg.lineaID = o.lineaID)"
			+ " join geltokia as g on lg.geltokiaID = g.geltokiaID) "
			+ "where i.ibilbideaID=?1 "
			+ "order by lg.posizioa" ,nativeQuery=true)	
	List<GeltokiaRest> getGeltokiak(long ibilbideaID);
	
	@Query(value="SELECT g.geltokiaID, g.izena "
			+ "from geltokia as g left join linea_geltokiak as lg on lg.geltokiaID = g.geltokiaID "
			+ "where lg.lineaID=?1", nativeQuery=true)
	List<GeltokiaRest2> getGeltokiakLinea(long lineaID);
	
	@Query(value="SELECT g.geltokiaID, g.izena " 
			+ "from (SELECT * from linea_geltokiak where lineaID=?1) "
			+ "as lg right join geltokia as g on lg.geltokiaID = g.geltokiaID "
			+ "where lg.lineaID is null", nativeQuery=true)
	List<GeltokiaRest2> getGeltokiakEzLinea(long lineaID);
	
}
