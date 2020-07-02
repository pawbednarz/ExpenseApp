insert into user_role(role, description) values ("ROLE_USER", "default role for user");
insert into user_role(role, description) values ("ROLE_ADMIN", "default role for admin");

insert into user(first_name, last_name, username, email, password) values("pb", "pb", "pablo", "pablo@test.com", "$2a$10$2oxW/SMDpLhTjk7XZCvBIuj0h7vYXnMqkvdX0BFr0XovsTkrVXzbe");

insert into user_user_roles(user_user_id, user_roles_id) values (1, 1);

insert into expense (category, date, value, id_user) values (1, NOW(), 13.24, 1);
insert into expense (category, date, value, id_user) values (3, NOW(), 79.99, 1);
