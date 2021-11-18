-- fbehri2s, 18.11.2021


-- Drop Section
-- _______________

drop table col_tab_job_advertisement;
drop table col_tab_contact_person;
drop table col_tab_company;
drop table col_tab_student;
drop table col_tab_user;
drop table col_tab_address;

drop sequence col_seq_address_id;
drop sequence col_seq_user_id;
drop sequence col_seq_advertisement_id;
drop sequence col_seq_company_id;

-- Trigger Section
-- _______________

create sequence col_seq_address_id
    start with 10000000
    increment by 1
    minvalue 10000000
    maxvalue 19999999;

create sequence col_seq_user_id
    start with 20000000
    increment by 1
    minvalue 20000000
    maxvalue 29999999;

create sequence col_seq_advertisement_id
    start with 30000000
    increment by 1
    minvalue 30000000
    maxvalue 39999999;

create sequence col_seq_company_id
    start with 40000000
    increment by 1
    minvalue 40000000
    maxvalue 49999999;

-- Table Section
-- _____________

create table col_tab_address (
     address_id varchar(8) not null default nextval('col_seq_address_id'),
     postal_code varchar(5) not null,
     city varchar(16) not null,
     countrie varchar(16) not null,
     street varchar(12) not null,
     house_number varchar(4) not null,
     constraint col_pk_address_id primary key (address_id));

create table col_tab_contact_person (
     user_id varchar(8) not null,
     company_id varchar(8) not null,
     role varchar(16) not null,
     constraint col_pk_cp_user_id primary key (user_id),
     constraint col_un_cp_company_id unique (company_id));

create table col_tab_user (
     password varchar(20) not null,
     phone_number varchar(12) not null,
     address_id varchar(8) not null,
     salutation varchar(10) not null,
     title varchar(16) not null,
     first_name varchar(16) not null,
     surname varchar(16) not null,
     mail_address varchar(32) not null,
     user_id varchar(8) not null default nextval('col_seq_user_id'),
     constraint col_pk_u_user_id primary key (user_id),
     constraint col_un_u_address_id unique (address_id));

create table col_tab_job_advertisement (
     contact_person_id varchar(8) not null,
     job_description varchar(1024) not null,
     job_title varchar(64) not null,
     student_id varchar(8) not null,
     advertisement_id varchar(8) not null default nextval('col_seq_advertisement_id'),
     constraint col_pk_advertisement_id primary key (advertisement_id),
     constraint col_un_student_id unique (student_id),
     constraint col_un_contact_person_id unique (contact_person_id));

create table col_tab_student (
     user_id varchar(8) not null,
     subject_field varchar(16) not null,
     constraint col_pk_s_user_id primary key (user_id));

create table col_tab_company (
     company_description varchar(1024) not null,
     company_id varchar(8) not null default nextval('col_seq_company_id'),
     homepage varchar(32) not null,
     fax_number varchar(12) not null,
     company_name varchar(32) not null,
     address_id varchar(8) not null,
     constraint col_pk_company_id primary key (company_id),
     constraint col_un_c_address_id unique (address_id));

-- Constraints Section
-- ___________________

alter table col_tab_contact_person
    add constraint col_fk_cp_user_id
    foreign key (user_id)
    references col_tab_user;

alter table col_tab_contact_person
    add constraint col_fk_cp_company_id
    foreign key (company_id)
    references col_tab_company;

alter table col_tab_user
    add constraint col_fk_u_address_id
    foreign key (address_id)
    references col_tab_address;

alter table col_tab_job_advertisement
    add constraint col_fk_student_id
    foreign key (student_id)
    references col_tab_student;

alter table col_tab_job_advertisement
    add constraint col_fk_contact_person_id
    foreign key (contact_person_id)
    references col_tab_contact_person;

alter table col_tab_student
    add constraint col_fk_s_user_id
    foreign key (user_id)
    references col_tab_user;

alter table col_tab_company
    add constraint col_fk_c_address_id
    foreign key (address_id)
    references col_tab_address;

--alter table col_tab_user add constraint
--     check(exists(select * from col_tab_contact_person
--                  where col_tab_contact_person.user_id = user_id));

--alter table col_tab_user add constraint
--     check(exists(select * from col_tab_student
--                  where col_tab_student.user_id = user_id));

--alter table col_tab_student add constraint
--     check(exists(select * from col_tab_job_advertisement
--                  where col_tab_job_advertisement.student_id = user_id));

--alter table col_tab_company add constraint
--     check(exists(select * from col_tab_contact_person
--                  where col_tab_contact_person.company_id = company_id));

--alter table col_tab_address add constraint
--     check(exists(select * from col_tab_company
--                  where col_tab_company.address_id = address_id));

--alter table col_tab_address add constraint
--     check(exists(select * from col_tab_user
--                  where col_tab_user.address_id = address_id));

--alter table col_tab_contact_person add constraint
--     check(exists(select * from col_tab_job_advertisement
--                  where col_tab_job_advertisement.contact_person_id = user_id));

-- Index Section
-- _____________

-- create unique index IDAdressen
--     on col_tab_address (address_id);

--create unique index IDAnsprechpartner
--     on col_tab_contact_person (user_id);

--create unique index IDAnsprechpartner_1
--     on col_tab_contact_person (company_id);

--create unique index IDNutzer
--     on col_tab_user (user_id);

--create unique index IDNutzer_1
--    on col_tab_user (address_id);

--create unique index IDBewerbung
--     on col_tab_job_advertisement (advertisement_id);

--create unique index IDBewerbung_1
--     on col_tab_job_advertisement (student_id);

--create unique index IDStellenausschreibung
--    on col_tab_job_advertisement (contact_person_id);

--create unique index IDStudenten
--    on col_tab_student (user_id);

--create unique index IDUnternehmen
--    on col_tab_company (company_id);

--create unique index IDUnternehmen_1
--    on col_tab_company (address_id);

