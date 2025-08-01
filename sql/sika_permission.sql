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

 Date: 01/08/2025 23:39:52
*/


-- ----------------------------
-- Table structure for sika_permission
-- ----------------------------
DROP TABLE IF EXISTS "public"."sika_permission";
CREATE TABLE "public"."sika_permission" (
  "id" int8 NOT NULL,
  "permission_content" varchar(255) COLLATE "pg_catalog"."default",
  "permission_desc" varchar(600) COLLATE "pg_catalog"."default",
  "permission_type" int2,
  "create_time" timestamp(6),
  "create_by" varchar(255) COLLATE "pg_catalog"."default",
  "create_id" int8,
  "update_time" timestamp(6),
  "update_by" varchar(255) COLLATE "pg_catalog"."default",
  "update_id" int8
)
;
COMMENT ON COLUMN "public"."sika_permission"."id" IS 'id';
COMMENT ON COLUMN "public"."sika_permission"."permission_content" IS '权限字符串';
COMMENT ON COLUMN "public"."sika_permission"."permission_desc" IS '权限描述';
COMMENT ON COLUMN "public"."sika_permission"."permission_type" IS '权限类型';
COMMENT ON COLUMN "public"."sika_permission"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."sika_permission"."create_by" IS '创建人用户名';
COMMENT ON COLUMN "public"."sika_permission"."create_id" IS '创建人id';
COMMENT ON COLUMN "public"."sika_permission"."update_time" IS '更新时间';
COMMENT ON COLUMN "public"."sika_permission"."update_by" IS '更新人用户名';
COMMENT ON COLUMN "public"."sika_permission"."update_id" IS '更新人id';
COMMENT ON TABLE "public"."sika_permission" IS '权限表';

-- ----------------------------
-- Primary Key structure for table sika_permission
-- ----------------------------
ALTER TABLE "public"."sika_permission" ADD CONSTRAINT "SIKA_PERMISSION_pkey" PRIMARY KEY ("id");
