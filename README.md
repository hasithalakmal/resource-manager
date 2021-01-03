## Welcome to RMS

File management is a complex and common issue that every person/company is facing. With this RMS application you can easily manage your files. It contains just two steps. This is highly recommended for the Linux platform.

1. Upload file with relevant data
2. Search file by any details

Also if you are plan to use this for a company or some organization, it would be help evenyone to keep files hosted and search from every where. 

### Installation

### Prerequisites
1. Install docker compose
2. Create okta account with the following configurations
3. 9090 and 83 ports should be available in the server machine. If not you can change those configs accordingly.

![alt text](https://raw.githubusercontent.com/hasithalakmal/resource-manager/main/misc/image/okta_integration.png)

### Setting Up
1. Download/clone the repository
2. update .env file with okta secrets
3. execute `docker-compose build`
4. execute `docker-compose run -d`
5. execute this if you need to stop the service `docker-compose down`

# Technology usage
My main intention was to play around with some of the technologies to myself. But it has ended with a good product. Since I thought to expose it as an open source project. Since this is a good sample project if you are trying to use the following technologies

1. Spring boot with Java 11
2. Frontend development with spring boot ([http://www.thymeleaf.org])
3. Understand Okta and authorization code grant type
4. Integrate java application with Okta identity management service
5. Process Management Tools and it's usage
6. Docker compose
7. Communicate between two docker containers using docker network
8. Save data outside docker container (Local HDD)
9. Liquibase and how to create DDL structures when application starts.
10. Using logback with spring and size base log rotation

# How others can help to improve this product
I'm inviting to all of you to contribute for my project, these are the few improvements that you can play with.

1. Delete file
2. Udate file details
3. Upload multiple files with some sequence number
