package org.myhealth.DAO;
import java.io.FileWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.myhealth.model.Health_Record;

public class Health_Record_Operations {

    // Adds a new health record to the database
    public boolean addRecord(Health_Record record) {

        String sql = """
                INSERT INTO health_records
                (user_id, weight, temperature, blood_pressure, note, record_date)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection connect = DB_connections.getConnnection();
             PreparedStatement preparedStatement1 = connect.prepareStatement(sql)) {

            preparedStatement1.setInt(1, record.getUserId());
            preparedStatement1.setString(2, record.getWeight());
            preparedStatement1.setString(3, record.getTemperature());
            preparedStatement1.setString(4, record.getBloodPressure());
            preparedStatement1.setString(5, record.getNote());
            preparedStatement1.setString(6, record.getRecordDate());

            preparedStatement1.executeUpdate();
            return true;

        } catch (SQLException e) {

            System.out.println("Add health record error: " + e);
            return false;
        }
    }

    // Gets only the records that belong to the logged-in user
    public List<Health_Record> getRecordsByUserId(int userId) {

        List<Health_Record> records = new ArrayList<>();

        String sql = "SELECT * FROM health_records WHERE user_id = ? ORDER BY record_date DESC, id DESC";

        try (Connection connect = DB_connections.getConnnection();
             PreparedStatement PreparedStatement1 = connect.prepareStatement(sql)) {

            PreparedStatement1.setInt(1, userId);

            ResultSet rs = PreparedStatement1.executeQuery();

            while (rs.next()) {
                records.add(new Health_Record(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("weight"),
                        rs.getString("temperature"),
                        rs.getString("blood_pressure"),
                        rs.getString("note"),
                        rs.getString("record_date")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Get records error: " + e);
        }

        return records;
    }

    // Deletes selected health record
    public boolean deleteRecord(int recordId) {

        String sql = "DELETE FROM health_records WHERE id = ?";

        try (Connection connect = DB_connections.getConnnection();
             PreparedStatement PreparedStatement1 = connect.prepareStatement(sql)) {

            PreparedStatement1.setInt(1, recordId);
            PreparedStatement1.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Delete record error: " + e);
            return false;
        }
    }

    // Exports all health records into a text file
    public boolean exportRecords(List<Health_Record> records, String fileName) {
        try (FileWriter writer1 = new FileWriter(fileName)) {
            // Writes every health record into the file
            for (Health_Record record : records) {

                writer1.write("Date: " + record.getRecordDate() + "\n");
                writer1.write("Weight: " + record.getWeight() + "\n");
                writer1.write("Temperature: " + record.getTemperature() + "\n");
                writer1.write("Blood Pressure: " + record.getBloodPressure() + "\n");
                writer1.write("Note: " + record.getNote() + "\n");
                writer1.write("-----------------------------\n");
            }

            return true;
        } catch (Exception e) {

            System.out.println(
                    "Export error: " + e
            );

            return false;
        }
    }

    // Counts how many health records belong to a user
    public int getRecordCount(int userId) {

        String sql = """
            SELECT COUNT(*)
            FROM health_records
            WHERE user_id = ?
            """;

        try (Connection connect = DB_connections.getConnnection();
             PreparedStatement PreparedStatement1 = connect.prepareStatement(sql)) {

            PreparedStatement1.setInt(1, userId);

            ResultSet rs = PreparedStatement1.executeQuery();

            if (rs.next()) {

                return rs.getInt(1);
            }

        } catch (SQLException e) {

            System.out.println("Record count error: " + e);
        }

        return 0;
    }
}
