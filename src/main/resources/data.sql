INSERT INTO `drone`.`medication` (`code`, `image_url`, `name`, `weight`)
 VALUES ('MD_1', 'https://www.abc.com/Metformin', 'Metformin', '10'),
  ('MD_2', 'https://www.abc.com/Losartan', 'Losartan', '20'),
  ('MD_3', 'https://www.abc.com/Antibiotics', 'Antibiotics', '15'),
  ('MD_4', 'https://www.abc.com/Albuterol', 'Albuterol', '30'),
  ('MD_5', 'https://www.abc.com/Gabapentin', 'Gabapentin', '50'),
  ('MD_6', 'https://www.abc.com/Antihistamines', 'Antihistamines', '5'),
  ('MD_7', 'https://www.abc.com/Gabapentin', 'Gabapentin', '120');

INSERT INTO `drone`.`address` (`address_id`, `city`, `country`, `latitude`, `line1`, `line2`, `longitude`)
 VALUES ('1', 'Beliatta', 'Sri Lanka', '77.20N', '51A', '', '85.325E'),
 ('2', 'Colombo', 'Sri Lanka', '72.20N', '55 A, Colombo 10', '', '81.325E'),
 ('3', 'Colombo', 'Sri Lanka', '73.20N', '121/2 , Colombo 5', '', '81.325E');

insert
    into
        `drone`.`drone`
        (`battery_capacity`, `drone_model`, `drone_state`, `weight_limit_in_gram`, `serial_number`)
    values
        (100, '1', '3', 500, '111'),
        (100, '2', '2', 500, '222'),
        (100, '3', '1', 500, '333');

insert
    into
        delivery
        (address_id, serial_number, total_weight)
    values
        (1, '111', 130),
        (1, '222', 295),
        (2, '111', 385);


insert
    into
        delivery_details
        (delivery_id, code, medication_qty)
    values
        (1, 'MD_1', 3),
        (1, 'MD_2', 5),
        (2, 'MD_3', 3),
        (2, 'MD_4', 5),
        (3, 'MD_5', 5),
        (3, 'MD_6', 3),
        (3, 'MD_7', 1);