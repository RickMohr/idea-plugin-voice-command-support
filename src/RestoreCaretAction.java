import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;

public class RestoreCaretAction extends EditorAction
{
    protected RestoreCaretAction() {
        super(new Handler());
    }

    private static class Handler extends EditorActionHandler {

        public void execute(Editor editor, DataContext dataContext) {
            PersistCaret.restore(editor);
        }

    }
}