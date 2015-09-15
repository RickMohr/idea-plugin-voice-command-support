import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;

/**
 * Created by Rick on 9/14/2015.
 */
public class EditorSelection {

    public static void moveTo(Editor editor, int newOffset) {
        editor.getCaretModel().moveToOffset(newOffset);
        editor.getSelectionModel().removeSelection();
    }

    public static void extendTo(Editor editor, int newOffset) {
        int offset = editor.getCaretModel().getOffset();
        int root = offset;
        SelectionModel selectionModel = editor.getSelectionModel();
        if (selectionModel.hasSelection()) {
            int start = selectionModel.getSelectionStart();
            int end = selectionModel.getSelectionEnd();
            root = (offset == start ? end : start);
        }
        editor.getCaretModel().moveToOffset(newOffset);
        editor.getSelectionModel().setSelection(Math.min(root, newOffset), Math.max(root, newOffset));
    }
}
