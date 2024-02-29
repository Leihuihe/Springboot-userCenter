create database user_center;

use user_center;
create table user
(
    id           bigint auto_increment
        primary key,
    username     varchar(256)  default 'username'                                                                       null,
    userAccount  varchar(256)                                                                                           null,
    avatarUrl    varchar(1024) default 'https://gw.alipayobjects.com/zos/antfincdn/XAosXuNZyF/BiazfanxmamNRoxxVxka.png' null,
    gender       tinyint                                                                                                null,
    userPassword varchar(512)                                                                                           not null,
    phone        varchar(128)                                                                                           null,
    email        varchar(256)                                                                                           null,
    userStatus   int           default 0                                                                                not null,
    createTime   datetime      default CURRENT_TIMESTAMP                                                                null,
    updateTime   datetime      default CURRENT_TIMESTAMP                                                                null,
    isDelete     tinyint       default 0                                                                                null invisible,
    userRole     int           default 0                                                                                not null,
    codeID       varchar(512)                                                                                           null
);