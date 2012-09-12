package sourcelookup;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.sourcelookup.ISourceContainer;
import org.eclipse.debug.core.sourcelookup.ISourceContainerType;
import org.eclipse.debug.core.sourcelookup.containers.CompositeSourceContainer;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.launching.IRuntimeClasspathEntry;
import org.eclipse.jdt.launching.JavaRuntime;

/**
 * Compute and provide source containers from a maven managed java project.
 * 
 * @author Björn Michael
 * @since 1.0
 */
class MyMvnSourceContainer extends CompositeSourceContainer {

  private final IJavaProject jp;

  MyMvnSourceContainer(final IJavaProject javaProject) {
    jp = javaProject;
  }

  @Override
  public String getName() {
    return String.format("Maven Dependencies of '%s'", getProjectName());
  }

  @Override
  public ISourceContainerType getType() {
    return getSourceContainerType(MyMvnSourceContainerTypeDelegate.TYPE_ID);
  }

  @Override
  protected ISourceContainer[] createSourceContainers() throws CoreException {
    return fromMavenClasspathContainer();
  }

  private ISourceContainer[] fromMavenClasspathContainer() throws CoreException {
    for (final IClasspathEntry cpe : jp.getRawClasspath()) {
      if (IClasspathEntry.CPE_CONTAINER == cpe.getEntryKind() && //
          "org.eclipse.m2e.MAVEN2_CLASSPATH_CONTAINER".equals(cpe.getPath().toString())) {
        final IRuntimeClasspathEntry newRuntimeContainerClasspathEntry = JavaRuntime.newRuntimeContainerClasspathEntry(
            cpe.getPath(), IRuntimeClasspathEntry.USER_CLASSES, jp);
        final IRuntimeClasspathEntry[] resolveRuntimeClasspathEntry = JavaRuntime.resolveRuntimeClasspathEntry(
            newRuntimeContainerClasspathEntry, jp);

        // there is only one maven2 classpath container in a project
        return JavaRuntime.getSourceContainers(resolveRuntimeClasspathEntry);
      }
    }

    return new ISourceContainer[0];
  }

  String getProjectName() {
    return jp.getElementName();
  }

}
