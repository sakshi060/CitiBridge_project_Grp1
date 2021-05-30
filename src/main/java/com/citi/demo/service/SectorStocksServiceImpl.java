package com.citi.demo.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.citi.demo.BackendappApplication;
import com.citi.demo.model.SectorAvg;
import com.citi.demo.model.SectorStocks;
import com.citi.demo.service.StockWrapperServiceImpl;


@Repository(value="dao1")
@Service
public class SectorStocksServiceImpl implements SectorStocksService {

	private static final Logger logger = LogManager.getLogger(BackendappApplication.class);

	@Autowired
	JdbcTemplate template;
	double sum;

	@Override
	public ArrayList<SectorStocks> findCompanyBySector(String sector) {
		// TODO Auto-generated method stub
		try
		{
			String FINDSHARES = "select * from sector_stocks where sector=?";
			ArrayList<SectorStocks> sectorCompanies = (ArrayList<SectorStocks>) template.query(FINDSHARES, new RowMapper<SectorStocks>() {

				@Override
				public SectorStocks mapRow(ResultSet set, int arg1) throws SQLException {
					// TODO Auto-generated method stub
					return new SectorStocks(set.getString(1),set.getString(2),set.getString(3));
				}

			}, sector);
			
			logger.info("Companies under Sector : "+sector+" found!");
			return sectorCompanies;
		}
		catch(Exception e)
		{
			logger.info("Companies under Sector could not be found!");
			return null;
		}
	}

	@Override
	public List<String> findCompanySymbolBySector(String sector) {
		// TODO Auto-generated method stub
		try
		{
			String FINDSHARES = "select company_symbol from sector_stocks where sector=?";
			List<String> sectorCompanies = template.query(FINDSHARES, new RowMapper<String>() {

				@Override
				public String mapRow(ResultSet set, int arg1) throws SQLException {
					// TODO Auto-generated method stub
					return new String(set.getString(1));
				}

			}, sector);

			logger.info("Companies under Sector : "+sector+" found!");
			return sectorCompanies;
		}
		catch(Exception e)
		{
			logger.info("Companies under Sector could not be found!");
			return null;
		}
	}

	@Override
	public String findSectorByCompanySymbol(String companySymbol) {
		// TODO Auto-generated method stub
		try
		{
			String FINDSHARES = "select sector from sector_stocks where company_symbol=?";
			List<String> sectorCompanies = template.query(FINDSHARES, new RowMapper<String>() {

				@Override
				public String mapRow(ResultSet set, int arg1) throws SQLException {
					// TODO Auto-generated method stub
					return new String(set.getString(1));
				}

			}, companySymbol);

			logger.info("Company : "+companySymbol+" belongs to Sector :"+sectorCompanies.get(0));
			String sector = sectorCompanies.get(0);
			return sector;
		}
		catch(Exception e)
		{
			return null;
		}
	}



	@Override
	public List<SectorAvg> getSectorWiseGrowth(){
		try
		{
			List<SectorAvg> sectorWiseGrowth = new ArrayList<>();
			List<String> sectors = getDistinctSectors();
			System.out.println(sectors);

			if(!ObjectUtils.isEmpty(sectors)) {
				sectors.forEach(sector -> {
					List<String> symbols = findCompanySymbolBySector(sector);
					if(!ObjectUtils.isEmpty(symbols)) {
						sum = 0;
						symbols.forEach(symbol -> {
							sum += StockWrapperServiceImpl.findStock(symbol).getChange().doubleValue();	
						});

					}
					SectorAvg obj = new SectorAvg();
					obj.setAvggrowth(sum/symbols.size());
					obj.setSector(sector);
					sectorWiseGrowth.add(obj);

				});

			}
			logger.info("Sector Wise Avg Growth!");
			return sectorWiseGrowth;
		}
		catch(Exception e)
		{
			logger.info("Could not find Sector Wise Avg Growth!");
			return null;
		}
	}

	@Override
	public List<String> getDistinctSectors() {
		// TODO Auto-generated method stub
		try
		{
			String FINDSHARES = "select distinct(sector) from sector_stocks";

			List<String> sectorCompanies = template.query(FINDSHARES, new RowMapper<String>() {

				@Override
				public String mapRow(ResultSet set, int arg1) throws SQLException {
					// TODO Auto-generated method stub
					return new String(set.getString(1));
				}

			});
			System.out.println(sectorCompanies);
			return sectorCompanies;

		}
		catch(Exception e)
		{
			return null;
		}
	}
}



