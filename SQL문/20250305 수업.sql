select * from user250304;

edit user250304;

create table user250304(
id number(5) constraints user_id_pk primary key,
username varchar2(30) not null,
password varchar2(100) not null,
email varchar2(50),
createdate varchar2(50),
role varchar2(30)
);

select * from user250304;

