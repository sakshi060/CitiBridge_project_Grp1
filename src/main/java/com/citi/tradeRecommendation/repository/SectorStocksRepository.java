package com.citi.tradeRecommendation.repository;


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

import com.citi.tradeRecommendation.BackendappApplication;
import com.citi.tradeRecommendation.model.SectorStocks;

@Repository
public class SectorStocksRepository {  

	private static final Logger logger = LogManager.getLogger(BackendappApplication.class);

	@Autowired
	JdbcTemplate template;
	double sum;


	public ArrayList<SectorStocks> findCompanyBySector(String sector)  {
		// Returns Companies of sector passed as an argument from the database.

		logger.info("Fetching Companies under Sector - " +sector);
		ArrayList<SectorStocks> sectorCompanies = null;
		try
		{
			String FINDSHARES = "select * from sector_stocks where sector=?";
			sectorCompanies = (ArrayList<SectorStocks>) template.query(FINDSHARES, new RowMapper<SectorStocks>() {

				@Override
				public SectorStocks mapRow(ResultSet set, int arg1) throws SQLException {
					SectorStocks sectorStocks = new SectorStocks();
					sectorStocks.setCompanySymbol(set.getString(1));
					sectorStocks.setCompanyName(set.getString(2));
					sectorStocks.setSector(set.getString(3));
					return sectorStocks;
				}
			}, sector);	
			if(sectorCompanies.size()!=0)
			{
				return sectorCompanies;
			}
			else
				logger.error("Error occured in fetching Company Symbols");
			return null;
		}
		catch(Exception e)
		{
			logger.error("Error occured in fetching Company Symbols",e.getMessage());
			return null;
		}
	}

	public List<String> findCompanySymbolBySector(String sector) {
		// Returns Company Symbols of sector passed as an argument from the database.

		logger.info("Fetching Company Symbols of Companies under Sector - " +sector);
		List<String> sectorCompanies = null;
		try
		{
			String FINDSHARES = "select company_symbol from sector_stocks where sector=?";
			sectorCompanies = template.query(FINDSHARES, new RowMapper<String>() {

				@Override
				public String mapRow(ResultSet set, int arg1) throws SQLException {
					SectorStocks sectorStocks = new SectorStocks();
					sectorStocks.setCompanySymbol(set.getString(1));
					return sectorStocks.getCompanySymbol();
				}
				
			}, sector);
			if(sectorCompanies.size()!=0)
			{
				return sectorCompanies;
			}
			else
				logger.error("Error occured in fetching Company Symbols");
			return null;
		}
		catch(Exception e)
		{
			logger.error("Error occured in fetching Company Symbols - {}", e.getMessage());
			return null;
		}
	}

	public String findSectorByCompanySymbol(String companySymbol) {
		// Returns sector of companySymbol passed as an argument from the database.

		logger.info("Fetching Sector of Company - " +companySymbol);
		String sectorCompany = null;
		try
		{
			String FINDSHARES = "select sector from sector_stocks where company_symbol=?";
			sectorCompany = template.queryForObject(FINDSHARES, new RowMapper<String>() {

				@Override
				public String mapRow(ResultSet set, int arg1) throws SQLException {
					String sector = new String();
					sector = set.getString(1);
					return sector;
				}

			}, companySymbol);

			if(sectorCompany!=null)
			{
				return sectorCompany;
			}
			else
				logger.error("Error occured in fetching Company Symbols");
			return null;
		}
		catch(Exception e)
		{
			logger.error("Error occured in fetching Sector - {}", e.getMessage());
			return null;
		}
	}

	public List<String> findDistinctSectors() {
		//Returns distinct sectors from the database.
		
		logger.info("Fetching Distict Sectors");
		List<String> sectorCompanies;
		try
		{
			String FINDSHARES = "select distinct(sector) from sector_stocks";
			sectorCompanies = template.query(FINDSHARES, new RowMapper<String>() {

				@Override
				public String mapRow(ResultSet set, int arg1) throws SQLException {
					String sectors = new String();
					sectors = set.getString(1);
					return sectors;
				}

			});
			if(sectorCompanies.size()!=0)
			{
				return sectorCompanies;
			}
			else
				logger.error("Error occured in fetching Company Symbols");
			return null;
		}
		catch(Exception e)
		{
			logger.error("Error occured in fetching Distict Sectors - {}", e.getMessage());
			return null;
		}

	}
}

