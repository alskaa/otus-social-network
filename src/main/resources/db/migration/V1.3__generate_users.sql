create table if not exists temp
(
    first_name varchar(100),
    last_name varchar(100),
    age bigint,
    city varchar(36)
);

COPY temp(first_name, age, city)
    FROM '/etc/people.csv'
    DELIMITER ','
    CSV HEADER;

update temp
set first_name = split_part(first_name, ' ', 1),
    last_name = split_part(first_name, ' ', 2);

DO $do$
    DECLARE rec record;
    BEGIN FOR rec IN (
        SELECT
            temp.first_name,
            temp.last_name,
            temp.age,
            temp.city
        FROM
            temp
    ) LOOP INSERT INTO user_info
           VALUES
               (
                   nextval('public.user_info_id_seq'),
                   gen_random_uuid(),
                   rec.first_name,
                   rec.last_name,
                   NOW() + '-50 years' +  random() * ((NOW() + '-10 years') - (NOW() + '-50 years')),
                   (array['MALE', 'FEMALE', null])[floor(random() * 3 + 1)],
                   null,
                   rec.city
               );
        END LOOP;
    END;
$do$;

drop table temp;