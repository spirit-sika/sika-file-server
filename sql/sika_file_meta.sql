create table sika_file_meta
(
    id               varchar(64)        not null
        constraint "sika-file-meta_pkey"
            primary key,
    original_name    varchar(255),
    file_mime        varchar(255),
    file_extension   varchar(255),
    storage_path     text,
    preview_path     text,
    sha256           varchar(64),
    region_target    varchar(10),
    create_time      timestamp(6),
    create_by        varchar(255),
    create_id        bigint,
    update_time      timestamp(6),
    update_by        varchar(255),
    update_id        bigint,
    absolute_path    varchar(1000),
    file_size        bigint,
    parent_id        varchar(64),
    meta_type        smallint default 2 not null,
    absolute_id_path varchar(1000)
);

comment on table sika_file_meta is '文件元数据表';

comment on column sika_file_meta.id is '文件唯一标识';

comment on column sika_file_meta.original_name is '原始文件名';

comment on column sika_file_meta.file_mime is '文件mime类型';

comment on column sika_file_meta.file_extension is '文件扩展名带.';

comment on column sika_file_meta.storage_path is 'S3/OSS存储路径(加密)';

comment on column sika_file_meta.preview_path is '文件预览ISS存储路径';

comment on column sika_file_meta.sha256 is '文件哈希(校验完整性)';

comment on column sika_file_meta.region_target is '目标区域(US/CN/ALL)';

comment on column sika_file_meta.create_time is '上传时间';

comment on column sika_file_meta.create_by is '上传用户名';

comment on column sika_file_meta.create_id is '上传人id';

comment on column sika_file_meta.update_time is '修改时间';

comment on column sika_file_meta.update_by is '更新人用户名';

comment on column sika_file_meta.update_id is '更新人id';

comment on column sika_file_meta.absolute_path is '文件系统中的路径';

comment on column sika_file_meta.file_size is '文件大小字节数';

comment on column sika_file_meta.parent_id is '父文件id, 用于组装文件系统的树形结构';

comment on column sika_file_meta.meta_type is '文件类型, 1-文件夹, 2-文件';

comment on column sika_file_meta.absolute_id_path is '文件系统中的路径id';

alter table sika_file_meta
    owner to postgres;

