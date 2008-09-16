connect 'jdbc:derby://localhost:1527/conf;create=true; user 'sa' password 'pass'; 

INSERT into USERS(enabled, firstname, lastname, username, password) values(1, 'FN', 'LN', 'admin', 'admin');
INSERT into AUTHORITY (role) values ('ROLE_APP-ADMIN');
INSERT into AUTHORITY (role) values ('ROLE_ROOM-ADMIN');
INSERT into AUTHORITY (role) values ('ROLE_USER');
INSERT into USERS_AUTHORITY (USERS_id, roles_id) values (1,1);