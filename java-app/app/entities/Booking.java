package semestral_project.app.entities;


import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Datová reprezentace záznamu z tabulky Booking.
 */
public class Booking {
    private int Id;
    private int DoctorToTestId;
    private int PacientId;
    private Date StartsAt;
    private boolean IsPaid;
    private boolean IsPositive;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getDoctorToTestId() {
        return DoctorToTestId;
    }

    public void setDoctorToTestId(int doctorToTestId) {
        DoctorToTestId = doctorToTestId;
    }

    public int getPacientId() {
        return PacientId;
    }

    public void setPacientId(int pacientId) {
        PacientId = pacientId;
    }

    public Date getStartsAt() {
        return StartsAt;
    }

    public void setStartsAt(Date startsAt) {
        StartsAt = startsAt;
    }

    public void setStartsAt(java.util.Date startsAt) {
        StartsAt = new java.sql.Date(startsAt.getTime());
    }

    public boolean isPaid() {
        return IsPaid;
    }

    public void setPaid(boolean paid) {
        IsPaid = paid;
    }

    public boolean isPositive() {
        return IsPositive;
    }

    public void setPositive(boolean positive) {
        IsPositive = positive;
    }

    public static Booking FromResultSet(ResultSet rs) throws SQLException {
        Booking booking = new Booking();

        booking.setId(rs.getInt("Id"));
        booking.setDoctorToTestId(rs.getInt("DoctorToTestId"));
        booking.setPacientId(rs.getInt("PacientId"));
        booking.setStartsAt(rs.getDate("StartsAt"));
        booking.setPaid(rs.getBoolean("Paid"));
        booking.setPositive(rs.getBoolean("IsPositive"));

        return booking;
    }

    @Override
    public String toString() {
        return "Id=" + Id +
                ", DoctorToTestId=" + DoctorToTestId +
                ", PacientId=" + PacientId +
                ", StartsAt=" + StartsAt +
                ", IsPaid=" + IsPaid +
                ", IsPositive=" + IsPositive;
    }
}
