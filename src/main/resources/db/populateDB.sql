DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO meals (user_id, date_time, description, calories) VALUES
  (100000, '14.01.2015T14:20', 'юзер1', 500),
  (100000, '04.02.2016T15:10', 'юзер2', 510),
  (100001, '04.01.2015T15:10', 'Адмін', 500),
  (100001, '04.02.2016T15:10', 'Адмін2', 500);

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);


