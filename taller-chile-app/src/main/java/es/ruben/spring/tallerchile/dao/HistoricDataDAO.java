package es.ruben.spring.tallerchile.dao;

import java.util.Date;
import java.util.List;

import es.ruben.spring.tallerchile.service.model.HistoricData;

public interface HistoricDataDAO {
	
	public void save(HistoricData data);
	public HistoricData getById(String id);
	public List<HistoricData> getByDate(Date date);

}
