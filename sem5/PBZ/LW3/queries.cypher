//Создание данных
CREATE
(hz1:HabitatZone {name: 'Саванна', description: 'Широкая равнина с редкими деревьями и обилием трав и кустарников.'}),
(hz2:HabitatZone {name: 'Тундра', description: 'Степная местность с коротким летом и длинной зимой, покрытая мхами и лишайниками.'}),
(hz3:HabitatZone {name: 'Тропический лес', description: 'Плотный лес с высоким уровнем осадков и разнообразием растений и животных.'}),
(hz5:HabitatZone {name: 'Лес', description: 'Зеленая зона с густыми деревьями и разнообразными экосистемами.'}),
(hz6:HabitatZone {name: 'Горы', description: 'Высокие участки земли с изменяющимися климатическими условиями и флорой.'});

CREATE
(wp1:WinteringPlace {code: 'A01', country: 'Россия', arrival_date: date('2024-11-01'), departure_date: date('2025-03-01')}),
(wp2:WinteringPlace {code: 'B02', country: 'Казахстан', arrival_date: date('2024-12-15'), departure_date: date('2025-02-28')});

CREATE
(ft1:FeedType {name: 'Зерновой'}),
(ft2:FeedType {name: 'Мясной'});

CREATE
(fr1:FeedingRation {name: 'Рацион утренний для хищников'}),
(fr2:FeedingRation {name: 'Рацион вечерний для хищников'}),
(fr3:FeedingRation {name: 'Рацион утренний для травоядных'}),
(fr4:FeedingRation {name: 'Рацион вечерний для травоядных'});

CREATE
(e1:Employee {name: 'Иван Иванов', birthdate: date('1985-05-12'), phone_number: '+375 29 777 33 44', employee_type: 'Ветеринар', marital_status: 'M'}),
(e2:Employee {name: 'Мария Смирнова', birthdate: date('1990-08-23'), phone_number: '+375 29 747 33 44', employee_type: 'Смотритель', marital_status: 'M'}),
(e3:Employee {name: 'Сергей Петров', birthdate: date('1992-03-15'), phone_number: '+375 29 123 45 67', employee_type: 'Смотритель', marital_status: 'S'});

CREATE
(p1:Pet {name: 'Белка', birthdate: date('2020-06-15'), sex: 'Ж'}),
(p2:Pet {name: 'Лео', birthdate: date('2019-08-03'), sex: 'М'});

CREATE
(b1:Bird:Pet {name: 'Аист', birthdate: date('2021-04-10'), sex: 'М'}),
(b2:Bird:Pet {name: 'Соловей', birthdate: date('2022-05-05'), sex: 'Ж'});

CREATE
(r1:Reptile:Pet {name: 'Игуана', birthdate: date('2020-02-14'), sex: 'М', normal_temperature: 30.00, sleep_period: duration('PT12H')}),
(r2:Reptile:Pet {name: 'Ящерица', birthdate: date('2021-06-21'), sex: 'Ж', normal_temperature: 28.50, sleep_period: duration('PT10H')});



//Создание связей
MATCH (p1:Pet {name: 'Белка'}), (hz1:HabitatZone {name: 'Саванна'})
CREATE (p1)-[:HAS_HABITAT_ZONE]->(hz1);

MATCH (p2:Pet {name: 'Лео'}), (hz2:HabitatZone {name: 'Тундра'})
CREATE (p2)-[:HAS_HABITAT_ZONE]->(hz2);

MATCH (b1:Bird {name: 'Аист'}), (hz6:HabitatZone {name: 'Горы'})
CREATE (b1)-[:HAS_HABITAT_ZONE]->(hz6);

MATCH (b2:Bird {name: 'Соловей'}), (hz5:HabitatZone {name: 'Лес'})
CREATE (b2)-[:HAS_HABITAT_ZONE]->(hz5);

MATCH (r1:Reptile {name: 'Игуана'}), (hz3:HabitatZone {name: 'Тропический лес'})
CREATE (r1)-[:HAS_HABITAT_ZONE]->(hz3);

MATCH (r2:Reptile {name: 'Ящерица'}), (hz1:HabitatZone {name: 'Саванна'})
CREATE (r2)-[:HAS_HABITAT_ZONE]->(hz1);

