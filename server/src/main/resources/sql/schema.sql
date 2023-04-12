-- drop database if exists products;

-- create database products;

drop table products;

create table if not exists products (
                                        ean varchar(15) primary key,
                                        name varchar(255),
                                        brand varchar(255));

drop table profiles;
create table if not exists profiles (
                    email varchar(255) primary key,
                    name varchar(255),
                    lastname varchar(255)
);

insert into profiles (email, name, lastname) VALUES ('milad.be@gmail.com', 'Milad', 'Beigi'),
                                                    ('shayan@gmail.com', 'Shayan', 'Taghinezhad');

insert into products (ean, name, brand) values
                                            ('1234567890123', 'iPhone 6', 'Apple'),
                                            ('1234567890124', 'iPhone 6 Plus', 'Apple'),
                                            ('1234567890125', 'iPhone 5', 'Apple'),
                                            ('1234567890126', 'iPhone 5s', 'Apple'),
                                            ('1234567890127', 'iPhone 5c', 'Apple'),
                                            ('1234567890128', 'iPhone 4s', 'Apple'),
                                            ('1234567890129', 'iPhone 4', 'Apple'),
                                            ('1234567890130', 'iPhone 3GS', 'Apple'),
                                            ('1234567890131', 'iPhone 3G', 'Apple'),
                                            ('1234567890132', 'iPhone', 'Apple'),
                                            ('1234567890133', 'Galaxy S5', 'Samsung'),
                                            ('1234567890134', 'Galaxy S4', 'Samsung'),
                                            ('1234567890135', 'Galaxy S3', 'Samsung'),
                                            ('1234567890136', 'Galaxy S2', 'Samsung'),
                                            ('1234567890137', 'Galaxy S', 'Samsung'),
                                            ('1234567890138', 'Galaxy Note 3', 'Samsung'),
                                            ('1234567890139', 'Galaxy Note 2', 'Samsung'),
                                            ('1234567890140', 'Galaxy Note', 'Samsung'),
                                            ('1234567890141', 'Galaxy Nexus', 'Samsung'),
                                            ('1234567890142', 'Galaxy Tab 3', 'Samsung'),
                                            ('1234567890143', 'Galaxy Tab 2', 'Samsung'),
                                            ('1234567890144', 'Galaxy Tab', 'Samsung'),
                                            ('1234567890145', 'Nexus 5', 'LG'),
                                            ('1234567890146', 'Nexus 4', 'LG'),
                                            ('1234567890147', 'Nexus 7', 'LG'),
                                            ('1234567890148', 'Nexus 10', 'LG'),
                                            ('1234567890149', 'Nexus 4', 'LG'),
                                            ('1234567890150', 'Nexus 5', 'LG'),
                                            ('1234567890151', 'Nexus 7', 'LG')
