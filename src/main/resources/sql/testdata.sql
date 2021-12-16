
delete from col_tab_student
where user_id = 20000000;
delete from col_tab_user
where user_id = 20000000;
delete from col_tab_company
where company_id = 40000000;
delete from col_tab_address
where address_id = 10000000;


insert into col_tab_address
    values (10000000, 53757, 'Sankt Augustin', 'Deutschland', 'Hofgartenstrasse', 15);

insert into col_tab_company
    values (40000000,
        'Nur ein Test...',
        'https://test.com/awesome',
        012398489,
        'Erfolgreiche Test Firma GmbH',
        10000000,
        'testcompany@test.com',
        '09878934');

insert into col_tab_user
    values(default,
           'Frederick',
           'Behringer',
           10000000,
           to_date('05 Dec 2000', 'DD Mon YYYY'),
           'plain_password',
           '192345',
           'Herr',
           '-',
           'mail',
           'st');

insert into col_tab_student
    values('Ich bin ein toller student!',
           'paymoneytofredi.com',
           'M.Sc. Informatik',
           'Kann gut Klettern',
           'Klettern, Datenbanken',
           10000000,
           'Komplexe Softwaresysteme');
