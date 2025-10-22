package cc.sika.file.consts;

/**
 *
 * @author 小吴来哩
 * @since 2025-09
 */
public class FileConsts {

    /**
     * 元数据类型
     */
    public static class MetaType {
        public static final int ALL_TYPE = -1;
        public static final int DIR = 1;
        public static final int FILE = 2;

        private MetaType() {
            // constant do nothing
        }
    }



    private FileConsts() {
        // constant do nothing
    }
}
