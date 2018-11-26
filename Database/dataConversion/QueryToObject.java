package dataConversion;
import Accounts.*;
import Utility.*;
import java.sql.*;
/**
 * 
 * @author Rokas
 * For converting query results to an Object, so that it could be used
 * in GUI display.
 * Should be static?
 */
public final class QueryToObject {
	
	public static Student rowToStudent(ResultSet res) {
		String userId, name, password, title, email, tutor;
		char permission;
		Degree degree; PeriodOfStudy periodOfStudy;
			
			res.next(); userId = res.getString("userId");
			res.next();name = res.getString("name");
			res.next();password = res.getString("password");
			res.next();res.getString("permission").charAt(0);
			res.next();title = res.getString("title");
			/*How to get degree from Student?
			 *  By linking Degree and Student tables?
			 */
			res.next();email = res.getString("email");
			res.next();tutor = res.getString("tutor");
			res.next();periodOfStudy = res.getString("period");
			//Pull periodOfStudy from PeriodOfStudy table
			
		
		Student student = new Student(userId, name, password, permission, title, degree,
				email, tutor, periodOfStudy);
		
		return student;
	}
	
	public static Degree rowToDegree(ResultSet res) {
		while(res.next()) {
			
		}
		Degree degree = new Degree(/*Parameters*/);
		return degree;
	}
	
	public static PeriodOfStudy rowToPeriod(ResultSet res) {
		while(res.next()) {
			char label = res.getString("label").charAt(0); //converting to char
			char levelOfStudy = res.getString("level").charAt(0);
			String startDate = res.getString("start-date"); //Isn't it type Date?
			String endDate = res.getString("end-date"); 
			PeriodOfStudy poS =
					new PeriodOfStudy(label, levelOfStudy, startDate, endDate);
		}
		
		return poS;
	}
}
