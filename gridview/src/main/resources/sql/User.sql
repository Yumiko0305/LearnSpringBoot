# User表
DROP TABLE IF EXISTS `t_user`;
create table t_user
(
    id  varchar(100) primary key,
    name varchar(50) not null,
    age int not null,
    bir DATE not null
);

# 计数表
DROP TABLE IF EXISTS `tablecounter`;
create table tablecounter
(
    tid     bigint auto_increment primary key,
    name    varchar(255)     not null,
    counter bigint default 0 not null
);

create unique index tablecounter_name_uindex
    on tablecounter (name);

insert into tablecounter(name, counter) VALUE ('user', 0);

# 计算User的触发器，插入
CREATE TRIGGER `counter`
    AFTER INSERT
    ON t_user
    FOR EACH ROW
BEGIN
    UPDATE tablecounter SET tablecounter.counter=tablecounter.counter + 1 WHERE tablecounter.name = 'user';
END;

# 计算User的触发器,删除
CREATE TRIGGER `deletecounter`
    AFTER DELETE
    ON t_user
    FOR EACH ROW
BEGIN
    UPDATE tablecounter SET tablecounter.counter=tablecounter.counter - 1 WHERE tablecounter.name = 'user';
END;

# 添加User数据
INSERT INTO t_user (id,NAME,age,bir) VALUES ('1','username',21,date(NOW()));

