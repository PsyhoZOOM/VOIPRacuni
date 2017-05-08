package classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by PsyhoZOOM@gmail.com on 5/8/17.
 */
public class getCSVUserData {


    public static ArrayList<CSVData> getData(int userID, String accountPhone, String startDate, String endDate, Database db) {
        ArrayList<CSVData> csvDataArrayList = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;
        String query = "SELECT * FROM csv WHERE account=? AND connectTime >= ? AND connectTime <= ? ";
        try {
            ps = db.connection.prepareStatement(query);
            ps.setString(1, accountPhone);
            ps.setString(2, startDate + " 00:00:00");
            ps.setString(3, endDate + " 59:59:59");
            System.out.println(ps.toString());
            rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    CSVData csvData = new CSVData();
                    csvData.setId(rs.getInt("id"));
                    csvData.setAccount(rs.getString("account"));
                    csvData.setFrom(rs.getString("from"));
                    csvData.setTo(rs.getString("to"));
                    csvData.setCountry(rs.getString("country"));
                    csvData.setDescription(rs.getString("description"));
                    csvData.setConnectTime(rs.getString("connectTime"));
                    csvData.setChargedTimeMinSec(rs.getString("chargedTimeMS"));
                    csvData.setChargedTimeSec(rs.getInt("chargedTimeS"));
                    csvData.setChargedAmountRSD(rs.getDouble("chargedAmountRSD"));
                    csvData.setServiceName(rs.getString("serviceName"));
                    csvData.setChargedQuantity(rs.getInt("chargedQuantity"));
                    csvData.setServiceUnit(rs.getString("serviceUnit"));
                    csvData.setCustomerID(rs.getString("customerID"));
                    csvData.setFileName(rs.getString("fileName"));
                    csvDataArrayList.add(csvData);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return csvDataArrayList;
    }
}
