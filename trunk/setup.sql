INSERT into USERS(ID, enabled, firstname, lastname, username, password) values(null, true, 'FN', 'LN', 'admin', 'admin');
INSERT into AUTHORITY (ID, role) values (null, 'ROLE_APP-ADMIN');
INSERT into AUTHORITY (ID, role) values (null, 'ROLE_ROOM-ADMIN');
INSERT into AUTHORITY (ID, role) values (null, 'ROLE_USER');
INSERT into USERS_AUTHORITY (USERS_id, roles_id) values (1,1);