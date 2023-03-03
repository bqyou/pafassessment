create database acme_bank;

use acme_bank;

create table accounts (
    account_id varchar(10) not null,
    name varchar(128) not null,
    balance decimal (38,2) not null
);

insert into accounts (account_id, name, balance) values ("V9L3Jd1BBI", "fred", 100);
insert into accounts (account_id, name, balance) values ("fhRq46Y6vB", "barney", 300);
insert into accounts (account_id, name, balance) values ("uFSFRqUpJy", "wilma", 1000);
insert into accounts (account_id, name, balance) values ("ckTV56axff", "betty", 1000);
insert into accounts (account_id, name, balance) values ("Qgcnwbshbh", "pebbles", 50);
insert into accounts (account_id, name, balance) values ("if9l185l18", "bambam", 50);

