package landakoop.landabus.mainapp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import landakoop.landabus.mainapp.model.AurrekoGeltokia;
import landakoop.landabus.mainapp.model.AutobusGeldialdia;
import landakoop.landabus.mainapp.model.GeldialdiEkintza;

@Service
public interface AutobusGeldialdiaDao extends CrudRepository<AutobusGeldialdia,Long> {
	Iterable<AutobusGeldialdia> findAll();
	@Query(value="select count(*) as kopurua, min(g.noiz) as data, o.lineaID as linea, i.eguraldia as eguraldia "
			+ "from ((ibilbidea as i join ibilbidea_geltokia_bidaiaria as g on i.ibilbideaID = g.ibilbideaID)"
			+ " join ordutegia o on o.ordutegiaID=i.ordutegiaID) "
			+ "where g.ekintza=?1 and g.geltokiaID=?2 "
			+ "group by g.ibilbideaID "
			+ "order by g.ibilbideaID",nativeQuery=true)
	List<GeldialdiEkintza> getGeldialdiaEkintzak(String ekintza,long geltokiaID);
	
	@Query(value="select g.geltokiaID as geltokia, if(((lg.geltokiaID is null) or (lg.posizioa > (select posizioa from linea_geltokiak where geltokiaID=?2 and lineaID=?1))),'false','true') as pasatu "
			+ "from (geltokia as g left join (select * from linea_geltokiak where lineaId=?1) as lg on g.geltokiaID=lg.geltokiaID) " 
			+ "where g.geltokiaID <> ?2 "
			+ "order by g.geltokiaID", nativeQuery=true)
	List<AurrekoGeltokia> getAurrekoGeltokiakJaitsi(long lineaID,long geltokiaID);
	
	@Query(value="select g.geltokiaID as geltokia, if(((lg.geltokiaID is null) or (lg.posizioa < (select posizioa from linea_geltokiak where geltokiaID=?2 and lineaID=?1))),'false','true') as pasatu "
			+ "from (geltokia as g left join (select * from linea_geltokiak where lineaId=?1) as lg on g.geltokiaID=lg.geltokiaID) " 
			+ "where g.geltokiaID <> ?2 "
			+ "order by g.geltokiaID", nativeQuery=true)
	List<AurrekoGeltokia> getAurrekoGeltokiakIgo(long lineaID,long geltokiaID);
	
}
