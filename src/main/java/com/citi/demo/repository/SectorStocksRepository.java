package com.citi.demo.repository;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


import com.citi.demo.model.SectorStocks;

@Repository
public class SectorStocksRepository {

	@Autowired
	JdbcTemplate template;
	double sum;


	public ArrayList<SectorStocks> findCompanyBySector(String sector) {
		// TODO Auto-generated method stub
		// Returns Companies of sector passed as an argument from the database.
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


			return sectorCompanies;
		}
		catch(Exception e)
		{

			return null;
		}
	}


	public List<String> findCompanySymbolBySector(String sector) {
		// TODO Auto-generated method stub
		// Returns Company Symbols of sector passed as an argument from the database.
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

			return sectorCompanies;
		}
		catch(Exception e)
		{
			return null;
		}
	}


	public String findSectorByCompanySymbol(String companySymbol) {
		// TODO Auto-generated method stub
		// Returns sector of companySymbol passed as an argument from the database.
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

			String sector = sectorCompanies.get(0);
			return sector;
		}
		catch(Exception e)
		{
			return null;
		}
	}


	public List<String> findDistinctSectors() {
		// TODO Auto-generated method stub
		//Returns distinct sectors from the database.
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
			return sectorCompanies;

		}
		catch(Exception e)
		{
			return null;
		}
	}
}