MATCH (fr1:FeedingRation {name: 'Рацион утренний для хищников'}), (ft1:FeedType {name: 'Мясной'})
CREATE (fr1)-[:HAS_FEED_TYPE]->(ft1);

MATCH (fr2:FeedingRation {name: 'Рацион вечерний для хищников'}), (ft1:FeedType {name: 'Мясной'})
CREATE (fr2)-[:HAS_FEED_TYPE]->(ft1);

MATCH (fr3:FeedingRation {name: 'Рацион утренний для травоядных'}), (ft2:FeedType {name: 'Зерновой'})
CREATE (fr3)-[:HAS_FEED_TYPE]->(ft2);

MATCH (fr4:FeedingRation {name: 'Рацион вечерний для травоядных'}), (ft2:FeedType {name: 'Зерновой'})
CREATE (fr4)-[:HAS_FEED_TYPE]->(ft2);


MATCH (p1:Pet {name: 'Белка'}), (fr3:FeedingRation {name: 'Рацион утренний для травоядных'})
CREATE (p1)-[:HAS_FEEDING_RATION]->(fr3);

MATCH (p2:Pet {name: 'Лео'}), (fr2:FeedingRation {name: 'Рацион вечерний для хищников'})
CREATE (p2)-[:HAS_FEEDING_RATION]->(fr2);

MATCH (b1:Bird {name: 'Аист'}), (fr1:FeedingRation {name: 'Рацион утренний для хищников'})
CREATE (b1)-[:HAS_FEEDING_RATION]->(fr1);

MATCH (b2:Bird {name: 'Соловей'}), (fr4:FeedingRation {name: 'Рацион вечерний для травоядных'})
CREATE (b2)-[:HAS_FEEDING_RATION]->(fr4);

MATCH (r1:Reptile {name: 'Игуана'}), (fr2:FeedingRation {name: 'Рацион вечерний для хищников'})
CREATE (r1)-[:HAS_FEEDING_RATION]->(fr2);

MATCH (r2:Reptile {name: 'Ящерица'}), (fr3:FeedingRation {name: 'Рацион утренний для травоядных'})
CREATE (r2)-[:HAS_FEEDING_RATION]->(fr3);

MATCH (b1:Bird {name: 'Аист'}), (wp1:WinteringPlace {code: 'A01'})
CREATE (b1)-[:WINTERING_IN]->(wp1);

MATCH (b2:Bird {name: 'Соловей'}), (wp2:WinteringPlace {code: 'B02'})
CREATE (b2)-[:WINTERING_IN]->(wp2);


MATCH (p1:Pet {name: 'Белка'}), (e1:Employee {name: 'Иван Иванов'}), (e2:Employee {name: 'Мария Смирнова'})
CREATE (p1)-[:VET_CARED_BY]->(e1), (p1)-[:KEEPER_CARED_BY]->(e2);

MATCH (p2:Pet {name: 'Лео'}), (e1:Employee {name: 'Иван Иванов'}), (e3:Employee {name: 'Сергей Петров'})
CREATE (p2)-[:VET_CARED_BY]->(e1), (p2)-[:KEEPER_CARED_BY]->(e3);

MATCH (b1:Bird {name: 'Аист'}), (e1:Employee {name: 'Иван Иванов'}), (e2:Employee {name: 'Мария Смирнова'})
CREATE (b1)-[:VET_CARED_BY]->(e1), (b1)-[:KEEPER_CARED_BY]->(e2);

MATCH (b2:Bird {name: 'Соловей'}), (e1:Employee {name: 'Иван Иванов'}), (e3:Employee {name: 'Сергей Петров'})
CREATE (b2)-[:VET_CARED_BY]->(e1), (b2)-[:KEEPER_CARED_BY]->(e3);

MATCH (r1:Reptile {name: 'Игуана'}), (e1:Employee {name: 'Иван Иванов'}), (e2:Employee {name: 'Мария Смирнова'})
CREATE (r1)-[:VET_CARED_BY]->(e1), (r1)-[:KEEPER_CARED_BY]->(e2);

MATCH (r2:Reptile {name: 'Ящерица'}), (e1:Employee {name: 'Иван Иванов'}), (e3:Employee {name: 'Сергей Петров'})
CREATE (r2)-[:VET_CARED_BY]->(e1), (r2)-[:KEEPER_CARED_BY]->(e3);


