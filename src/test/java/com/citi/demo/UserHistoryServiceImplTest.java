package com.citi.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.annotation.Rollback;


import com.citi.demo.model.UserHistory;
import com.citi.demo.service.SectorStocksService;
import com.citi.demo.service.StockWrapperServiceImpl;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserHistoryServiceImplTest {

	 @Autowired
		JdbcTemplate template;
	    SectorStocksService sector;
	    private static final Logger logger = LogManager.getLogger(BackendappApplication.class);
	    
	    @Test
		public void saveUserHistoryByuserId() {
			// TODO Auto-generated method stub
			String userId = "XYZ";
			String companySymbol = "TCS.NS";
			long quantity = 5;
			
			UserHistory share = new UserHistory();
//			try
//			{
				System.out.println(userId);
				System.out.println(companySymbol);
				System.out.println(quantity);
				String sector1 = sector.findSectorByCompanySymbol(companySymbol);
				System.out.println(sector1);
				StockWrapperServiceImpl.findStock(companySymbol);
				
				int added = template.update("insert into user_history(company_symbol,price,sector,user_id,volume) values(?,?,?,?,?)",
						companySymbol,StockWrapperServiceImpl.findStock(companySymbol).getPrice(),sector,userId,quantity);
				logger.info("Insertion Successful for User: "+userId);
				 if(added ==1)
					 {
						share.setUserId(userId);
						share.setPrice(StockWrapperServiceImpl.findStock(companySymbol).getPrice());
						share.setCompanySymbol(companySymbol);
						share.setSector(sector1);
						share.setVolume(StockWrapperServiceImpl.findStock(companySymbol).getVolume());
						System.out.println(share);
						
					 }
//			}
//			catch(Exception e)
//			{
//				logger.info("Insertion could not be done!");
//				
//			}
			assertThat(share).isNotNull();
			
		}
//	    public void testFinddataByuserId()
//	    {
//	    	String userId = "Rhythm";
//	    	String FINDSHARES = "select * from user_history where user_id=?";
//	    	try
//	    	{
//			List<UserHistory> shares = template.query(FINDSHARES, new RowMapper<UserHistory>() {
//
//				@Override
//				public UserHistory mapRow(ResultSet set, int arg1) throws SQLException {
//					// TODO Auto-generated method stub
//					return new UserHistory(set.getString(1),set.getBigDecimal(2),set.getString(3),set.getLong(4));
//				}
//
//			}, userId);
//			if(shares!=null)
//			System.out.println("\n\t"+shares);
//			
//			assertThat(shares).isNotNull();
//	    	}
//	    	catch(Exception e)
//	    	{
//	    		logger.info("User does not exist!");
//	    	}
//	    	
//			
//	    }
	    
//	    @Test
//	    public void testAdddataByuserId()
//	    {
//		  
//	    	String userId = "Sakshi";
//	    	BigDecimal price = new BigDecimal("0.05");
//	    	String companySymbol = "Dr Reddy's";
//	    	long volume = 20;
//	    	UserHistory share = new UserHistory();
//	 
//	    	share.setUserId(userId);
//	    	share.setPrice(price);
//	    	share.setCompanySymbol(companySymbol);
//	    	share.setVolume(volume);
//	    	try {
//	    	int added = template.update("insert into user_history(user_id,price,company_symbol,volume) values(?,?,?,?)",
//					 share.getUserId(),share.getPrice(),share.getCompanySymbol(),share.getVolume());
//	    	
//	    	if(added==1) {
//				 
//				// return dao.findSectorsByuserId(share.getUserId());
//				 logger.info("New Entry added  \n\tUserId - "+share.getUserId()+" \n\tPrice - "+share.getPrice()+" \n\tStock- "+share.getCompanySymbol()+" \n\tVolume - "+share.getVolume());
//				 
//				 }
//			}
//			catch(Exception e)
//			{
//				logger.info(" already in use!.");
//			}
//	    	assertThat(share).isNotNull();
//			
//	    }
	    

}
