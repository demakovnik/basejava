create table resumes
(
    uuid      char(36) primary key not null,
    full_name text                 not null
);


create table contacts
(
    id          serial,
    resume_uuid char(36) not null references resumes (uuid) on DELETE cascade,
    type        text     not null,
    value       text     not null
);

create unique index contact_uuid_type_index
    on contacts (resume_uuid, type);


