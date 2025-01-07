create table Companies (
`id` int auto_increment primary key,
`name` varchar(20),
`email` varchar(20),
`password` varchar(20)
);

create table Customers (
`id` int auto_increment primary key,
`first_name` varchar(20),
`last_name` varchar(20),
`email` varchar(20),
`password` varchar(20)
);


create table Coupons(
`id` int auto_increment primary key,
`company_id` int,
`category_id` int,
`title` varchar(20),
`description` varchar(100),
`start_date` date,
`end_date` date,
`amount` int,
`price` double,
`image` varchar(20),
foreign key (`company_id`) references Companies(id)
);

create table Customers_vs_Coupons(
`customer_id` int,
`coupon_id` int,
foreign key (`customer_id`) references Customers(id),
foreign key (`coupon_id`) references Coupons(id)
)