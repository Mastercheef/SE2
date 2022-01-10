-- fbehri2s, 18.11.2021


-- Drop Section
-- _______________

drop table col_tab_settings;
drop table col_tab_application;
drop table col_tab_job_advertisement;
drop table col_tab_contact_person;
drop table col_tab_company;
drop table col_tab_message;
drop table col_tab_student;
drop table col_tab_user;
drop table col_tab_address;

drop sequence col_seq_address_id;
drop sequence col_seq_user_id;
drop sequence col_seq_advertisement_id;
drop sequence col_seq_company_id;
drop sequence col_seq_message_id;
drop sequence col_seq_application_id;

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

create sequence col_seq_message_id
    start with 50000000
    increment by 1
    minvalue 50000000
    maxvalue 59999999;

create sequence col_seq_application_id
    start with 60000000
    increment by 1
    minvalue 60000000
    maxvalue 69999999;

-- Table Section
-- _____________

create table col_tab_address (
     address_id bigint not null default nextval('col_seq_address_id'),
     postal_code varchar(5) not null,
     city varchar(16) not null,
     country varchar(16) not null,
     street varchar(16) not null,
     house_number varchar(4) not null,
     constraint col_pk_address_id primary key (address_id));

create table col_tab_contact_person (
     user_id bigint not null,
     company_id bigint not null,
     role varchar(16),
     constraint col_pk_cp_user_id primary key (user_id));

create table col_tab_user (
     user_id bigint not null default nextval('col_seq_user_id'),
     first_name varchar(16) not null,
     last_name varchar(16) not null,
     address_id bigint not null,
     date_of_birth date not null,
     password varchar(255) not null,
     phone_number varchar(12) not null,
     salutation varchar(10) not null,
     title varchar(16) not null,
     mail_address varchar(32) not null,
     type varchar(2),
     constraint col_pk_u_user_id primary key (user_id));

create table col_tab_job_advertisement (
     temporary_employment boolean not null,
     type_of_employment varchar(16) not null,
     working_hours numeric(2) not null default 0,
     requirements varchar(1024),
     address_id bigint not null,
     start_of_work date,
     end_of_work date,
     contact_person_id bigint not null,
     job_description varchar(1024),
     salary int,
     job_title varchar(64) not null,
     advertisement_id bigint not null default nextval('col_seq_advertisement_id'),
     constraint col_pk_advertisement_id primary key (advertisement_id));

create table col_tab_student (
     student_description varchar(2048),
     website varchar(32),
     graduation varchar(32),
     skills varchar(1024),
     interests varchar(512),
     user_id bigint not null,
     subject_field varchar(32),
     constraint col_pk_s_user_id primary key (user_id));

create table col_tab_company (
     company_id bigint not null default nextval('col_seq_company_id'),
     company_description varchar(1024) not null,
     homepage varchar(32),
     fax_number varchar(12),
     company_name varchar(32) not null,
     address_id bigint not null,
     mail_address varchar(32),
     phone_number varchar(12),
     constraint col_pk_company_id primary key (company_id));

create table col_tab_application (
     application_id bigint not null default nextval('col_seq_application_id'),
     user_id bigint not null,
     advertisement_id bigint not null,
     headline varchar(128) not null,
     text varchar(4096) not null,
     date date not null,
     constraint col_pk_application_id primary key (application_id));

create table col_tab_message (
    message_id bigint not null default nextval('col_seq_message_id'),
    sender_id bigint not null,
    recipient_id bigint not null,
    content varchar(512) not null,
    subject varchar(64) not null,
    date date not null,
    read boolean not null default false,
    constraint col_pk_message_id primary key (message_id));

create table col_tab_settings (
    user_id bigint not null,
    notification_is_enabled boolean not null,
    constraint col_pk_se_user_id primary key (user_id));


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
    add constraint col_fk_contact_person_id
    foreign key (contact_person_id)
    references col_tab_contact_person;

alter table col_tab_job_advertisement
    add constraint col_fk_j_address_id
    foreign key (address_id)
    references col_tab_address;

alter table col_tab_student
    add constraint col_fk_s_user_id
    foreign key (user_id)
    references col_tab_user;

alter table col_tab_application
    add constraint col_fk_a_user_id
    foreign key (user_id)
    references col_tab_student;

alter table col_tab_application
    add constraint col_fk_a_advertisement_id
    foreign key (advertisement_id)
    references col_tab_job_advertisement;

alter table col_tab_company
    add constraint col_fk_c_address_id
    foreign key (address_id)
    references col_tab_address;

alter table col_tab_message
    add constraint col_fk_m_r_user_id
    foreign key (recipient_id)
    references col_tab_user;

alter table col_tab_message
    add constraint col_fk_m_s_user_id
    foreign key (sender_id)
    references col_tab_user;

alter table col_tab_settings
    add constraint col_fk_se_user_id
    foreign key (user_id)
    references col_tab_user;


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

