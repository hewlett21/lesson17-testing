drop table if exists animal;
drop table if exists animals;

--таблица животных
create table animals
(
    idanimal bigserial NOT NULL PRIMARY KEY,
    type_animal varchar(30),		-- вид животного
    spec_animal varchar(30), 	-- название животного  
    weight integer,     	-- вес животного
    cage int,			-- номер клетки
    feed_type varchar(30),	-- корм
    volume integer		-- кол-во пищи
);

insert into  animals (type_animal, spec_animal, weight, cage, feed_type, volume) values ('Хищник', 'Тигр', 300, 100, 'мясо', 15);
insert into  animals (type_animal, spec_animal, weight, cage, feed_type, volume) values ('Хищник', 'Тигр', 200, 100, 'мясо', 10);
insert into  animals (type_animal, spec_animal, weight, cage, feed_type, volume) values ('Хищник', 'Лев', 250, 110, 'мясо', 10);
insert into  animals (type_animal, spec_animal, weight, cage, feed_type, volume) values ('Хищник', 'Медведь', 500, 120, 'мясо', 20);
insert into  animals (type_animal, spec_animal, weight, cage, feed_type, volume) values ('Травоядное', 'Слон', 3000, 200, 'листья', 200);
insert into  animals (type_animal, spec_animal, weight, cage, feed_type, volume) values ('Травоядное', 'Жираф', 1000, 210, 'листья', 100);
insert into  animals (type_animal, spec_animal, weight, cage, feed_type, volume) values ('Примат', 'Горилла', 300, 300, 'банан', 30);
