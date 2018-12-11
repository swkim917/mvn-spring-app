create table if not exists member (
  id varchar(12) primary key,
  passwd varchar(12) not null,
  name varchar(20) not null,
  reg_date timestamp not null,
  age integer,
  gender varchar(5),
  email varchar(30),
  address varchar(100),
  tel varchar(30),
  mtel varchar(30)
);

create table if not exists board (
  num integer primary key,
  name varchar(20),
  passwd varchar(20),
  subject varchar(50),
  content varchar(2000),
  ip varchar(20),
  reg_date timestamp,
  readcount integer,
  re_ref integer,
  re_lev integer,
  re_seq integer,
  filename varchar(50)
);
