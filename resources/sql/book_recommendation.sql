create table book
(
  uri varchar(100) not null
    primary key,
  name varchar(150) not null,
  author_name varchar(100) not null,
  author_movement varchar(100) not null,
  genre varchar(100) not null,
  constraint book_uri_uindex
  unique (uri)
)
;

create table searched_books
(
  `index` int auto_increment
    primary key,
  user varchar(30) not null,
  book varchar(100) not null,
  constraint searched_books_book_uri_fk
  foreign key (book) references book_recommendation.book (uri)
)
;

create index searched_books_book_uri_fk
  on searched_books (book)
;

create index searched_books_user_username_fk
  on searched_books (user)
;

create table user
(
  username varchar(30) not null,
  password varchar(45) not null,
  firstname varchar(45) not null,
  lastname varchar(45) not null,
  primary key (username, password),
  constraint username_UNIQUE
  unique (username)
)
;

alter table searched_books
  add constraint searched_books_user_username_fk
foreign key (user) references book_recommendation.user (username)
;

