use  appointments;

create table users(
id int auto_increment primary key,
username varchar(255) not null,
email varchar(255) not null,
password varchar(255) not null,
role enum('admin', 'user','user_provider'),
created_at timestamp default current_timestamp
);

alter table users
drop column role;
alter table users
modify column email varchar(255) not null unique key;
alter table users
add roles varchar

create table Role(
id int auto_increment not null primary key,
name varchar(20)
);
create table user_role(
user_id int,
role_id int,
foreign key(user_id) references users(id),
foreign key(role_id) references Role(id)
);
create table services(
id int auto_increment primary key,
name varchar(255) not null,
description text,
price decimal(10,2),
service_provider_id int,
foreign key(service_provider_id)
references users(id)
);

create table appointments(
id int auto_increment primary key,
user_id int,
service_id int,
appointment_time timestamp not null,
status enum('confirmed','cancelled','pending') default 'pending',
foreign key(user_id)
references users(id),
foreign key(service_id)
references services(id)
);



create table availbility(
id int auto_increment primary key,
service_provider_id int,
start_time timestamp not null,
end_time timestamp not null,
foreign key(service_provider_id)
references users(id)
);


select *from  appointments;
select *from  availability;
select *from  services;
select *from  users;
select *from Role;
select *from user_role;

alter table users drop column username;
alter table users add username varchar(255) not null;