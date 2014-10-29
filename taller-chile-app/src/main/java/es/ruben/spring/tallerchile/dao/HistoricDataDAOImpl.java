package es.ruben.spring.tallerchile.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import es.ruben.spring.tallerchile.service.model.HistoricData;

@Component
public class HistoricDataDAOImpl implements HistoricDataDAO {
	
	@Resource
	private DataSource dataSource;
	
	private class HistoricDataRowMapper implements RowMapper<HistoricData> {
		
		@Override
		public HistoricData mapRow(ResultSet rs, int rowNum) throws SQLException {
			HistoricData data = new HistoricData();
			data.setId(rs.getString("id"));
			data.setForecastDate(rs.getDate("forecastDate"));
			data.setForecast(rs.getString("forecast"));
			data.setCity(rs.getString("city"));
			
			return data;
		}
			
	}

	@Override
	public void save(HistoricData data) {
		String query = "insert into HistoricData (id, forecastDate, forecast, city) values (?,?,?,?)";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(query, data.getId(), data.getForecastDate(), data.getForecast(), data.getCity());
	
	}

	@Override
	public HistoricData getById(String id) {
		
		String query = "select * from HistoricData where id = ?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<HistoricData> results = jdbcTemplate.query(query, new HistoricDataRowMapper(), id);
		
		if(results.size() == 0) {
			return null;
		}
		else if(results.size() == 1) {
			return results.get(0);
		}
		else {
			throw new RuntimeException("Existen varios HistoricData con el mismo ID. Parece que la base de datos es"
					+ "inconsistente");
		}
	}

	@Override
	public List<HistoricData> getByDate(Date date) {
		
		String query = "select * from HistoricData where forecastDate = ?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<HistoricData> results = jdbcTemplate.query(query, new HistoricDataRowMapper(), date);
		
		return results;
	}

}
