drop table if exists animal;
drop table if exists animals;

--������� ��������
create table animals
(
    idanimal bigserial NOT NULL PRIMARY KEY,
    type_animal varchar(30),		-- ��� ���������
    spec_animal varchar(30), 	-- �������� ���������  
    weight integer,     	-- ��� ���������
    cage int,			-- ����� ������
    feed_type varchar(30),	-- ����
    volume integer		-- ���-�� ����
);

insert into  animals (type_animal, spec_animal, weight, cage, feed_type, volume) values ('������', '����', 300, 100, '����', 15);
insert into  animals (type_animal, spec_animal, weight, cage, feed_type, volume) values ('������', '����', 200, 100, '����', 10);
insert into  animals (type_animal, spec_animal, weight, cage, feed_type, volume) values ('������', '���', 250, 110, '����', 10);
insert into  animals (type_animal, spec_animal, weight, cage, feed_type, volume) values ('������', '�������', 500, 120, '����', 20);
insert into  animals (type_animal, spec_animal, weight, cage, feed_type, volume) values ('����������', '����', 3000, 200, '������', 200);
insert into  animals (type_animal, spec_animal, weight, cage, feed_type, volume) values ('����������', '�����', 1000, 210, '������', 100);
insert into  animals (type_animal, spec_animal, weight, cage, feed_type, volume) values ('������', '�������', 300, 300, '�����', 30);
