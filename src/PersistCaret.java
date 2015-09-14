import com.intellij.openapi.editor.Editor;

public class PersistCaret {

    private static int _offset;

    public static void save(Editor editor) {
        _offset = editor.getCaretModel().getOffset();
    }

    public static void restore(Editor editor) {
        editor.getCaretModel().moveToOffset(_offset);
    }
}
