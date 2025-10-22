@echo off
chcp 65001
setlocal

rem ===== 配置参数 =====
set JAR_NAME=sika-file-server-1.0.0.jar

rem ===== 虚拟机参数 =====
set VM_OPTS=^
 -Dsika.upload.access-key=$oss-key ^
 -Dsika.upload.secret-key=$oss-secret ^
 -Dsika.upload.bucket=sika-file ^
 -Dsika.upload.end-point=oss-cn-guangzhou.aliyuncs.com ^
 -Dsika.upload.oss-type=a_li ^
 -Dspring.data.redis.host=$host ^
 -Dspring.data.redis.password=$password ^
 -Dspring.datasource.password=$password ^
 -Xms512m ^
 -Xmx1024m ^
 -XX:+UseG1GC

rem ===== 启动应用并显示日志 =====
echo 正在启动应用: %JAR_NAME%
echo 虚拟机参数: %VM_OPTS%
echo.
echo ===== 应用日志开始 =====
echo.

java %VM_OPTS% -jar %JAR_NAME%

rem ===== 应用退出后处理 =====
echo.
echo ===== 应用已退出 =====
echo 按任意键关闭窗口...
pause >nul
endlocal