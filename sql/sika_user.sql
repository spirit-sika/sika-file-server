/*
 Navicat Premium Dump SQL

 Source Server         : ps
 Source Server Type    : PostgreSQL
 Source Server Version : 170004 (170004)
 Source Host           : localhost:5432
 Source Catalog        : sika-file
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 170004 (170004)
 File Encoding         : 65001

 Date: 01/08/2025 23:40:18
*/


-- ----------------------------
-- Table structure for sika_user
-- ----------------------------
DROP TABLE IF EXISTS "public"."sika_user";
CREATE TABLE "public"."sika_user" (
  "id" int8 NOT NULL DEFAULT nextval('"SIKA_USER_ID_seq"'::regclass),
  "username" varchar(255) COLLATE "pg_catalog"."default",
  "password" varchar(255) COLLATE "pg_catalog"."default",
  "email" varchar(255) COLLATE "pg_catalog"."default",
  "phone" varchar(255) COLLATE "pg_catalog"."default",
  "avatar" varchar(600) COLLATE "pg_catalog"."default",
  "sex" int2,
  "status" int2,
  "create_time" timestamp(6),
  "create_by" varchar(255) COLLATE "pg_catalog"."default",
  "create_id" int8 NOT NULL DEFAULT nextval('"SIKA_USER_CREATE_ID_seq"'::regclass),
  "update_time" timestamp(6),
  "update_by" varchar(255) COLLATE "pg_catalog"."default",
  "update_id" int8 NOT NULL DEFAULT nextval('"SIKA_USER_UPDATE_ID_seq"'::regclass)
)
;
COMMENT ON COLUMN "public"."sika_user"."username" IS '用户名';
COMMENT ON COLUMN "public"."sika_user"."password" IS '密码';
COMMENT ON COLUMN "public"."sika_user"."email" IS '邮箱';
COMMENT ON COLUMN "public"."sika_user"."phone" IS '手机号码';
COMMENT ON COLUMN "public"."sika_user"."avatar" IS '头像url';
COMMENT ON COLUMN "public"."sika_user"."sex" IS '性别: 0-未知, 1-男, 2-女';
COMMENT ON COLUMN "public"."sika_user"."status" IS '状态, 0禁用,1-正常, 2-已删除';
COMMENT ON COLUMN "public"."sika_user"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sika_user"."create_by" IS '创建人用户名';
COMMENT ON COLUMN "public"."sika_user"."create_id" IS '创建人id';
COMMENT ON COLUMN "public"."sika_user"."update_time" IS '更新时间';
COMMENT ON COLUMN "public"."sika_user"."update_by" IS '更新人用户名';
COMMENT ON COLUMN "public"."sika_user"."update_id" IS '更新人id';
COMMENT ON TABLE "public"."sika_user" IS '用户表';

-- ----------------------------
-- Primary Key structure for table sika_user
-- ----------------------------
ALTER TABLE "public"."sika_user" ADD CONSTRAINT "SIKA_USER_pkey" PRIMARY KEY ("id");
