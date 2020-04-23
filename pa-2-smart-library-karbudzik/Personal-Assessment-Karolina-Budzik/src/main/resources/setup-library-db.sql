DROP TABLE IF EXISTS publishers CASCADE;
DROP TABLE IF EXISTS authors CASCADE;
DROP TABLE IF EXISTS books;

CREATE TABLE publishers(
	id TEXT PRIMARY KEY,
	"name" VARCHAR(255) UNIQUE NOT NULL 	
);

CREATE TABLE authors(
	id serial PRIMARY KEY,
	"first_name" VARCHAR(50) NOT NULL,
  	"surname" VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS books (
    "isbn" BIGINT PRIMARY KEY,
    "author_id" INT REFERENCES authors(ID) NOT NULL,
    "title" TEXT NOT NULL,
    "publisher_id" TEXT REFERENCES publishers(id),
    "publication_year" INT NULL,
    "price" FLOAT NULL
);

INSERT INTO authors ("first_name", "surname") VALUES
	('Tom',	'Hanks'),
    ('Dexter', 'Dias'),
    ('Jane', 'Austen'),
    ('Gunter', 'Grass'),
    ('Deborah', 'Moggah'),
    ('Margaret', 'Atwood'),
    ('Nigella', 'Lawson'),
    ('Mark', 'Twain');

INSERT INTO publishers ("id", "name") VALUES
    ('2ak','William Heinemann'),
    ('112','Poltex'),
    ('83f','StoryBox'),
    ('5k4','Vintage'),
    ('cub','Chatto & Windus'),
    ('2g8','Wordsworth');

INSERT INTO books VALUES
    (9780099540656,4,'The Tin Drum','5k4',2017,56),
    (9780099594024,6,'Hag Seed','5k4',2017,45),
    (9780701172879,7,'Nigella Bites','cub',2001,13.99),
    (9780701184605,7,'Chatto & Windus','cub',2010,87.8),
    (9780701189358,7,'Simply Nigella: Feel Good Food','cub',2015,69.9),
    (9781784700805,5,'Tulip Fever','5k4',2017,34),
    (9781784703684,4,'Of All That Ends','5k4',2017,45),
    (9781784741631,7,'At My Table','5k4',2017,115.5),
    (9781784871444,6,'The Handmaids Tale','5k4',2016,44.1),
    (9781784871581,8,'The Adventures of Tom Sawyer','5k4',2016,11.6),
    (9781784871727,3,'Pride and Prejudice','5k4',2016,11.6),
    (9781784871741,3,'Sense and Sensibility','5k4',2016,11.6),
    (9781785150162,2,'The Ten Types of Human','2ak',2017,62),
    (9781785151521,1,'Uncommon Type','2ak',2017,51.5),
    (9781840221831,8,'Tom Sawyer Abroad & Detective','2g8',2009,11.6),
    (9781840226362,8,'The Innocents Abroad','2g8',2010,11.6),
    (9781840226836,8,'Life on the Mississippi','2g8',2012,11.6),
    (9781853260117,8,'Tom Sawyer & Huckleberry Finn','2g8',2001,13),
    (9788375617634,3,'Pride and Prejudice','112',2017,29.9),
    (9788380976535,3,'Lady Susan','83f',2016,24.9);