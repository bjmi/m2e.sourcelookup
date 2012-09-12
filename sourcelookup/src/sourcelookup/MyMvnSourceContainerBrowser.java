package sourcelookup;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.debug.core.sourcelookup.ISourceContainer;
import org.eclipse.debug.core.sourcelookup.ISourceLookupDirector;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.debug.ui.IJavaDebugUIConstants;
import org.eclipse.jdt.internal.debug.ui.JDIDebugUIPlugin;
import org.eclipse.jdt.internal.debug.ui.actions.ProjectSelectionDialog;
import org.eclipse.jdt.internal.debug.ui.sourcelookup.JavaProjectSourceContainerBrowser;
import org.eclipse.jdt.internal.debug.ui.sourcelookup.SourceLookupMessages;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

/**
 * // TODO (michael) document me
 * 
 * @author Björn Michael
 * @since 1.0
 */
public class MyMvnSourceContainerBrowser extends JavaProjectSourceContainerBrowser {

  @Override
  public ISourceContainer[] addSourceContainers(final Shell shell, final ISourceLookupDirector director) {
    final List<IJavaProject> projects = getPossibleAdditions(director);

    final ProjectSelectionDialog dialog = new ProjectSelectionDialog(shell, projects);
    dialog.setTitle(SourceLookupMessages.JavaProjectSourceContainerBrowser_1);

    final MultiStatus status = new MultiStatus(JDIDebugUIPlugin.getUniqueIdentifier(),
        IJavaDebugUIConstants.INTERNAL_ERROR, "Failed to add project(s)", null);

    final List<ISourceContainer> sourceContainers = new ArrayList<ISourceContainer>();
    if (dialog.open() == Window.OK) {
      final Object[] selections = dialog.getResult();
      final List<IJavaProject> additions = new ArrayList<IJavaProject>(selections.length);
      for (final Object selection : selections) {
        final IJavaProject jp = (IJavaProject) selection;
        additions.add(jp);
      }

      for (final IJavaProject jp : additions) {
        sourceContainers.add(new MyMvnSourceContainer(jp));
      }
    }

    if (!status.isOK()) {
      JDIDebugUIPlugin.statusDialog(status);
    }

    return sourceContainers.toArray(new ISourceContainer[sourceContainers.size()]);
  }

}
