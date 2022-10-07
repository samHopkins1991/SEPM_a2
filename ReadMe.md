# Software Engineering Project Management 

## Assignment 2
***

**Contributors**  

Samuel Hopkins: s3864355  

Alan Wood: S3861662  

Rafael Zuniga: S3401227   

Harley Urquhart: S3824191   

Josh Whiteford: S3550186   
***

### About
The project is a Java IT Ticketing application run on the console. Users can submit tickets while Technicians can view and update them.  
Technicians and Staff have been hardcoded for now for easy use  
 
*** 
***
### Current Version 0.1
***

### What's New
***
The team in group 6 has implemented the following features:  
- Users and Technicians can be created from the login screen
- Once a User or Technician has been set up, they can log in
- Users and Technicians have their own menu, as they have different privelidges
	- Users can create new tickets
	- Users can view their submitted tickets
	- Technicians can view their assigned tickets
- Each ticket is assigned by the business rules that were dictated by the client
	- low and medium severity go to level 1 technicians
	- high severity go to level 2 technicians
	- tickets will be assigned to the technician that has the least amount of tickets 
	- if 2 technicians have equal lowest tickets, assignment will be random


***
### What's Coming Next
***
The following features are planned to be delivered next sprint:  
- Streamlined login function (moving forgotten password hurdle before logging in)
- Technicians can change the severity of the ticket up or down
	- this will trigger a reassignment of the ticket
- Technicians can change the status of the tickets: OPEN/CLOSED, RESOLVED/UNRESOLVED
- Users can view their OPEN tickets
- The system will automatically archive closed tickets
- Technicians can view all closed and archived tickets
- Technicians can only view open tickets assigned to them
***
## Instructions
***
### To open on Eclipse
1. Download the zip file named SEPM_a2.zip and save it to the desktop
2. Open Eclipse
3. Click File > Open Projects from File System
4. Click the "Archive.." button which is located next to the "Directory.." button
5. Select the Desktop and choose the Zip file that is saved. Click Open
6. Navigate to SEPM_a2/src
7. Right click "Main.java" and select "Run As" > "Java Application"

### Once the program is running
The menu system of the program is run by entering the corresponding digits into the console and pressing enter.  
IE: to choose 1. Existing User Login, simply enter '1' and press enter  

You can log in 3 ways, as an existing Staff Memeber, as a Technician, Or sign up as a Staff Member  

To log in as a Staff member, select "1. Existing User Login", and then "1. Continue to login form" and use one of the following logins:  

Email: 			Password:  

Sam:			Sam  
Alan:			Alan  
Harley:			Harley  
Raf:			Raf  
Josh:			Josh  


To login as a Technician, select "1. Existing User Login" and then "1. Continue to login form" and use one of the following logins:  

Email: 				Password:  
harrystyles@gmail.com:		password123  
nialhoran@gmail.com:		password123  
louistomlinson@gmail.com:	password123  
zaynmalik@gmail.com:		password123  
liampayne@gmail.com:		password123  


To create a new user, select "2. Create New User" and follow the promtps.  
Please note, passwords must be at least 20 characters long and contain both alha and numeric characters

### As a Staff Member
To Submit a ticket:  
Select 1. Submit Ticket  
Enter the severity  
Enter a description  
This will take you back to the main staff menu  
  
You can either submit another ticket or log out  
To logout enter "-1"  



**TEACHING STAFF FYI**  


There is a hidden option 3 from the main menu of staff members to view all tickets  
This is so that you can easily login as a Technician and view their tickets.  
It also helps to verify the business rules of the assignment are followed when assigning tickets.  
It will just show arrays of each Technician's Tickets. if blank, it is an empty array  

***Teaching Staff FYI** 

### As a Technician
Login with the Technician details.  
To view the technicians assigned tickets, select "1. View Assigned Tickets"  
  
Please note that even though 2. View closed and Archived Tickets is shows, it is not yet functional.  


### As either Staff or Technician
To change your password:   
From the login menu, select "1. Existing User Login"  
Select 2. I forgot my password  
Enter your email   
Enter your name  
Enter your mobile number  

If the details entered match a User in the system, you will be prompted to enter a new password.  



