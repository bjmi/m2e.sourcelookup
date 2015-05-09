package bjmi.m2e.sourcelookup;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.sourcelookup.ISourceContainer;
import org.eclipse.debug.core.sourcelookup.ISourceLookupDirector;
import org.eclipse.debug.ui.sourcelookup.AbstractSourceContainerBrowser;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.debug.ui.actions.ProjectSelectionDialog;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.m2e.core.MavenPlugin;
import org.eclipse.m2e.core.project.IMavenProjectFacade;
import org.eclipse.swt.widgets.Shell;

import bjmi.m2e.sourcelookup.internal.SourceLookupMessages;
import bjmi.m2e.sourcelookup.internal.SourceLookupPlugin;

/**
 * // TODO (michael) document me TODO(michael) chose a more appropriate class name
 * 
 * @author Björn Michael
 * @since 1.0
 */
public class MyMvnSourceContainerBrowser extends AbstractSourceContainerBrowser {

  private static List<IJavaProject> getPossibleAdditions0(final ISourceLookupDirector director) {
    final List<IProject> mavenProjects = new ArrayList<IProject>();
    for (final IMavenProjectFacade mavenProject : MavenPlugin.getMavenProjectRegistry().getProjects()) {
      mavenProjects.add(mavenProject.getProject());
    }

    final List<IJavaProject> javaProjects = new ArrayList<IJavaProject>();
    final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
    try {
      for (final IJavaProject javaProject : JavaCore.create(root).getJavaProjects()) {
        if (mavenProjects.contains(javaProject.getProject())) {
          javaProjects.add(javaProject);
        }
      }
    } catch (final JavaModelException e) {
      final IStatus status = new Status(IStatus.ERROR, SourceLookupPlugin.getInstance().getBundle().getSymbolicName(),
          "Can't retrieve Java projects.", e);
      SourceLookupPlugin.getInstance().getLog().log(status);
    }

    for (final ISourceContainer container : director.getSourceContainers()) {
      if (container.getType().getId().equals(MyMvnSourceContainerTypeDelegate.TYPE_ID)) {
        javaProjects.remove(((MyMvnSourceContainer) container).getJavaProject());
      }
    }

    return javaProjects;
  }

  @Override
  public ISourceContainer[] addSourceContainers(final Shell shell, final ISourceLookupDirector director) {
    final List<IJavaProject> projects = getPossibleAdditions0(director);

    final ProjectSelectionDialog dialog = new ProjectSelectionDialog(shell, projects);
    dialog.setTitle(SourceLookupMessages.MyMvnSourceContainerBrowser_Dialog_Title);

    final MultiStatus status = new MultiStatus(SourceLookupPlugin.getInstance().getBundle().getSymbolicName(),
        IStatus.ERROR, "Failed to add project(s)", null);

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
      ErrorDialog.openError(shell, "Error", null, status);
    }

    return sourceContainers.toArray(new ISourceContainer[]{});
  }

  @Override
  public boolean canAddSourceContainers(final ISourceLookupDirector director) {
    return !getPossibleAdditions0(director).isEmpty();
  }

}
