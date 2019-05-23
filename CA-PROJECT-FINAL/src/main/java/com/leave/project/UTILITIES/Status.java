package com.leave.project.UTILITIES;

public enum Status {
	APPLIED("APPLIED"),REJECTED("REJECTED"),APPROVED("APPROVED");
	
	
   private String status;
	 
	Status(String status) {
	        this.status = status;
	    }
	 
	    public String getStatus() {
	        return status;
	    }
}
