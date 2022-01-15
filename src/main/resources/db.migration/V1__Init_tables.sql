CREATE TABLE student_group
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(10) UNIQUE NOT NULL,
    department VARCHAR(150),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE subscription
(
    id         SERIAL PRIMARY KEY,
    subgroup   INT     DEFAULT 0,
    is_default BOOLEAN DEFAULT FALSE,
    user_id    VARCHAR NOT NULL UNIQUE,
    group_id   INT REFERENCES student_group (id) ON DELETE CASCADE,

    CHECK ( subgroup BETWEEN 0 AND 2 )
);

CREATE TABLE teacher
(
    id          SERIAL PRIMARY KEY,
    last_name   VARCHAR(30) NOT NULL,
    first_name  VARCHAR(30) NOT NULL,
    middle_name VARCHAR(30) NOT NULL,
    gender      VARCHAR(10),
    email       VARCHAR(30) UNIQUE,
    phone       VARCHAR(20) UNIQUE,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CHECK ( phone ~ '^(\+?\d)[ 0-9-()]{6,19}' ),
    CHECK ( email ~* '(^[a-z]{2})([a-z_|0-9-]{1,15})@([a-z]{1,15})\.(com|ru)$')
);

CREATE TABLE subject
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(50),
    type       VARCHAR(20) DEFAULT 'lecture',
    created_at TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,

    UNIQUE (name, type),
    CHECK ( type = 'lecture' OR type = 'practice' )
);

CREATE TABLE teacher_subject
(
    subject_id INT REFERENCES subject (id) ON DELETE CASCADE,
    teacher_id INT REFERENCES teacher (id) ON DELETE CASCADE,
    UNIQUE (subject_id, teacher_id)
);

CREATE TABLE period
(
    id          SERIAL PRIMARY KEY,
    period_num  INT UNIQUE NOT NULL,
    first_half  jsonb,
    second_half jsonb,

    CHECK ( period_num BETWEEN 1 AND 6 )
);

CREATE TABLE day
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(15) UNIQUE NOT NULL,
    CHECK ( name IN ('monday', 'tuesday', 'wednesday', 'thursday',
                     'friday', 'saturday', 'sunday'))
);

CREATE TABLE planned_timetable
(
    id          SERIAL PRIMARY KEY,
    week_type   VARCHAR(20) NOT NULL DEFAULT 'numerator',
    subgroup    INT                  DEFAULT 0 NOT NULL,
    day_id      INT,
    period_id   INT,
    subject_id  INT,
    group_id    INT,
    audience_id INT,
    created_at  TIMESTAMP            DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP            DEFAULT CURRENT_TIMESTAMP,

    CHECK ( week_type = 'numerator' OR week_type = 'denominator')
);

CREATE TABLE audience
(
    id         SERIAL PRIMARY KEY,
    number     VARCHAR(10) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE group_teacher
(
    group_id   INT REFERENCES student_group (id) ON DELETE CASCADE,
    teacher_id INT REFERENCES teacher (id) ON DELETE CASCADE,
    UNIQUE (group_id, teacher_id)
);
