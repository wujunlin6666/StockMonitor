-- 股票项目的数据库建表脚本
create table stocks(
                       symbol varchar(20) primary key,
                       previous_close decimal(14,4) not null,
                       current_price decimal(14,4) not null
);

create table sectors(
                        sector_id int not null ,
                        sector_name varchar(50) not null,
                        primary key (sector_id)
);

create table sector_component(
                                 sector_id int not null ,
                                 symbol varchar(20) not null ,
                                 weight decimal(5,4) not null ,
                                 primary key (sector_id,symbol),
                                 foreign key (sector_id) references sectors(sector_id),
                                 foreign key (symbol) references stocks(symbol)
);

