# Software Engineering Project Management 

## Assignment 2
***

**Contributors**  

| Name            | Student ID |
|-----------------|------------|
| Samuel Hopkins  | s3864355   |
| Alan Wood       | s3861662   |
| Rafael Zuniga   | s3401227   |
| Harley Urquhart | s3824191   |
| Josh Whiteford  | s3550186   |

***

### About
The project is a Java IT Ticketing application run on the console. 
Users can submit tickets while Technicians can view and update them.  
Technicians and Staff have been hardcoded for now for easy use.
 
*** 
### Current Version 0.3

#### What's New 

- Implementation of reporting for System Owner

- System owner is now able to generate a report under their login. 
- System owner can search the following parameters:
    - Start Date of Report
    - End Date of Report 
- A report will generate containing the following:
  - Opened By 
  - Date Opened
  - Severity 
  - Status (Open/Closed)
  - Assigned to
  - Days opened

A new user (System Owner) has been added with the following login information to allow access:

| Login           | Password |
|-----------------|----------|
| x               | x        |



***
### Previous Version 0.2


- System will automatically create 12 tickets for ease of testing
- Users are now able to change their password before attempting to login.

- Technicians are now able to change the severity of a ticket.
  - This also reassigns the ticket to the correct Level technician upon reassignment. 
- Technicians are now able to change the status of a ticket.
  - They can also reopen tickets that were archived less than 24 hours ago.
- Staff & Technicians can view their OPEN tickets.
- Closed tickets are automatically archived after 24 hours. (1 minute in program - change "TIME_IN_SECONDS" variable in Main.java to adjust
- Technicians can view all closed tickets. 
- Technicians can only view their own assigned tickets.



***
### Previous Version 0.1

The team in group 6 has implemented the following features:  
- Users and Technicians can be created from the login screen
- Once a User or Technician has been set up, they can log in
- Users and Technicians have their own menu, as they have different privelidges
	- Users can create new tickets
	- Users can view their submitted tickets
	- Technicians can view their assigned tickets
- Each ticket is assigned by the business rules that were dictated by the client
	- Low and Medium severity go to Level 1 technicians
	- High severity go to Level 2 technicians
	- Tickets will be assigned to the technician that has the least amount of tickets 
	- If 2 technicians have equal lowest tickets, assignment will be random


***
### What's Coming Next

We await feedback from the client for the next opportunities to enhance this product.


***
## Instructions

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
ie: to choose 1. Existing User Login, simply enter '1' and press enter  

You can log in 3 ways, as an existing Staff Member, as a Technician, Or sign up as a Staff Member  

To log in as a Staff member, select "1. Existing User Login", and then "1. Continue to login form" and use one of the following logins:  


|Email|Password|
|-----|--------|
|Sam|Sam|
|Alan|Alan|
|Harley|Harley|
|Raf|Raf|
|Josh|Josh|



To login as a Technician, select "1. Existing User Login" and then "1. Continue to login form" and use one of the following logins:  

| Email                    | Password     |
|--------------------------|--------------|
| harrystyles@gmail.com    | password123  |
| nialhoran@gmail.com      | password123  |
| louistomlinson@gmail.com | password123  |
| zaynmalik@gmail.com      | password123  |
| liampayne@gmail.com      | password123  |


To create a new user, select "2. Create New User" and follow the prompts.  
Please note, passwords must be at least 20 characters long and contain both alphanumeric characters.


***
## As a Staff Member
To Submit a ticket:  
- Select 1. Submit Ticket  
- Enter the severity  
- Enter a description  
- This will take you back to the main staff menu  
  
You can either submit another ticket or log out  
To logout enter "-1"  


***



## As a Technician


Login with the Technician details.  


### View Assigned Tickets

To view the Technicians assigned tickets, select "1. View Assigned Tickets" and a list of currently assigned and OPEN tickets are shown to the user. 
The Technician can select an open ticket and then further options will be shown to update the tickets status or severity of the ticket.

##### 1. Change Status

After selecting a Ticket, the Technician will have the option to Change the Status of the Ticket.

- The Technician will have the option to either mark the ticket as CLOSE_RESOLVED or CLOSE_UNRESOLVED.

When the Ticket is closed, it will be automatically archived after 24 hours. 

##### 2. Change Severity

After selecting a Ticket, the Technician will have the option to Change the Severity of the Ticket.

Tickets can be assigned to a LOW, MEDIUM, or HIGH severity with the current severity excluded from the available options.
  - If a Level 1 Technician reassigns a ticket to a HIGH status, this will reassign the ticket to a Level 2 Technician and will no longer be visible by a Level 1 Technician. 
  - If a Level 2 Technician reassigns a ticket to a MEDIUM or LOW status, this will  reassign the ticket to a Level 1 Technician and will no longer be visible by a Level 2 Technician.


### View Closed Tickets

After selecting this option, the Technician will be shown the closed tickets which are not yet archived by the system which occurs after 24 hours. 
If a ticket is archived, it cannot be reopened. 

If a ticket is closed but not yet archived, the Technician will have the option to reopen the ticket or to change the closed status of the ticket. 

### View Archived Tickets 

After selecting this option, all Tickets archived by this Technician will be shown. There is no option to reopen an archived ticket.


***
### As either Staff or Technician
To change your password:   
- From the login menu, select "1. Existing User Login"  
- Select 2. I forgot my password  
- Enter your Email   
- Enter your name  
- Enter your Mobile Number  

If the details entered match a User in the system, you will be prompted to enter a new password.  
***





