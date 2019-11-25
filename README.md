# Technical solution description

* Main app: [railroad](https://github.com/asivers/railroad)
* Schedule board app: [JSFWebSockets](https://github.com/asivers/JSFWebSockets)

## Abstract
The main application is simulating the operation of the information system of a railway company. There are user mode and administrator mode. The user has the opportunity to view trains at a given station, view stations along the train, view purchased tickets, and purchase tickets. The administrator has the opportunity to add stations and trains, delete stations and trains, view passengers registered on the train. Authorization is performed using Spring Security. Access the database is through Hibernate.
The second application is an electronic schedule board for a specific station. When the program starts, a list of trains arriving at the station is displayed on the board. The list is updated upon receipt of a notification from the main application about a change in the train schedule. It is based on WebSocket and MQ technologies.

## User story
##### Main App
There are two buttons on the index page: sign in (go to the authorization page) and sign up (go to the registration page). To register, user has to enter a login-password pair (values must not be empty or longer than 100 characters).
###### User
Upon successful authorization, the user goes to the main page of the user. The user’s main page contains buttons: “Find train”, “Schedule”, “My tickets” and “Exit”.
When searching by the specified parameters (“Find train”), the user enters the departure station, arrival station and the time interval during which the train must arrive at the arrival station. As a result, a list of trains matching the given conditions is displayed. On this page you can proceed to purchase a ticket for any train. The user enters the personal data of the registered passenger. If the train leaves no earlier than in 10 minutes, if there are empty seats, and if the passenger with the same data is not on this train, the ticket is purchased.
When searching for trains by station (“Schedule”), the user must enter the name of the station. A list of all trains passing this station is displayed. By clicking on any of the trains you can see a list of stations that this train passes.
When choosing to view my tickets, the user sees a list of purchased tickets.
###### Admin
For the application, there is a login-password pair, which has been set initially, that allows to log in with administrator rights. The administrator’s main page contains the buttons “Passengers by train”, “All trains”, “Add/Update train”, “Add station”, “Exit”.
When searching for passengers by train, the administrator has to enter the train number. A list of all passengers who bought a ticket for this train is displayed.
When choosing to view all trains, a list of all trains available in the database is displayed on the screen. It is possible to see the list of passengers for each train, as well as delete this train from the database.
When choosing the “Add/Update train” option, the administrator has to enter the train number and the number of seats in it. If the train with this number is not in the database, it is added to the database, otherwise it is selected for further editing. Next, the administrator can add a station for the train, delete the station for the train, or return to the main page. When adding a station, the administrator enters the station name and stop time. If the station is not on the route of the train, its name is correct (not empty and does not contain the characters '/'), and the stop time is not occupied by another station, the station is added for this train. When deleting a station, the administrator enters the name of the station. If the station was on the route of the train, it would be removed from it.
When choosing the “Add station” option, the administrator has to enter the name of the station. If the station is not in the database and if its name is correct (not empty and does not contain the characters '/'), it is added to the database.
##### Schedule board app
An electronic schedule board for a particular station shows a list of trains passing this station during the day, and their stop times. With any change in the schedule, the information on the schedule board is updated.

## Data model
###### Entities:
* Train (id, train number, seats);
* Station (id, station name);
* Station-Train relation (station id, train id, stop time). Many-to-many relation;
* Passenger (id, first name, second name, birth date);
* Ticket eq. Passenger-Train relation (id, passenger id, train id). Many-to-many relation;
* User (id, username, password);
* Authority-User relation (authority id, user id). Admin authority id = 1, user authority id = 2;
* Passenger-User relation (passenger id, user id). One user can buy tickets for multiple passengers.

## Architecture
###### Main App
The main application is based on Spring MVC. Configuration via JavaConfig (without web.xml). All request methods are located in the MainController controller. The logic is implemented in Service. Access data through DAO, using HQL queries. Authorization through Spring Security. WebService NotificationEndPoint for event listening from application schedule board. MessageSender for sending notifications to the schedule board. Pages are stored in webapp.
###### Schedule board App
Schedule board application configuration through JavaConfig (without web.xml). MainConroller is only for redirecting to the main page. WebService NotificationEndPoint for adding the current session when the application starts. MessageReciever to receive notification from the main application. Bean NotificationManagedBean to output a notification to Websocket. Reversed parsing of a string in javascript in template.xhtml. JSF tags in template.xhtml for filling the schedule board table in browser.

## Technologies
* Configuration: JavaConfig - without web.xml (both apps)
* Application server: Tomcat 9.0 (both apps)
* Build automation: Maven (both apps)
* Database: MySQL
* JPA: Hibernate
* Framework: Spring MVC, EJB
* Pages: JSP (main app); XHTML (schedule board app)
* Notification browser output: WebSocket (schedule board app)
* Notification transfer method: ActiveMQ
* Event listening: WebServices (endpoint)

## Possibilities for improvement
1. Add route entity to prevent mistakes made by wrong database filling by admin.
2. Add more functions to passenger (delete ticket).
3. Add more functions to admin (delete passenger, delete ticket, delete user).
4. Optimize graphic interface (less free space, more information). 
5. Optimize controller structure (divide)
