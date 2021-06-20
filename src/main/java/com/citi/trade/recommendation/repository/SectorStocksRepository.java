package com.citi.trade.recommendation.repository;


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

import com.citi.trade.recommendation.BackendappApplication;
import com.citi.trade.recommendation.model.SectorStocks;

@Repository
public class SectorStocksRepository {  

	private static final Logger logger = LogManager.getLogger(BackendappApplication.class);

	@Autowired
	JdbcTemplate template;
	double sum;


	public List<SectorStocks> findCompanyBySector(String sector)  {
		// Returns Companies of sector passed as an argument from the database.

		List<SectorStocks> sectorCompanies = null;
		try
		{
			String findShares = "select * from sector_stocks where sector=?";
			sectorCompanies = (ArrayList<SectorStocks>) template.query(findShares, new RowMapper<SectorStocks>() {

				@Override
				public SectorStocks mapRow(ResultSet set, int arg1) throws SQLException {
					SectorStocks sectorStocks = new SectorStocks();
					sectorStocks.setCompanySymbol(set.getString(1));
					sectorStocks.setCompanyName(set.getString(2));
					sectorStocks.setSector(set.getString(3));
					return sectorStocks;
				}
			}, sector);	
			if(sectorCompanies.isEmpty())
			{
				logger.info("Company symbols not found");
			}
		}
		catch(Exception e)
		{
			logger.error("Error occured in fetching Company Symbols - {}",e.getMessage());

		}
		return sectorCompanies;
	}

	public List<String> findCompanySymbolBySector(String sector) {
		// Returns Company Symbols of sector passed as an argument from the database.

		List<String> sectorCompanies = null;
		try
		{
			String findShares = "select company_symbol from sector_stocks where sector=?";
			sectorCompanies = template.query(findShares, new RowMapper<String>() {

				@Override
				public String mapRow(ResultSet set, int arg1) throws SQLException {
					SectorStocks sectorStocks = new SectorStocks();
					sectorStocks.setCompanySymbol(set.getString(1));
					return sectorStocks.getCompanySymbol();
				}

			}, sector);
			if(sectorCompanies.isEmpty())
			{
				logger.info("Company symbols not found");
			}
		}
		catch(Exception e)
		{
			logger.error("Error occured in fetching Company Symbols - {}", e.getMessage());

		}
		return sectorCompanies;
	}

	public String findSectorByCompanySymbol(String companySymbol) {
		// Returns sector of companySymbol passed as an argument from the database.

		logger.info("Fetching Sector of Company - " +companySymbol);
		String sectorCompany = null;
		try
		{
			String findShares = "select sector from sector_stocks where company_symbol=?";
			sectorCompany = template.queryForObject(findShares, new RowMapper<String>() {

				@Override
				public String mapRow(ResultSet set, int arg1) throws SQLException {
					return set.getString(1);
				}

			}, companySymbol);

		}
		catch(Exception e)
		{
			logger.error("Error occured in fetching Sector - {}", e.getMessage());

		}
		return sectorCompany;
	}

	public List<String> findDistinctSectors() {
		//Returns distinct sectors from the database.

		logger.info("Fetching Distict Sectors");
		List<String> sectorCompanies = new ArrayList<>();
		try
		{
			String findShares = "select distinct(sector) from sector_stocks";
			sectorCompanies = template.query(findShares, new RowMapper<String>() {

				@Override
				public String mapRow(ResultSet set, int arg1) throws SQLException {
					return set.getString(1); //sector
				}

			});
			if(sectorCompanies.isEmpty())
				logger.info("No company symbols were found");

		}
		catch(Exception e)
		{
			logger.error("Error occured in fetching Distict Sectors - {}", e.getMessage());

		}
		return sectorCompanies;

	}
}

