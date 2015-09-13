import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.util.TextRange;

public class EmacsStyleWordNavigation {

    public static void moveByWords(Editor editor, int count) {
        int newOffset = getOffsetOfMoveByWords(editor, count);
        editor.getCaretModel().moveToOffset(newOffset);
        editor.getSelectionModel().removeSelection();
    }

    public static void extendByWords(Editor editor, int count) {
        int offset = editor.getCaretModel().getOffset();
        int root = offset;
        SelectionModel selectionModel = editor.getSelectionModel();
        if (selectionModel.hasSelection()) {
            int start = selectionModel.getSelectionStart();
            int end = selectionModel.getSelectionEnd();
            root = (offset == start ? end : start);
        }
        int newOffset = getOffsetOfMoveByWords(editor, count);
        editor.getCaretModel().moveToOffset(newOffset);
        editor.getSelectionModel().setSelection(Math.min(root, newOffset), Math.max(root, newOffset));
    }

    private static int getOffsetOfMoveByWords(Editor editor, int count) {
        Document doc = editor.getDocument();
        int offset = editor.getCaretModel().getOffset();
        if (count > 0) {  // Move forward
            while (count-- > 0) {
                // Make sure we start at a word character
                if (!hasWordCharAfter(doc, offset))
                    while (!hasWordCharAfter(doc, offset))
                        offset++;
                // Move to first non-word character
                while (hasWordCharAfter(doc, offset))
                    offset++;
            }
        } else if (count < 0) {  // Move backward
            while (count < 0) {
                count++;
                // Make sure we start at a word character
                if (!hasWordCharBefore(doc, offset))
                    while (!hasWordCharBefore(doc, offset))
                        offset--;
                // Move to first non-word character
                while (hasWordCharBefore(doc, offset))
                    offset--;
            }
        }
        return offset;
    }

    private static boolean hasWordCharAfter(Document doc, int offset) {
        String text = doc.getText(new TextRange(offset, offset + 1));
        if (text.length() == 0) {
            return false;
        } else {
            char c = text.charAt(0);
            return (Character.isLetter(c) || Character.isDigit(c));
        }
    }

    private static boolean hasWordCharBefore(Document doc, int offset) {
        if (offset == 0) {
            return false;
        } else {
            String text = doc.getText(new TextRange(offset - 1, offset));
            char c = text.charAt(0);
            return (Character.isLetter(c) || Character.isDigit(c));
        }
    }
}
