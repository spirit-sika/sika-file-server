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

 Date: 01/08/2025 23:40:13
*/


-- ----------------------------
-- Table structure for sika_role
-- ----------------------------
DROP TABLE IF EXISTS "public"."sika_role";
CREATE TABLE "public"."sika_role" (
  "id" int8 NOT NULL,
  "role_name" varchar(255) COLLATE "pg_catalog"."default",
  "role_desc" varchar(500) COLLATE "pg_catalog"."default",
  "create_time" timestamp(6),
  "create_by" varchar(255) COLLATE "pg_catalog"."default",
  "create_id" int8,
  "update_time" timestamp(6),
  "update_by" varchar(255) COLLATE "pg_catalog"."default",
  "update_id" int8
)
;
COMMENT ON COLUMN "public"."sika_role"."id" IS 'ID';
COMMENT ON COLUMN "public"."sika_role"."role_name" IS '角色名称';
COMMENT ON COLUMN "public"."sika_role"."role_desc" IS '角色描述';
COMMENT ON COLUMN "public"."sika_role"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sika_role"."create_by" IS '创建人用户名';
COMMENT ON COLUMN "public"."sika_role"."create_id" IS '创建人id';
COMMENT ON COLUMN "public"."sika_role"."update_time" IS '更新时间';
COMMENT ON COLUMN "public"."sika_role"."update_by" IS '更新人用户名';
COMMENT ON COLUMN "public"."sika_role"."update_id" IS '更新人id';
COMMENT ON TABLE "public"."sika_role" IS '角色表';

-- ----------------------------
-- Primary Key structure for table sika_role
-- ----------------------------
ALTER TABLE "public"."sika_role" ADD CONSTRAINT "SIKA_ROLE_pkey" PRIMARY KEY ("id");
