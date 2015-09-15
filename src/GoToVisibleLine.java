import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;

import java.awt.*;

public class GoToVisibleLine {

    public static void go(Editor editor, int lineNumberSuffix, boolean extend) {
        Rectangle visibleBox = editor.getScrollingModel().getVisibleArea();
        int firstVisibleLine = editor.xyToLogicalPosition(visibleBox.getLocation()).line + 1;
        int line = firstVisibleLine / 100 * 100 + lineNumberSuffix;
        if (line < firstVisibleLine)
            line += 100;
        LogicalPosition pos = new LogicalPosition(line, 0);
        int newOffset = editor.logicalPositionToOffset(pos);

        if (extend) {
            EditorSelection.extendTo(editor, newOffset);
        } else {
            EditorSelection.moveTo(editor, newOffset);
        }
    }
}
