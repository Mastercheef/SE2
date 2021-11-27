insert into col_tab_address
values (10000000, 53757, 'Sankt Augustin', 'Deutschland', 'Hofgartenstrasse', 15);

insert into col_tab_company
values (default,
        'Nur ein Test...',
        'https://test.com/awesome',
        012398489,
        'Erfolgreiche Test Firma GmbH',
        (SELECT MAX(address_id) FROM col_tab_address),
        'testcompany@test.com',
        '09878934');

insert into col_tab_user
    values(default,
           'Frederick',
           'Behringer',
           (SELECT MAX(address_id) FROM col_tab_address),
           to_date('05 Dec 2000', 'DD Mon YYYY'),
           'password',
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
           (SELECT MAX(user_id) FROM col_tab_user),
           'Komplexe Softwaresysteme');
