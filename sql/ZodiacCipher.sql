-- Database: "Zodiac"

-- DROP DATABASE "Zodiac";

CREATE DATABASE "Zodiac"
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'English, United States'
       LC_CTYPE = 'English, United States'
       CONNECTION LIMIT = -1;
	   
-- Table: parts_of_speech

-- DROP TABLE parts_of_speech;

CREATE TABLE parts_of_speech
(
  word character varying NOT NULL,
  part_of_speech character(1) NOT NULL,
  frequency_weight integer DEFAULT 1,
  CONSTRAINT pk_word_pos PRIMARY KEY (part_of_speech, word )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE parts_of_speech
  OWNER TO postgres;

-- Index: idx_word

-- DROP INDEX idx_word;

CREATE INDEX idx_word
  ON parts_of_speech
  USING btree
  (word COLLATE pg_catalog."default" );
  

-- Table: cipher

-- DROP TABLE cipher;

CREATE TABLE cipher
(
  id SERIAL NOT NULL,
  "name" character varying NOT NULL,
  "rows" integer NOT NULL DEFAULT 0,
  columns integer NOT NULL DEFAULT 0,
  CONSTRAINT pk_cipher_id PRIMARY KEY (id ),
  CONSTRAINT unique_name UNIQUE (name )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE cipher
  OWNER TO postgres;

INSERT INTO "cipher"(id, "name", "rows", columns) VALUES
(1, 'zodiac340', 20, 17),
(2, 'zodiac408', 24, 17);	   


-- Table: ciphertext

-- DROP TABLE ciphertext;

CREATE TABLE ciphertext
(
  ciphertext_id integer NOT NULL,
  "value" character varying NOT NULL,
  cipher_id integer NOT NULL,
  CONSTRAINT pk_cipher_id_ciphertext_id PRIMARY KEY (cipher_id, ciphertext_id ),
  CONSTRAINT fk_cipher_id FOREIGN KEY (cipher_id)
      REFERENCES cipher (id) MATCH SIMPLE
      ON UPDATE  CASCADE ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE ciphertext
  OWNER TO postgres;

INSERT INTO ciphertext(cipher_id, ciphertext_id, "value") VALUES
('1', 0, 'h'),
('1', 1, 'e'),
('1', 2, 'r'),
('1', 3, 'greater'),
('1', 4, 'backp'),
('1', 5, 'backl'),
('1', 6, 'carrot'),
('1', 7, 'v'),
('1', 8, 'p'),
('1', 9, 'backk'),
('1', 10, 'i'),
('1', 11, 'bottomsemi'),
('1', 12, 'l'),
('1', 13, 't'),
('1', 14, 'g'),
('1', 15, 'topsemi'),
('1', 16, 'backd'),
('1', 17, 'n'),
('1', 18, 'backp'),
('1', 19, 'plus'),
('1', 20, 'b'),
('1', 21, 'vertstrike'),
('1', 22, 'fullbox'),
('1', 23, 'o'),
('1', 24, 'lrbox'),
('1', 25, 'd'),
('1', 26, 'w'),
('1', 27, 'y'),
('1', 28, 'dot'),
('1', 29, 'less'),
('1', 30, 'llbox'),
('1', 31, 'k'),
('1', 32, 'backf'),
('1', 33, 'horstrike'),
('1', 34, 'b'),
('1', 35, 'backy'),
('1', 36, 'rightdoti'),
('1', 37, 'backc'),
('1', 38, 'm'),
('1', 39, 'plus'),
('1', 40, 'u'),
('1', 41, 'z'),
('1', 42, 'g'),
('1', 43, 'w'),
('1', 44, 'vertstrike'),
('1', 45, 'horstrike'),
('1', 46, 'l'),
('1', 47, 'fullbox'),
('1', 48, 'zodiac'),
('1', 49, 'h'),
('1', 50, 'j'),
('1', 51, 's'),
('1', 52, 'backp'),
('1', 53, 'backp'),
('1', 54, 'tridot'),
('1', 55, 'carrot'),
('1', 56, 'backl'),
('1', 57, 'fulltri'),
('1', 58, 'llbox'),
('1', 59, 'v'),
('1', 60, 'rightsemi'),
('1', 61, 'backp'),
('1', 62, 'o'),
('1', 63, 'plus'),
('1', 64, 'plus'),
('1', 65, 'r'),
('1', 66, 'k'),
('1', 67, 'topsemi'),
('1', 68, 'box'),
('1', 69, 'tri'),
('1', 70, 'm'),
('1', 71, 'plus'),
('1', 72, 'zodiac'),
('1', 73, 'flipt'),
('1', 74, 'backj'),
('1', 75, 'backd'),
('1', 76, 'i'),
('1', 77, 'fullcircle'),
('1', 78, 'f'),
('1', 79, 'p'),
('1', 80, 'plus'),
('1', 81, 'p'),
('1', 82, 'leftsemi'),
('1', 83, 'backk'),
('1', 84, 'forslash'),
('1', 85, 'backp'),
('1', 86, 'fulltri'),
('1', 87, 'r'),
('1', 88, 'carrot'),
('1', 89, 'f'),
('1', 90, 'backl'),
('1', 91, 'o'),
('1', 92, 'minus'),
('1', 93, 'llbox'),
('1', 94, 'backd'),
('1', 95, 'c'),
('1', 96, 'backk'),
('1', 97, 'f'),
('1', 98, 'greater'),
('1', 99, 'topsemi'),
('1', 100, 'd'),
('1', 101, 'vertstrike'),
('1', 102, 'fullbox'),
('1', 103, 'fullcircle'),
('1', 104, 'plus'),
('1', 105, 'k'),
('1', 106, 'backq'),
('1', 107, 'lrbox'),
('1', 108, 'leftdoti'),
('1', 109, 'topsemi'),
('1', 110, 'u'),
('1', 111, 'backc'),
('1', 112, 'x'),
('1', 113, 'g'),
('1', 114, 'v'),
('1', 115, 'dot'),
('1', 116, 'zodiac'),
('1', 117, 'l'),
('1', 118, 'i'),
('1', 119, 'vertstrike'),
('1', 120, 'g'),
('1', 121, 'topsemi'),
('1', 122, 'j'),
('1', 123, 'backf'),
('1', 124, 'backj'),
('1', 125, 'fullbox'),
('1', 126, 'o'),
('1', 127, 'plus'),
('1', 128, 'box'),
('1', 129, 'n'),
('1', 130, 'y'),
('1', 131, 'zodiac'),
('1', 132, 'plus'),
('1', 133, 'boxdot'),
('1', 134, 'l'),
('1', 135, 'tri'),
('1', 136, 'backd'),
('1', 137, 'less'),
('1', 138, 'm'),
('1', 139, 'plus'),
('1', 140, 'backb'),
('1', 141, 'plus'),
('1', 142, 'z'),
('1', 143, 'r'),
('1', 144, 'topsemi'),
('1', 145, 'f'),
('1', 146, 'b'),
('1', 147, 'backc'),
('1', 148, 'backy'),
('1', 149, 'a'),
('1', 150, 'circledot'),
('1', 151, 'leftsemi'),
('1', 152, 'k'),
('1', 153, 'minus'),
('1', 154, 'zodiac'),
('1', 155, 'backl'),
('1', 156, 'u'),
('1', 157, 'v'),
('1', 158, 'plus'),
('1', 159, 'carrot'),
('1', 160, 'j'),
('1', 161, 'plus'),
('1', 162, 'o'),
('1', 163, 'backp'),
('1', 164, 'tridot'),
('1', 165, 'less'),
('1', 166, 'f'),
('1', 167, 'b'),
('1', 168, 'backy'),
('1', 169, 'minus'),
('1', 170, 'u'),
('1', 171, 'plus'),
('1', 172, 'r'),
('1', 173, 'forslash'),
('1', 174, 'fullcircle'),
('1', 175, 'flipt'),
('1', 176, 'e'),
('1', 177, 'i'),
('1', 178, 'd'),
('1', 179, 'y'),
('1', 180, 'b'),
('1', 181, 'backp'),
('1', 182, 'backb'),
('1', 183, 't'),
('1', 184, 'm'),
('1', 185, 'k'),
('1', 186, 'o'),
('1', 187, 'topsemi'),
('1', 188, 'less'),
('1', 189, 'backc'),
('1', 190, 'backl'),
('1', 191, 'r'),
('1', 192, 'j'),
('1', 193, 'i'),
('1', 194, 'llbox'),
('1', 195, 'fullcircle'),
('1', 196, 't'),
('1', 197, 'leftsemi'),
('1', 198, 'm'),
('1', 199, 'dot'),
('1', 200, 'plus'),
('1', 201, 'p'),
('1', 202, 'b'),
('1', 203, 'f'),
('1', 204, 'zodiac'),
('1', 205, 'circledot'),
('1', 206, 'tri'),
('1', 207, 's'),
('1', 208, 'backy'),
('1', 209, 'fullbox'),
('1', 210, 'plus'),
('1', 211, 'n'),
('1', 212, 'i'),
('1', 213, 'fullcircle'),
('1', 214, 'f'),
('1', 215, 'b'),
('1', 216, 'backc'),
('1', 217, 'vertstrike'),
('1', 218, 'leftdoti'),
('1', 219, 'fulltri'),
('1', 220, 'r'),
('1', 221, 'backl'),
('1', 222, 'g'),
('1', 223, 'f'),
('1', 224, 'n'),
('1', 225, 'carrot'),
('1', 226, 'backf'),
('1', 227, 'fullcircle'),
('1', 228, 'topsemi'),
('1', 229, 'leftsemi'),
('1', 230, 'backb'),
('1', 231, 'dot'),
('1', 232, 'backc'),
('1', 233, 'v'),
('1', 234, 'leftsemi'),
('1', 235, 'flipt'),
('1', 236, 'plus'),
('1', 237, 'plus'),
('1', 238, 'backy'),
('1', 239, 'b'),
('1', 240, 'x'),
('1', 241, 'bottomsemi'),
('1', 242, 'llbox'),
('1', 243, 'rightdoti'),
('1', 244, 'leftsemi'),
('1', 245, 'tri'),
('1', 246, 'c'),
('1', 247, 'e'),
('1', 248, 'greater'),
('1', 249, 'v'),
('1', 250, 'u'),
('1', 251, 'z'),
('1', 252, 'fullcircle'),
('1', 253, 'minus'),
('1', 254, 'plus'),
('1', 255, 'i'),
('1', 256, 'backc'),
('1', 257, 'dot'),
('1', 258, 'rightsemi'),
('1', 259, 'zodiac'),
('1', 260, 'b'),
('1', 261, 'k'),
('1', 262, 'vertstrike'),
('1', 263, 'o'),
('1', 264, 'backp'),
('1', 265, 'carrot'),
('1', 266, 'dot'),
('1', 267, 'backf'),
('1', 268, 'm'),
('1', 269, 'backq'),
('1', 270, 'g'),
('1', 271, 'topsemi'),
('1', 272, 'r'),
('1', 273, 'backc'),
('1', 274, 't'),
('1', 275, 'plus'),
('1', 276, 'l'),
('1', 277, 'bottomsemi'),
('1', 278, 'circledot'),
('1', 279, 'c'),
('1', 280, 'less'),
('1', 281, 'plus'),
('1', 282, 'f'),
('1', 283, 'backl'),
('1', 284, 'w'),
('1', 285, 'b'),
('1', 286, 'i'),
('1', 287, 'horstrike'),
('1', 288, 'l'),
('1', 289, 'plus'),
('1', 290, 'plus'),
('1', 291, 'horstrike'),
('1', 292, 'w'),
('1', 293, 'c'),
('1', 294, 'zodiac'),
('1', 295, 'w'),
('1', 296, 'backc'),
('1', 297, 'p'),
('1', 298, 'o'),
('1', 299, 's'),
('1', 300, 'h'),
('1', 301, 't'),
('1', 302, 'forslash'),
('1', 303, 'vertstrike'),
('1', 304, 'horstrike'),
('1', 305, 'backp'),
('1', 306, 'i'),
('1', 307, 'f'),
('1', 308, 'backk'),
('1', 309, 'backd'),
('1', 310, 'w'),
('1', 311, 'less'),
('1', 312, 'tridot'),
('1', 313, 'flipt'),
('1', 314, 'b'),
('1', 315, 'box'),
('1', 316, 'y'),
('1', 317, 'o'),
('1', 318, 'b'),
('1', 319, 'llbox'),
('1', 320, 'minus'),
('1', 321, 'c'),
('1', 322, 'backc'),
('1', 323, 'greater'),
('1', 324, 'm'),
('1', 325, 'd'),
('1', 326, 'h'),
('1', 327, 'n'),
('1', 328, 'backp'),
('1', 329, 'backk'),
('1', 330, 's'),
('1', 331, 'zodiac'),
('1', 332, 'z'),
('1', 333, 'o'),
('1', 334, 'fulltri'),
('1', 335, 'a'),
('1', 336, 'i'),
('1', 337, 'k'),
('1', 338, 'leftdoti'),
('1', 339, 'plus');

INSERT INTO ciphertext(cipher_id, ciphertext_id, "value") VALUES
('2', 0, 'tri'),
('2', 1, 'lrbox'),
('2', 2, 'p'),
('2', 3, 'forslash'),
('2', 4, 'z'),
('2', 5, 'forslash'),
('2', 6, 'u'),
('2', 7, 'b'),
('2', 8, 'lrbox'),
('2', 9, 'backk'),
('2', 10, 'o'),
('2', 11, 'r'),
('2', 12, 'pi'),
('2', 13, 'backp'),
('2', 14, 'x'),
('2', 15, 'pi'),
('2', 16, 'b'),
('2', 17, 'w'),
('2', 18, 'v'),
('2', 19, 'plus'),
('2', 20, 'backe'),
('2', 21, 'g'),
('2', 22, 'y'),
('2', 23, 'f'),
('2', 24, 'circledot'),
('2', 25, 'tri'),
('2', 26, 'h'),
('2', 27, 'p'),
('2', 28, 'boxdot'),
('2', 29, 'k'),
('2', 30, 'anchor'),
('2', 31, 'backq'),
('2', 32, 'y'),
('2', 33, 'backe'),
('2', 34, 'm'),
('2', 35, 'j'),
('2', 36, 'y'),
('2', 37, 'carrot'),
('2', 38, 'u'),
('2', 39, 'i'),
('2', 40, 'backk'),
('2', 41, 'tridot'),
('2', 42, 'backq'),
('2', 43, 't'),
('2', 44, 'flipt'),
('2', 45, 'n'),
('2', 46, 'q'),
('2', 47, 'y'),
('2', 48, 'd'),
('2', 49, 'fullcircle'),
('2', 50, 'horstrike'),
('2', 51, 's'),
('2', 52, 'vertstrike'),
('2', 53, 'forslash'),
('2', 54, 'tri'),
('2', 55, 'fullbox'),
('2', 56, 'b'),
('2', 57, 'p'),
('2', 58, 'o'),
('2', 59, 'r'),
('2', 60, 'a'),
('2', 61, 'u'),
('2', 62, 'lrbox'),
('2', 63, 'backf'),
('2', 64, 'r'),
('2', 65, 'backl'),
('2', 66, 'backq'),
('2', 67, 'e'),
('2', 68, 'backk'),
('2', 69, 'carrot'),
('2', 70, 'l'),
('2', 71, 'm'),
('2', 72, 'z'),
('2', 73, 'j'),
('2', 74, 'backd'),
('2', 75, 'backr'),
('2', 76, 'backslash'),
('2', 77, 'backp'),
('2', 78, 'f'),
('2', 79, 'h'),
('2', 80, 'v'),
('2', 81, 'w'),
('2', 82, 'backe'),
('2', 83, 'fulltri'),
('2', 84, 'y'),
('2', 85, 'boxdot'),
('2', 86, 'plus'),
('2', 87, 'backq'),
('2', 88, 'g'),
('2', 89, 'd'),
('2', 90, 'tri'),
('2', 91, 'k'),
('2', 92, 'i'),
('2', 93, 'horstrike'),
('2', 94, 'circledot'),
('2', 95, 'backq'),
('2', 96, 'x'),
('2', 97, 'fulltri'),
('2', 98, 'fullcircle'),
('2', 99, 'zodiac'),
('2', 100, 's'),
('2', 101, 'vertstrike'),
('2', 102, 'r'),
('2', 103, 'n'),
('2', 104, 'flipt'),
('2', 105, 'anchor'),
('2', 106, 'y'),
('2', 107, 'e'),
('2', 108, 'backl'),
('2', 109, 'o'),
('2', 110, 'fulltri'),
('2', 111, 'backq'),
('2', 112, 'g'),
('2', 113, 'b'),
('2', 114, 't'),
('2', 115, 'q'),
('2', 116, 's'),
('2', 117, 'fullbox'),
('2', 118, 'b'),
('2', 119, 'l'),
('2', 120, 'backd'),
('2', 121, 'forslash'),
('2', 122, 'p'),
('2', 123, 'fullbox'),
('2', 124, 'b'),
('2', 125, 'boxdot'),
('2', 126, 'x'),
('2', 127, 'backq'),
('2', 128, 'e'),
('2', 129, 'h'),
('2', 130, 'm'),
('2', 131, 'u'),
('2', 132, 'carrot'),
('2', 133, 'r'),
('2', 134, 'r'),
('2', 135, 'backk'),
('2', 136, 'backc'),
('2', 137, 'z'),
('2', 138, 'k'),
('2', 139, 'backq'),
('2', 140, 'backp'),
('2', 141, 'i'),
('2', 142, 'horstrike'),
('2', 143, 'w'),
('2', 144, 'backq'),
('2', 145, 'anchor'),
('2', 146, 'fulltri'),
('2', 147, 'fullcircle'),
('2', 148, 'l'),
('2', 149, 'm'),
('2', 150, 'backr'),
('2', 151, 'tri'),
('2', 152, 'fullbox'),
('2', 153, 'b'),
('2', 154, 'p'),
('2', 155, 'd'),
('2', 156, 'r'),
('2', 157, 'plus'),
('2', 158, 'backj'),
('2', 159, 'pi'),
('2', 160, 'circledot'),
('2', 161, 'backslash'),
('2', 162, 'n'),
('2', 163, 'vertstrike'),
('2', 164, 'backe'),
('2', 165, 'e'),
('2', 166, 'u'),
('2', 167, 'h'),
('2', 168, 'backk'),
('2', 169, 'f'),
('2', 170, 'z'),
('2', 171, 'backc'),
('2', 172, 'backp'),
('2', 173, 'o'),
('2', 174, 'v'),
('2', 175, 'w'),
('2', 176, 'i'),
('2', 177, 'fullcircle'),
('2', 178, 'plus'),
('2', 179, 'flipt'),
('2', 180, 'l'),
('2', 181, 'horstrike'),
('2', 182, 'backl'),
('2', 183, 'carrot'),
('2', 184, 'r'),
('2', 185, 'circledot'),
('2', 186, 'h'),
('2', 187, 'i'),
('2', 188, 'tri'),
('2', 189, 'd'),
('2', 190, 'r'),
('2', 191, 'box'),
('2', 192, 't'),
('2', 193, 'y'),
('2', 194, 'backr'),
('2', 195, 'backslash'),
('2', 196, 'backd'),
('2', 197, 'backe'),
('2', 198, 'forslash'),
('2', 199, 'boxdot'),
('2', 200, 'x'),
('2', 201, 'j'),
('2', 202, 'q'),
('2', 203, 'a'),
('2', 204, 'p'),
('2', 205, 'fullcircle'),
('2', 206, 'm'),
('2', 207, 'fulltri'),
('2', 208, 'r'),
('2', 209, 'u'),
('2', 210, 'flipt'),
('2', 211, 'lrbox'),
('2', 212, 'l'),
('2', 213, 'horstrike'),
('2', 214, 'n'),
('2', 215, 'v'),
('2', 216, 'e'),
('2', 217, 'k'),
('2', 218, 'h'),
('2', 219, 'pi'),
('2', 220, 'g'),
('2', 221, 'backr'),
('2', 222, 'i'),
('2', 223, 'anchor'),
('2', 224, 'j'),
('2', 225, 'backk'),
('2', 226, 'fullcircle'),
('2', 227, 'tri'),
('2', 228, 'fulltri'),
('2', 229, 'l'),
('2', 230, 'm'),
('2', 231, 'backl'),
('2', 232, 'n'),
('2', 233, 'a'),
('2', 234, 'horstrike'),
('2', 235, 'z'),
('2', 236, 'vertstrike'),
('2', 237, 'p'),
('2', 238, 'zodiac'),
('2', 239, 'u'),
('2', 240, 'backp'),
('2', 241, 'backk'),
('2', 242, 'a'),
('2', 243, 'tri'),
('2', 244, 'fullbox'),
('2', 245, 'b'),
('2', 246, 'v'),
('2', 247, 'w'),
('2', 248, 'backslash'),
('2', 249, 'plus'),
('2', 250, 'v'),
('2', 251, 't'),
('2', 252, 'flipt'),
('2', 253, 'o'),
('2', 254, 'p'),
('2', 255, 'carrot'),
('2', 256, 'pi'),
('2', 257, 's'),
('2', 258, 'backr'),
('2', 259, 'backl'),
('2', 260, 'backf'),
('2', 261, 'u'),
('2', 262, 'backe'),
('2', 263, 'circledot'),
('2', 264, 'tridot'),
('2', 265, 'd'),
('2', 266, 'zodiac'),
('2', 267, 'g'),
('2', 268, 'lrbox'),
('2', 269, 'lrbox'),
('2', 270, 'i'),
('2', 271, 'm'),
('2', 272, 'n'),
('2', 273, 'backk'),
('2', 274, 'horstrike'),
('2', 275, 's'),
('2', 276, 'backc'),
('2', 277, 'e'),
('2', 278, 'forslash'),
('2', 279, 'tri'),
('2', 280, 'lrbox'),
('2', 281, 'lrbox'),
('2', 282, 'z'),
('2', 283, 'backf'),
('2', 284, 'a'),
('2', 285, 'p'),
('2', 286, 'fullbox'),
('2', 287, 'b'),
('2', 288, 'v'),
('2', 289, 'backp'),
('2', 290, 'backe'),
('2', 291, 'x'),
('2', 292, 'backq'),
('2', 293, 'w'),
('2', 294, 'backq'),
('2', 295, 'box'),
('2', 296, 'f'),
('2', 297, 'fullbox'),
('2', 298, 'fulltri'),
('2', 299, 'backc'),
('2', 300, 'plus'),
('2', 301, 'boxdot'),
('2', 302, 'tri'),
('2', 303, 'a'),
('2', 304, 'tri'),
('2', 305, 'b'),
('2', 306, 'lrbox'),
('2', 307, 'o'),
('2', 308, 't'),
('2', 309, 'fullcircle'),
('2', 310, 'r'),
('2', 311, 'u'),
('2', 312, 'backc'),
('2', 313, 'plus'),
('2', 314, 'box'),
('2', 315, 'backd'),
('2', 316, 'y'),
('2', 317, 'backq'),
('2', 318, 'box'),
('2', 319, 'carrot'),
('2', 320, 's'),
('2', 321, 'backq'),
('2', 322, 'w'),
('2', 323, 'v'),
('2', 324, 'z'),
('2', 325, 'backe'),
('2', 326, 'g'),
('2', 327, 'y'),
('2', 328, 'k'),
('2', 329, 'e'),
('2', 330, 'box'),
('2', 331, 't'),
('2', 332, 'y'),
('2', 333, 'a'),
('2', 334, 'tri'),
('2', 335, 'lrbox'),
('2', 336, 'fullbox'),
('2', 337, 'l'),
('2', 338, 'flipt'),
('2', 339, 'box'),
('2', 340, 'h'),
('2', 341, 'anchor'),
('2', 342, 'f'),
('2', 343, 'b'),
('2', 344, 'x'),
('2', 345, 'tri'),
('2', 346, 'zodiac'),
('2', 347, 'x'),
('2', 348, 'a'),
('2', 349, 'd'),
('2', 350, 'backd'),
('2', 351, 'backslash'),
('2', 352, 'tridot'),
('2', 353, 'l'),
('2', 354, 'anchor'),
('2', 355, 'pi'),
('2', 356, 'backq'),
('2', 357, 'box'),
('2', 358, 'backe'),
('2', 359, 'backd'),
('2', 360, 'fullbox'),
('2', 361, 'fullbox'),
('2', 362, 'circledot'),
('2', 363, 'backe'),
('2', 364, 'fullcircle'),
('2', 365, 'p'),
('2', 366, 'o'),
('2', 367, 'r'),
('2', 368, 'x'),
('2', 369, 'q'),
('2', 370, 'f'),
('2', 371, 'lrbox'),
('2', 372, 'g'),
('2', 373, 'backc'),
('2', 374, 'z'),
('2', 375, 'boxdot'),
('2', 376, 'j'),
('2', 377, 't'),
('2', 378, 'flipt'),
('2', 379, 'backq'),
('2', 380, 'box'),
('2', 381, 'fulltri'),
('2', 382, 'j'),
('2', 383, 'i'),
('2', 384, 'plus'),
('2', 385, 'backr'),
('2', 386, 'b'),
('2', 387, 'p'),
('2', 388, 'q'),
('2', 389, 'w'),
('2', 390, 'circledot'),
('2', 391, 'v'),
('2', 392, 'e'),
('2', 393, 'x'),
('2', 394, 'backr'),
('2', 395, 'tri'),
('2', 396, 'w'),
('2', 397, 'i'),
('2', 398, 'circledot'),
('2', 399, 'backq'),
('2', 400, 'e'),
('2', 401, 'h'),
('2', 402, 'm'),
('2', 403, 'horstrike'),
('2', 404, 'pi'),
('2', 405, 'u'),
('2', 406, 'i'),
('2', 407, 'backk');

-- Table: solution_set

-- DROP TABLE solution_set;

CREATE TABLE solution_set
(
  id serial NOT NULL,
  name character varying(50),
  cipher_id integer NOT NULL,
  created_timestamp timestamp without time zone NOT NULL DEFAULT now(),
  CONSTRAINT pk_solution_set_id PRIMARY KEY (id ),
  CONSTRAINT fk_cipher_id FOREIGN KEY (cipher_id)
      REFERENCES cipher (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT unique_solution_set_name UNIQUE (name )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE solution_set
  OWNER TO postgres;

  
-- Table: solution

-- DROP TABLE solution;

CREATE TABLE solution
(
  solution_id integer NOT NULL,
  total_matches integer,
  created_timestamp timestamp without time zone NOT NULL DEFAULT now(),
  cipher_id integer NOT NULL,
  unique_matches integer DEFAULT 0,
  adjacent_matches integer DEFAULT 0,
  solution_set_id integer NOT NULL,
  CONSTRAINT pk_solution_set_id_solution_id PRIMARY KEY (solution_set_id, solution_id ),
  CONSTRAINT fk_cipher_id FOREIGN KEY (cipher_id)
      REFERENCES cipher (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_solution_set_id FOREIGN KEY (solution_set_id)
      REFERENCES solution_set (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE solution
  OWNER TO postgres;

  
-- Table: plaintext

-- DROP TABLE plaintext;

CREATE TABLE plaintext
(
  ciphertext_id integer NOT NULL,
  value character varying NOT NULL,
  solution_id integer NOT NULL,
  solution_set_id integer NOT NULL,
  has_match boolean DEFAULT false,
  CONSTRAINT pk_solution_set_id_solution_id_ciphertext_id PRIMARY KEY (solution_set_id, solution_id, ciphertext_id ),
  CONSTRAINT fk_solution_set_id_solution_id FOREIGN KEY (solution_set_id, solution_id)
      REFERENCES solution (solution_set_id, solution_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE
)
WITH (
  OIDS=FALSE
);
ALTER TABLE plaintext
  OWNER TO postgres;

  
-- Table: execution_stats

-- DROP TABLE execution_stats;

CREATE TABLE execution_stats
(
  id serial NOT NULL,
  start_date timestamp without time zone,
  end_date timestamp without time zone,
  population_size integer,
  lifespan integer,
  survival_rate double precision,
  mutation_rate double precision,
  crossover_rate double precision,
  crossover_algorithm character varying,
  fitness_evaluator character varying,
  mutation_algorithm character varying,
  CONSTRAINT pk_execution_id PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE execution_stats
  OWNER TO postgres;
  
  
-- Table: generation_stats

-- DROP TABLE generation_stats;

CREATE TABLE generation_stats
(
  id serial NOT NULL,
  execution_id integer NOT NULL,
  generation integer NOT NULL,
  execution_time bigint,
  best_fitness double precision,
  average_fitness double precision,
  known_solution_proximity double precision,
  CONSTRAINT pk_generation_id PRIMARY KEY (id ),
  CONSTRAINT fk_execution_id FOREIGN KEY (execution_id)
      REFERENCES execution_stats (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE generation_stats
  OWNER TO postgres;
