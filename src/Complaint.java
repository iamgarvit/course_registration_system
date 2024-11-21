package src;

import java.util.ArrayList;
import java.util.Date;

public class Complaint {
    private Student studentWithComplaint;
    private String description;
    private int status;
    private int complaintID;
    private String resolutionDescription;
    private Date dateFiled;
    
    private static ArrayList<Complaint> pendingComplaints = new ArrayList<>();
    private static ArrayList<Complaint> resolvedComplaints = new ArrayList<>();
    private static int num = 1;

    Complaint(Student student, String description) {
        this.studentWithComplaint = student;
        this.description = description;
        this.status = 0;
        this.complaintID = num;
        this.dateFiled = new Date();
        num += 1;
        pendingComplaints.add(this);
    }

    public int getStatus() {
        return this.status;
    }

    public int getComplaintID() {
        return this.complaintID;
    }

    public Date getDateFiled() {
        return this.dateFiled;
    }

    public String getDescription() {
        return this.description;
    }

    public Student getStudentWithComplaint() {
        return this.studentWithComplaint;
    }
    
    public String getResolutionDescription() {
    	return this.resolutionDescription;
    }
    	
    public void displayComplaintDetails() {
    	int status = getStatus();
    	String statusPrint;
    	
    	if (status == 0) {
    		statusPrint = "Pending";
    	}
    	else {
    		statusPrint = "Resolved";
    	}
    	
    	System.out.println("Complaint ID : " + this.complaintID + '\n' +
    					   "Status : " + statusPrint + '\n' +
    					   "Description : " + this.description + '\n' +
    					   "Date : " + this.dateFiled + '\n');
    }

    public void resolveComplaint(String description) {
        this.status = 1;
        this.resolutionDescription = description;
        pendingComplaints.remove(this);
        resolvedComplaints.add(this);
        System.out.println("Complaint resolved successfully.");
    }

    private static void viewPendingComplaints() {
        if (pendingComplaints.isEmpty()) {
            System.out.println("No pending complaints.");
            return;
        }

        System.out.println("Pending Complaints:");
        for (Complaint complaint : pendingComplaints) {
            System.out.println("Complaint ID: " + complaint.getComplaintID() + '\n' +
            				   "Student : " + complaint.getStudentWithComplaint().getusername() + '\n' +
                               "Description: " + complaint.getDescription() + '\n' +
                               "Date: " + complaint.getDateFiled() + '\n');
        }
    }

    private static void viewResolvedComplaints() {
        if (resolvedComplaints.isEmpty()) {
            System.out.println("No resolved complaints.");
            return;
        }

        System.out.println("Resolved Complaints:");
        for (Complaint complaint : resolvedComplaints) {
            System.out.println("Complaint ID : " + complaint.getComplaintID() + '\n' +
            				   "Student : " + complaint.getStudentWithComplaint().getusername() + '\n' +
                               "Description : " + complaint.getDescription() + '\n' +
                               "Resolution Description : " + complaint.getResolutionDescription() + '\n' +
                               "Date : " + complaint.getDateFiled() + '\n');
        }
    }

    public static void viewAllComplaints() {
        viewPendingComplaints();
        viewResolvedComplaints();
    }

    public static void filterComplaintsByStatus(int status) {
        if (status == 0) {
            viewPendingComplaints();
        } 
        else if (status == 1) {
            viewResolvedComplaints();
        } 
        else {
            System.out.println("Invalid status. Please choose 0 for pending or 1 for resolved.");
        }
    }

    public static Complaint findComplaintByID(int complaintID) {
        for (Complaint complaint : pendingComplaints) {
            if (complaint.getComplaintID() == complaintID) {
                return complaint;
            }
        }
        for (Complaint complaint : resolvedComplaints) {
            if (complaint.getComplaintID() == complaintID) {
                return complaint;
            }
        }
        return null;
    }
}