MATCH (e1:Employee {name: 'Иван Иванов'}), (e2:Employee {name: 'Мария Смирнова'}) CREATE (e1)-[:SPOUSE]->(e2);


//Запросы

//1. Найти тип рациона для каждого питомца
MATCH (p:Pet)-[:HAS_FEEDING_RATION]->(:FeedingRation)-[:HAS_FEED_TYPE]->(ft:FeedType)
RETURN ft.name AS FeedType, p.name AS PetName;

//2. Найти всех сотрудников, которые ухаживают за питомцем с именем name
MATCH (p:Pet {name: $name})-[:KEEPER_CARED_BY|VET_CARED_BY]->(e:Employee)
RETURN e;

//3. Найти всех животных, находящихся в определенной зоне обитания
MATCH (hz:HabitatZone {name: $zoneName})<-[:HAS_HABITAT_ZONE]-(p:Pet)
RETURN p;

//4. Найти всех животных, которые находятся в одной зоне обитания и получают одинаковый рацион питания
MATCH (p1:Pet)-[:HAS_HABITAT_ZONE]->(hz:HabitatZone)<-[:HAS_HABITAT_ZONE]-(p2:Pet),
(p1)-[:HAS_FEEDING_RATION]->(fr:FeedingRation)<-[:HAS_FEEDING_RATION]-(p2)
WHERE id(p1) < id(p2)
RETURN p1.name AS Animal1, p2.name AS Animal2, hz.name AS HabitatZone, fr.name AS FeedingRation;

//5. Найти всех животных, за которыми ухаживают сотрудники, состоящие в браке друг с другом:
MATCH (e1:Employee)-[:SPOUSE]->(e2:Employee),
(p:Pet)-[:KEEPER_CARED_BY|VET_CARED_BY]->(e1),
(p)-[:KEEPER_CARED_BY|VET_CARED_BY]->(e2)
RETURN p.name AS Pet, e1.name AS Spouse1, e2.name AS Spouse2;

//6. Получить для каждого сотрудника кол-во животных, за которыми он ухаживает
MATCH (e:Employee)<-[:KEEPER_CARED_BY|VET_CARED_BY]-(p:Pet)
WITH e, COUNT(p) AS AnimalCount
ORDER BY AnimalCount DESC
RETURN e.name AS Employee, AnimalCount;

//7. Найти питомцев, которые живут в зоне обитания с наибольшим числом других животных
MATCH (hz:HabitatZone)<-[:HAS_HABITAT_ZONE]-(p:Pet)
WITH hz, COUNT(p) AS PetCount
ORDER BY PetCount DESC
LIMIT 1
MATCH (hz)<-[:HAS_HABITAT_ZONE]-(p:Pet)
RETURN hz.name AS HabitatZone, p.name AS Pet;

//8. Найти птиц, которые в переданный день будут находится в месте зимования.
MATCH (b:Bird)-[:WINTERING_IN]->(wp:WinteringPlace)
WHERE $date >= wp.arrival_date AND $date <= wp.departure_date
RETURN b.name AS Bird, wp.country AS Country, wp.arrival_date AS ArrivalDate, wp.departure_date AS DepartureDate;


//9.  Найти питомца по имени и вывести его рацион питания, зону обитания и имена сотрудников, которые за ним присматривают
MATCH (p:Pet {name: $name})
MATCH (p)-[:HAS_FEEDING_RATION]->(fr:FeedingRation)
MATCH (p)-[:HAS_HABITAT_ZONE]->(hz:HabitatZone)
MATCH (p)-[:KEEPER_CARED_BY]->(k:Employee)
MATCH (p)-[:VET_CARED_BY]->(v:Employee)
RETURN
p.name AS PetName,
fr.name AS FeedingRation,
hz.name AS HabitatZone,
k.name AS Keeper,
v.name AS Veterinarian;


//10. Получить список всех зон обитания и количество животных в каждой зоне
MATCH (hz:HabitatZone)<-[:HAS_HABITAT_ZONE]-(p:Pet)
WITH hz, COUNT(p) AS PetCount
RETURN hz.name AS HabitatZone, PetCount
ORDER BY PetCount DESC;


//11. Получить для питомца все узлы, с которыми он имеет связь по имени
MATCH (p:Pet {name: $name})-[r]-(node)
RETURN p, r, node;