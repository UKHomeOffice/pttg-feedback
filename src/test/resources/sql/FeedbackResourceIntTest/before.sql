DELETE FROM feedback;

INSERT INTO feedback
    VALUES (
      '1',
      'some uuid',
      '2017-09-11 14:45:48.094',
      'some session id',
      'NOT USED',
      'NOT USED',
      'some user id',
       '{
         "nino": "JD123456C",
         "match": "yes"
        }'
    );


INSERT INTO feedback
VALUES (
        '2',
        'some uuid',
        '2017-09-11 14:45:55.033',
        'some session id',
        'NOT USED',
        'NOT USED',
        'some user id',
        '{
          "nino": "AN123456C",
          "match": "yes"
         }'
);