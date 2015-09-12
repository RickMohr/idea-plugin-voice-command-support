import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import com.intellij.openapi.util.TextRange;

public class WordLeftAction extends EditorAction
{
    protected WordLeftAction() {
        super(new Handler());
    }

    private static class Handler extends EditorActionHandler {
        public void execute(Editor editor, DataContext dataContext) {
            int newOffset = getOffsetOfMoveByWords(editor, -1);
            editor.getCaretModel().moveToOffset(newOffset);
            if (editor.getSelectionModel().hasSelection()) {
                editor.getSelectionModel().removeSelection();
            }
        }

        private int getOffsetOfMoveByWords(Editor editor, int count)
        {
            Document doc = editor.getDocument();
            int offset = editor.getCaretModel().getOffset();
            if (count > 0) // Move forward
            {
                while (count-- > 0)
                {
                    // Make sure we start at a word character
                    if (!hasWordCharAfter(doc, offset))
                        while (!hasWordCharAfter(doc, offset))
                            offset++;
                    // Move to first non-word character
                    while (hasWordCharAfter(doc, offset))
                        offset++;
                }
            }
            else if (count < 0) // Move backward
            {
                while (count < 0)
                {
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

        private boolean hasWordCharAfter(Document doc, int offset)
        {
            String text = doc.getText(new TextRange(offset, offset + 1));
            if (text.length() == 0) {
                return false;
            } else {
                char c = text.charAt(0);
                return (Character.isLetter(c) || Character.isDigit(c));
            }
        }

        private boolean hasWordCharBefore(Document doc, int offset)
        {
            if (offset == 0) {
                return false;
            } else {
                String text = doc.getText(new TextRange(offset - 1, offset));
                char c = text.charAt(0);
                return (Character.isLetter(c) || Character.isDigit(c));
            }
        }

    }
}